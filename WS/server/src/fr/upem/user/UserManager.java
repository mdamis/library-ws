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
	
	public void registerUser(String user, String password) throws IllegalArgumentException {
		if(users.containsKey(user)) {
			throw new IllegalArgumentException("This user already exists");
		} else {
			users.put(user, new User(user, password));
		}
		
	}
	
	public boolean exist(String user,String password) {
		if(users.containsKey(user)) {
			return password ==users.get(user).getPassword();
		}
		return false;
	}
	
	public String getUsernameConnected() {
		for(User u:users.values()) {
			if(u.isConnected()) {
				return u.getUser();
			}
		}
		return null;
	}
	
	public boolean connect(String user, String password) throws IllegalArgumentException {
		if(!users.containsKey(user)) {
			throw new IllegalArgumentException("This user doesn't exists");
		}
		User u = users.get(user);
		if(!u.getPassword().equals(password)) {
			throw new IllegalArgumentException("Wrong password");
		} else if(u.isConnected()) {
			throw new IllegalArgumentException("This user is already connected");
		} else{
			u.setConnected(true);
			return true;
		}
	}
	
	public boolean disconnect(String user) throws IllegalArgumentException {
		if(!users.containsKey(user)) {
			throw new IllegalArgumentException("This user doesn't exist");
		}
		User u = users.get(user);
		if(!u.isConnected()) {
			throw new IllegalArgumentException("This user is not connected");
		} else {
			u.setConnected(false);
			return true;
		}
	}

}
