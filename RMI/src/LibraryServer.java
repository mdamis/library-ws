import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;

public class LibraryServer {
	private final Library library;

	private LibraryServer() throws RemoteException {
		library = new LibraryImpl();
	}
	
	public static void main(String[] args) {
		try {
			LibraryServer server = new LibraryServer();

			// add some little reviews
			server.addBooks();
			server.addUsers();
			
			Book firstBook = server.library.getAllBooks().get(0);
			firstBook.setSummary("Summary available");
			List<User> users = server.library.getAllUsers();
			firstBook.addReview("Very good !", users.get(0));
			firstBook.addReview("Totally\nLove\nit\n<3", users.get(2));
			firstBook.addReview("meh", users.get(1));

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
