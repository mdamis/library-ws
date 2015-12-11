package fr.upem.user;

import java.util.HashMap;

public class UserManager {
	
	private final HashMap<String, User> users;
	
	public UserManager() {
		users = new HashMap<>();
	}
	
	private HashMap<String, User> getUsers() {
		return users;
	}
	
	public boolean registerUser(String user, String password) {
		System.out.println(users.keySet());
		if(users.containsKey(user)) {
			return false;
		} else {
			User newUser = new User(user, password);
			users.put(user, newUser);
			newUser.setConnected(true);
			return true;
		}
	}
	
	public boolean exist(String user) {
		return users.containsKey(user);
	}
	
	public boolean connect(String user, String password) {
		if(!users.containsKey(user)) {
			return false;
		}
		User u = users.get(user);
		if(!u.getPassword().equals(password)) {
			return false;
		} else if(u.isConnected()) {
			return false;
		} else{
			u.setConnected(true);
			return true;
		}
	}
	
	public boolean disconnect(String user) {
		if(!users.containsKey(user)) {
			return false;
		}
		User u = users.get(user);
		if(!u.isConnected()) {
			return false;
		} else {
			u.setConnected(false);
			return true;
		}
	}

}
