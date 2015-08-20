package com.cs.eyeweather.users;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	private List<User> users = new ArrayList<>();
	
	@PostConstruct
	public void init() throws IOException {
		
		String userDB = "/tmp/users.db";
		//String userDB = "C:\\Users\\Alex\\Documents\\users.db";
		BufferedReader b = null;
		String line = "";
		final String DELIM = ",";
		
		b = new BufferedReader(new FileReader(userDB));
		
		while ((line = b.readLine()) != null){
			String[] tokens = line.split(DELIM);
			User tmpUser = new User.Builder().id(tokens[0])
								.email(tokens[1])
								.firstName(tokens[2])
								.lastName(tokens[3])
								.userName(tokens[4])
								.password(tokens[5])
								.role(tokens[6])
								.enabled((tokens[7].equalsIgnoreCase("true")) ? true : false)
								.build();
			
			users.add(tmpUser);
			System.out.println(tmpUser.toString());
		}
		b.close();
	}
	
	public User getUser(String userName, String password) {
		if(userName == null || password == null) return null;
		for(User user : users) {
			if(user.getUserName().equals(userName) && user.getPassword().equals(password)) return user;
		}
		return null;
	}
	
	public User getUserById(String userId){
		
		for(User user : users){
			if(user.getId().equals(userId)){
				return user;
			}	
		}
		return null;
	}
}