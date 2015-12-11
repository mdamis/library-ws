package fr.upem.user;

import java.util.HashMap;

public class UserManager {
	
	/**
	 * List of the registered users
	 */
	private final HashMap<String, User> users;
	
	/**
	 * Default constructor
	 */
	public UserManager() {
		users = new HashMap<>();
		users.put("bcrochez", new User("bcrochez", "abc"));
		users.put("mdamis", new User("mdamis", "123"));
		users.put("mperou", new User("mperou", "abc123"));
	}
	
	/**
	 * Function to register an user
	 * 
	 * @param user name of the user
	 * @param password user's password
	 * @return true if the user has been correctly registered, false otherwise
	 */
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
	
	/**
	 * Tests if an user exist
	 * 
	 * @param user name to test
	 * @return true if the user exists, false otherwise
	 */
	public boolean exist(String user) {
		return users.containsKey(user);
	}
	
	/**
	 * Connects an user
	 * @param user user's name
	 * @param password user's password
	 * @return true if the user has been correctly connected, false otherwise
	 */
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
	
	/**
	 * Disconnects an user
	 * 
	 * @param user user's name
	 * @return true if the user has been correctly disconnected, false otherwise
	 */
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