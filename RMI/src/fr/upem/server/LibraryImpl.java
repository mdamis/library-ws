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
	private final ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();

	public LibraryImpl() throws RemoteException {
		super();
	}

	@Override
	public boolean addBook(String isbn, String title, String author, String introductionDate) throws RemoteException {
		if(books.containsKey(isbn)) {
			return false;
		}
		Book book = BookImpl.create(isbn, title, author, introductionDate);
		books.put(isbn, book);
		waitingLists.put(isbn, new ArrayBlockingQueue<>(10));
		return true;
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
	public List<Book> searchBook(String query) throws RemoteException {
		List<Book> searchResults = new ArrayList<>();
		for(String isbn : books.keySet()) {
			Book book = books.get(isbn);
			if(book.getTitle().contains(query) || book.getAuthor().contains(query)) {
				searchResults.add(book);
			}
		}
		return searchResults;
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
			if(user != null) {
				user.bookBorrowed(book);
			}
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
			System.out.println(book.getPatron().getUsername() + " is returning book " + book.getISBN());
			updatePatron(book);
			user.bookReturned(book);
			if(book.getPatron() != null) {
				System.out.println("new patron is " + book.getPatron().getUsername());
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean addUser(User user) throws RemoteException {
		if(users.containsKey(user.getUsername())) {
			return false;
		} else {
			users.put(user.getUsername(), user);
			return true;
		}
	}

	@Override
	public User connect(String username) throws RemoteException {
		if(users.containsKey(username)) {
			System.out.println(username + " is now connected.");
			return users.get(username);
		}
		return null;
	}

	@Override
	public List<User> getAllUsers() throws RemoteException {
		ArrayList<User> userList = new ArrayList<>();
		for(String username : users.keySet()) {
			userList.add(users.get(username));
		}
		return userList;
	}

}
