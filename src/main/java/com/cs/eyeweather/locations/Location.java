package com.cs.eyeweather.locations;

import java.util.Date;

public class Location {

	private String id;
	private String ownerId;
	private String address;
	private String coordinates;
	private Date reqTime;
	private String windSpeed;
	private String temperature;
	private String humidity;
	private String barPressure;
	private String currentWeather;
	private String forecast;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

	public String getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(String windSpeed) {
		this.windSpeed = windSpeed;
	}

	public Date getReqTime() {
		return reqTime;
	}

	public void setReqTime(Date reqTime) {
		this.reqTime = reqTime;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getBarPressure() {
		return barPressure;
	}

	public void setBarPressure(String barPressure) {
		this.barPressure = barPressure;
	}
	
	public String getCurrentWeather() {
		return currentWeather;
	}

	public void setCurrentWeather(String currentWeather) {
		this.currentWeather = currentWeather;
	}

	public String getForecast() {
		return forecast;
	}

	public void setForecast(String forecast) {
		this.forecast = forecast;
	}
		private Location(Builder b) {
		this.id = b.id;
		this.ownerId = b.ownerId;
		this.address = b.address;
		this.coordinates = b.coordinates;
		this.windSpeed = b.windSpeed;
		this.reqTime = b.reqTime;
		this.temperature = b.temperature;
		this.humidity = b.humidity;
		this.barPressure = b.barPressure;
		this.currentWeather = b.currentWeather;
		this.forecast = b.forecast;
	}

	public static class Builder {
		private String id;
		private String ownerId;
		private String address;
		private String coordinates;
		private String windSpeed;
		private Date reqTime;
		private String temperature;
		private String humidity;
		private String barPressure;
		private String currentWeather;
		private String forecast;
		
		public Builder id(String id) {
			this.id = id;
			return this;
		}
		
		public Builder ownerId(String ownerId) {
			this.ownerId = ownerId;
			return this;
		}
		
		public Builder address(String address) {
			this.address = address;
			return this;
		}
		
		public Builder coordinates(String coordinates) {
			this.coordinates = coordinates;
			return this;
		}
		
		public Builder windSpeed(String windSpeed) {
			this.windSpeed = windSpeed;
			return this;
		}
		
		public Builder reqTime(Date reqTime) {
			this.reqTime = reqTime;
			return this;
		}
		
		public Builder temperature(String temperature) {
			this.temperature = temperature;
			return this;
		}
		
		public Builder humidity(String humidity) {
			this.humidity = humidity;
			return this;
		}
		
		public Builder barPressure(String barPressure) {
			this.barPressure = barPressure;
			return this;
		}
		
		public Builder currentWeather(String currentWeather) {
			this.currentWeather = currentWeather;
			return this;
		}
		
		public Builder forecast(String forecast) {
			this.forecast = forecast;
			return this;
		}
		
		public Location build() {
			return new Location(this);
		}
	}
}
