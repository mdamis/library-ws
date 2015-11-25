import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LibraryImpl extends UnicastRemoteObject implements Library {

	private static final long serialVersionUID = -1744409560851948200L;

	private final HashMap<String, Book> books = new HashMap<>();

	public LibraryImpl() throws RemoteException {
		super();
	}

	@Override
	public void add(String isbn, String title, String author) throws RemoteException {
		Book book = new BookImpl(isbn, title, author);
		books.put(isbn, book);
	}

	@Override
	public void delete(String isbn) {
		if (books.containsKey(isbn)) {
			books.remove(isbn);
		}
	}

	@Override
	public List<Book> getAllBooks() {
		return new ArrayList<>(books.values());
	}

	@Override
	public void borrowBook(String isbn, String user) throws RemoteException {
		if (books.containsKey(isbn)) {
			Book book = books.get(isbn);
			if (book.isAvailable()) {
				book.setAvailable(false);
				book.setCurrentPatron(user);
			} else {
				System.out.println("Book not available");
				// TODO Add a queue
			}
		}
	}

	@Override
	public void returnBook(String isbn) throws RemoteException {
		if (books.containsKey(isbn)) {
			Book book = books.get(isbn);
			book.setAvailable(true);
			book.setCurrentPatron("");
		}
	}

}
