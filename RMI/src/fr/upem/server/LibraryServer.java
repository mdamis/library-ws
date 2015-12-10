import java.rmi.Naming;
import java.rmi.RemoteException;
import java.net.MalformedURLException;

public class LibraryServer {
	private final Library library;

	private LibraryServer() throws RemoteException, MalformedURLException {
		library = new LibraryImpl();
		Naming.rebind("rmi://localhost:1099/LibraryService", library);
	}
	
	public static void main(String[] args) {
		try {
			LibraryServer server = new LibraryServer();

			// add some little reviews
			server.addBooks();
			server.addUsers();
			
			Book firstBook = server.library.getAllBooks().get(0);
			firstBook.setSummary("Summary available");
			firstBook.addReview("Very good !");
			firstBook.addReview("Totally love it <3");
			firstBook.addReview("meh");

			Naming.rebind("LibraryService", server.library);

			System.out.println("Server Ready");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addBooks() throws RemoteException {
		library.addBook("0451524934", "1984", "George Orwell", "2012-03-13");
		library.addBook("8806203797", "1Q84", "Haruki Murakami", "2012-03-13");
		library.addBook("0460005251", "War and Peace", "Leo Tolstoy", "2012-03-13");
		library.addBook("1906141010", "And Then There Were None", "Agatha Christie", "2012-03-13");
	}
	
	private void addUsers() throws RemoteException {
		library.addUser(new UserImpl("bcrochez"));
		library.addUser(new UserImpl("mdamis"));
		library.addUser(new UserImpl("mperouma"));
	}

}
