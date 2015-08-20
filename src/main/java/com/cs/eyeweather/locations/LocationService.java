package com.cs.eyeweather.locations;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import com.cs.eyeweather.users.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class LocationService {
	
	private static final String GOOGLE_API = "maps.googleapis.com/maps/api/geocode/json";
	private static final String WEATHER_API = "forecast.weather.gov/MapClick.php";
	
	private List<Location> locList = new ArrayList<>();;
	
	public void addLocation(User user, String latlon) throws URISyntaxException, ClientProtocolException, IOException {
		
		String lat = latlon.substring(latlon.indexOf("=") + 1, latlon.indexOf("&"));
		String lon = latlon.substring(latlon.lastIndexOf("=") + 1);

		String formattedAddr = getFormattedAddr(lat, lon);
		
		URI uri = new URIBuilder()
		.setScheme("http")
		.setHost(WEATHER_API)
		.addParameter("lat", lat).addParameter("lon", lon)
		.addParameter("FcstType", "json")
		.build();
		
		HttpGet httpget = new HttpGet(uri);		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		//Set to 10 seconds because the weather.gov site is slow sometimes
		RequestConfig requestConfig = RequestConfig.custom()
		        .setSocketTimeout(10000)
		        .setConnectTimeout(10000)
		        .build();
		
		httpget.setConfig(requestConfig);		
		CloseableHttpResponse response1 = httpclient.execute(httpget);		
		
		HttpEntity result = response1.getEntity();
		InputStream stream = result.getContent();			
		ObjectMapper mapper = new ObjectMapper();
		
		//couldn't get TypeReference mapper to work with the forecast data
		//(workaround) read json string as a tree and walk through it to get to nested objects
		JsonNode root = null;
		
		try {
			root = mapper.readTree(stream);
		} catch  (Exception e) {
			//ignore error
		}
		
		String windSpeed = "Unknown";
		String temperature = "Unknown";
		String humidity = "Unknown";
		String barPressure = "Unknown";
		String currentWeather = "Unknown";
		String forecast = "No Forcast Data Available";
		
		//wind, temp, humidity, pressure, and weather
		if (root != null) {
			JsonNode current = root.get("currentobservation");
			windSpeed = current.get("Winds").asText() + "mph";
			temperature = current.get("Temp").asText() + "F";
			humidity = current.get("Relh").asText() + "%";
			barPressure = current.get("SLP").asText();
			currentWeather = current.get("Weather").asText();

			// forecast
			JsonNode data = root.get("data");
			JsonNode text = data.get("text");
			forecast = text.get(0).asText();
		}

		Location loc = new Location.Builder()
							.id(UUID.randomUUID().toString())
							.ownerId(user.getId())
							.address(formattedAddr)
							.coordinates("(" + lat + "," + lon + ")")
							.reqTime(new Date())
							.windSpeed(windSpeed)
							.temperature(temperature)
							.humidity(humidity)
							.barPressure(barPressure)
							.currentWeather(currentWeather)
							.forecast(forecast)
							.build();
		
		stream.close();
		httpclient.close();
		
		locList.add(loc);
		
	}
	
	private String getFormattedAddr(String lat, String lon) throws URISyntaxException, ClientProtocolException, IOException{
		
		URI uri = new URIBuilder()
		.setScheme("http")
		.setHost(GOOGLE_API)
		.addParameter("latlng", lat + "," + lon)
		.build();
		
		System.out.println(uri.toURL());
			
		HttpGet httpget = new HttpGet(uri);		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom()
		        .setSocketTimeout(5000)
		        .setConnectTimeout(5000)
		        .build();
		
		httpget.setConfig(requestConfig);		
		CloseableHttpResponse response1 = httpclient.execute(httpget);		
		
		HttpEntity result = response1.getEntity();
		InputStream stream = result.getContent();			
		ObjectMapper mapper = new ObjectMapper();
		//get inner nodes
		JsonNode root = mapper.readTree(stream);
		
		if (root.get("status").asText().equals("ZERO_RESULTS")){
			return "No Known Address at Specified Coordinates";
		}
		
		JsonNode results = root.get("results");
		JsonNode first = results.get(0);
		String address = first.get("formatted_address").asText();
		
		stream.close();
		httpclient.close();
		
		return address;
	}

	public List<Location> findAllByUsername(String userId) {
		List<Location> result = new ArrayList<>();
		
		if (userId != null){
			for (Location loc: locList){
				if (loc.getOwnerId().equals(userId)){
					result.add(loc);
				}
			}
		}
		return result;
	}

	public void deleteLocation(String userId, Location locToDelete) {
		Iterator<Location> iter = locList.iterator();
		
		while (iter.hasNext()) {
		    Location loc = iter.next();

		    if (loc.getId().equals(locToDelete.getId()) && loc.getOwnerId().equals(userId))
		        iter.remove();
		}
	}

	public Location getLocationById(String locId){
		for (Location loc : locList){
			if (loc.getId().equals(locId)){
				return loc;
			}
		}
		return null;
	}
}
