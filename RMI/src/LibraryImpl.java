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
	public boolean returnBook(String isbn, Observer obs) throws RemoteException {
		System.out.println(obs.getUser() + " is trying to return the book " + isbn);
		if (books.containsKey(isbn)) {
			Book book = books.get(isbn);
			if(!obs.getUser().equals(book.getCurrentPatron())) {
				return false;
			}
			System.out.println(book.getCurrentPatron()+" is returning book "+isbn);
			book.setCurrentPatron();
			if(book.getCurrentPatron().equals("")) {
				book.setAvailable(true);
			} else {
				book.setAvailable(false);
			}
			System.out.println("new patron is "+book.getCurrentPatron());
			return true;
		}
		return false;
	}

	@Override
	public List<Book> getBorrowedBooks(Observer obs) throws RemoteException {
		ArrayList<Book> borrowedBooks = new ArrayList<>();
		for(String key : books.keySet()) {
			Book book = books.get(key);
			if (book.getCurrentPatron().equals(obs.getUser())) {
				borrowedBooks.add(book);
			}
		}
		return borrowedBooks;
	}

}
