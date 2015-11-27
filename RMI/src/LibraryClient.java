import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import javafx.application.Application;

public class LibraryClient {
	private final Library library;
	private final Observer observer;
	
	public LibraryClient(String user, String serviceName) throws MalformedURLException, RemoteException, NotBoundException {
		library = (Library) Naming.lookup(serviceName);
		observer = new ObserverImpl(user);
	}

	public List<Book> getAllBooks() throws RemoteException {
		return library.getAllBooks();
	}

	public String getUser() {
		try {
			return observer.getUser();
		} catch (RemoteException e) {
			e.printStackTrace();
			return "LOGIN ERROR";
		}
	}

	public void addBook(String isbn, String title, String author) {
		try {
			library.add(isbn, title, author);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			Library library = (Library) Naming.lookup("LibraryService");
			
			// some little users
			//Observer me = new ObserverImpl(args[0]);
			Observer me = new ObserverImpl("bcrochez");
			//Observer you = new ObserverImpl("mdamis");
			
			

			List<Book> books = library.getAllBooks();
			/*System.out.println(books.size());
			library.delete("0460005251");
			books = library.getAllBooks();
			System.out.println(books.size());*/

			for (Book book : books) {
				System.out.println(book.details());
				System.out.println(book.getSummary());
			}
			System.out.println("");

			//System.out.println(library.borrowBook("1906141010", you));

			/*books = library.getAllBooks();
			for (Book book : books) {
				System.out.println(book.details());
				System.out.println(book.getSummary());
			}*/

			System.out.println(library.borrowBook("1906141010", me));
			library.returnBook("1906141010", me);
			System.out.println(library.borrowBook("1906141010", me));
			//library.returnBook("1906141010", you);

			//books = library.getAllBooks();
			for (Book book : books) {
				System.out.println(book.getReviews());
			}
			
			Application.launch(GUI.class, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
