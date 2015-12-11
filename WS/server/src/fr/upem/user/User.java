package fr.upem.user;

/**
 * @author bcrochez
 *
 */
public class User {

	private final String user;
	private String password;
	private boolean isConnected = false;

	public User(String user, String password) {
		this.user = user;
		this.password = password;
	}
	
	public String getUser() {
		return user;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

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
