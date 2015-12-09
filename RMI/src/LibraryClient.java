import javafx.application.Application;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public class LibraryClient {
	private final Library library;
	private User user = null;

	public LibraryClient() throws MalformedURLException, RemoteException, NotBoundException {
		library = (Library) Naming.lookup("LibraryService");
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
			
			Application.launch(GUI.class, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
