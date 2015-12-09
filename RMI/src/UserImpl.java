import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * This class must be instancied to borrow a book
 * 
 * @author bcrochez
 *
 */
public class UserImpl extends UnicastRemoteObject implements User {

	private static final long serialVersionUID = -1706698189361413223L;
	private final String username;
	private final ArrayList<Book> borrowedBooks = new ArrayList<>();

	protected UserImpl(String username) throws RemoteException {
		super();
		this.username = username;
	}

	@Override
	public void bookBorrowed(Book book) throws RemoteException {
		borrowedBooks.add(book);
		System.out.println("You borrowed the book " + book.getISBN());
	}

	@Override
	public void bookReturned(Book book) throws RemoteException {
		borrowedBooks.remove(book);
		System.out.println("You returned the book " + book.getISBN());
	}

	@Override
	public String getUsername() throws RemoteException {
		return username;
	}

	@Override
	public List<Book> getBorrowedBooks() throws RemoteException {
		return borrowedBooks;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof UserImpl)) {
			return false;
		}
		UserImpl user = (UserImpl) obj;
		return username.equals(user.username);
	}

}
