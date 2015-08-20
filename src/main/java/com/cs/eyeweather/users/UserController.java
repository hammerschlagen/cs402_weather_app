package com.cs.eyeweather.users;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cs.eyeweather.locations.Location;
import com.cs.eyeweather.locations.LocationService;

@Controller
public class UserController {
	@Autowired
	LocationService locService;
	@Autowired
	UserService userService;

	@RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
	public String redirect(){
		return "redirect:login.html";
	}
	
	@RequestMapping(value = {"/login/login"}, method = RequestMethod.POST)
	public String login(@RequestParam String username, @RequestParam String password, HttpSession session){
		
		User user = userService.getUser(username, password);
		
		if (user == null){
			return "redirect:login.html?error=true";
		}
		
		session.setAttribute("user", user);		
		
		return "redirect:/locations.html?user=" + user.getId();
	}
	
	@RequestMapping(value = "users/{userId}/locations", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Location> get(@PathVariable String userId, HttpSession session) {
		User user = (User)session.getAttribute("user");
		if(user != null && user.getId().equals(userId)){
			return getLocations(userId);
		}
		return null;
	}
	
	@RequestMapping(value = "users/{userId}/locations/{lid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Location> deleteLocation(@PathVariable String userId, @PathVariable String lid){
		
		Location loc = locService.getLocationById(lid);
		if (loc != null){
			locService.deleteLocation(userId, loc);
		}
		return getLocations(userId);
	}
	
	@RequestMapping(value = "users/{userId}/locations", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value=HttpStatus.OK)
	public @ResponseBody List<Location> addLocation(@PathVariable String userId,@RequestBody String latlon) throws ClientProtocolException, URISyntaxException, IOException{
		User currentUser = userService.getUserById(userId);
		if (currentUser != null){
			locService.addLocation(currentUser, latlon);
		}
		return getLocations(userId);
	}
	
	//returns sorted list of last 20 requests
	public List<Location> getLocations(String userId){
		List<Location> locations = null;
		
		try {
			locations = locService.findAllByUsername(userId);
		} catch (Exception e) {
		}
		
		Collections.sort(locations, new Comparator<Location>() {
			@Override
			public int compare(Location r1, Location r2) {
				return r2.getReqTime().compareTo(r1.getReqTime());
			}
			
		});

		if (locations.size() > 19){
			return locations.subList(0, 19);
		}
		return locations;
	}
	
}
