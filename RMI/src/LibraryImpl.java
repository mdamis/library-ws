import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class LibraryImpl extends UnicastRemoteObject implements Library {

	private static final long serialVersionUID = -1744409560851948200L;

	private final ConcurrentHashMap<String, Book> books = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<String, ArrayBlockingQueue<User>> waitingLists = new ConcurrentHashMap<>();

	public LibraryImpl() throws RemoteException {
		super();
	}

	@Override
	public void addBook(String isbn, String title, String author, String introductionDate) throws RemoteException {
		Book book = BookImpl.create(isbn, title, author, introductionDate);
		books.put(isbn, book);
		waitingLists.put(isbn, new ArrayBlockingQueue<>(10));
	}

	@Override
	public void removeBook(Book book) throws RemoteException {
		books.remove(book.getISBN());
		waitingLists.remove(book.getISBN());
	}

	@Override
	public List<Book> getAllBooks() {
		return new ArrayList<>(books.values());
	}

	@Override
	public String borrowBook(Book requestedBook, User user) throws RemoteException {
		System.out.println(user.getUsername() + " is requesting book " + requestedBook.getISBN());
		if (books.containsKey(requestedBook.getISBN())) {
			Book book = books.get(requestedBook.getISBN());
			book.setHasBeenBorrowed(true);
			if (book.isAvailable()) {
				addToWaitingList(book.getISBN(), user);
				updatePatron(book);
				return "Book borrowed";
			} else {
				if (user.equals(book.getPatron())) {
					return "You already borrowed that book!";
				}
				if (isWaiting(book.getISBN(), user)) {
					return "You already were in the waiting list";
				} else {
					if (addToWaitingList(book.getISBN(), user)) {
						return "You are in the waiting list";
					} else {
						return "The waiting list is full, try again later";
					}
				}
			}
		}
		return "This book does not exist in our library";
	}

	private boolean isWaiting(String isbn, User user) {
		if (waitingLists.containsKey(isbn)) {
			ArrayBlockingQueue<User> queue = waitingLists.get(isbn);
			return queue.contains(user);
		}
		return false;
	}

	private boolean addToWaitingList(String isbn, User user) {
		if (!waitingLists.containsKey(isbn)) {
			waitingLists.put(isbn, new ArrayBlockingQueue<>(10));
		}
		ArrayBlockingQueue<User> queue = waitingLists.get(isbn);
		return queue.add(user);
	}

	private void updatePatron(Book book) throws RemoteException {
		if (waitingLists.containsKey(book.getISBN())) {
			ArrayBlockingQueue<User> queue = waitingLists.get(book.getISBN());
			User user = queue.poll();
			book.setPatron(user);
			user.bookBorrowed(book);
		}
	}

	@Override
	public boolean returnBook(Book borrowedBook, User user) throws RemoteException {
		System.out.println(user.getUsername() + " is trying to return the book " + borrowedBook.getISBN());
		if (books.containsKey(borrowedBook.getISBN())) {
			Book book = books.get(borrowedBook.getISBN());
			if (!user.equals(book.getPatron())) {
				return false;
			}
			System.out.println(book.getPatron() + " is returning book " + book.getISBN());
			updatePatron(book);
			user.bookReturned(book);
			System.out.println("new patron is " + book.getPatron());
			return true;
		}
		return false;
	}

}
