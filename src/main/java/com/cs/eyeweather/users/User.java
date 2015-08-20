package com.cs.eyeweather.users;

public class User {

	private String id;
	private String email;
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private String role;
	private boolean enabled;
	
	public User(){
		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public String getId(){
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	private User(Builder b) {
		this.id = b.id;
		this.email = b.email;
		this.firstName = b.firstName;
		this.lastName = b.lastName;
		this.userName = b.userName;
		this.password = b.password;
		this.role = b.role;
		this.enabled = b.enabled;
	}
	
	public static class Builder {
		private String id;
		private String email;
		private String firstName;
		private String lastName;
		private String userName;
		private String password;
		private String role;
		private boolean enabled;
		
		public Builder id(String id) {
			this.id = id;
			return this;
		}
		
		public Builder email(String email) {
			this.email = email;
			return this;
		}
		
		public Builder firstName(String firstName) {
			this.firstName = firstName;
			return this;
		}
		
		public Builder lastName(String lastName) {
			this.lastName = lastName;
			return this;
		}
		
		public Builder userName(String userName) {
			this.userName = userName;
			return this;
		}
		
		public Builder password(String password) {
			this.password = password;
			return this;
		}
		
		public Builder role(String role) {
			this.role = role;
			return this;
		}
		
		public Builder enabled(boolean enabled) {
			this.enabled = enabled;
			return this;
		}
		
		public User build() {
			return new User(this);
		}
	}
}

