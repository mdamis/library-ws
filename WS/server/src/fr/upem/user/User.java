package fr.upem.user;

/**
 * @author bcrochez
 *
 */
public class User {

	private final String user;
	private String password;
	private boolean isConnected = false;

	/**
	 * Constructor
	 */
	public User(String user, String password) {
		this.user = user;
		this.password = password;
	}
	
	/**
	 * 
	 * @return the user's id
	 */
	public String getUser() {
		return user;
	}
	
	/**
	 * 
	 * @return the user's password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * 
	 * @param password set new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 
	 * @return true if user is connected else false
	 */
	public boolean isConnected() {
		return isConnected;
	}

	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}
	
	@Override
	public String toString() {
		return user + " : " + password;
	}

}
