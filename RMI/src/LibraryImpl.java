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
	public String borrowBook(String isbn, Observer obs) throws RemoteException {
		System.out.println(obs.getUser() +" is requesting book "+isbn);
		if (books.containsKey(isbn)) {
			Book book = books.get(isbn);
			String user = obs.getUser();
			if (book.isAvailable()) {
				book.setAvailable(false);
				book.setCurrentPatron(user);
				return "Book borrowed";
			} else {
				if(book.getCurrentPatron().equals(user)) {
					return "You already borrowed that book!";
				}
				book.addToQueue(obs);
				return "You are in the waiting queue";
			}
		}
		return "This book does not exists";
	}

	@Override
	public void returnBook(String isbn) throws RemoteException {
		// TODO give user as parameter to test who gives the book back
		if (books.containsKey(isbn)) {
			Book book = books.get(isbn);
			System.out.println(book.getCurrentPatron()+" is returning book "+isbn);
			book.setCurrentPatron();
			if(book.getCurrentPatron().equals("")) {
				book.setAvailable(true);
			} else {
				book.setAvailable(false);
			}
			System.out.println("new patron is "+book.getCurrentPatron());
		}
	}

}
