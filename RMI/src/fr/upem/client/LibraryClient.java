import javafx.application.Application;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.util.List;

@SuppressWarnings("deprecation")
public class LibraryClient {
	private final Library library;
	private User user = null;
	private String styleSheetPath = "styleA.css";

	public LibraryClient() throws MalformedURLException, RemoteException, NotBoundException {
		library = (Library) Naming.lookup("rmi://localhost:1099/LibraryService");
	}

	public Library getLibrary() {
		return library;
	}

	public List<Book> getAllBooks() throws RemoteException {
		return library.getAllBooks();
	}

	public User getUser() {
		return user;
	}
	
	public String getStyleSheetPath() {
		return styleSheetPath;
	}
	
	public void switchStyleSheet() {
		if(styleSheetPath.equals("styleA.css")) {
			styleSheetPath = "styleB.css";
		} else {
			styleSheetPath = "styleA.css";
		}
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public String getUsername() {
		try {
			return user.getUsername();
		} catch (RemoteException e) {
			e.printStackTrace();
			return "LOGIN ERROR";
		}
	}

	public void addBook(String isbn, String title, String author) {
		try {
			library.addBook(isbn, title, author, "now");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void addBook(String isbn, String title, String author, String introductionDate) {
		try {
			library.addBook(isbn, title, author, introductionDate);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			String codebase = "file:../server/";
			System.setProperty("java.rmi.server.codebase", codebase);
			System.setProperty("java.security.policy", "library.policy");
			System.setSecurityManager(new RMISecurityManager());
			
			
			Application.launch(GUI.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
