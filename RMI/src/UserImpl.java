import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * This class must be instancied to borrow a book
 * @author bcrochez
 *
 */
public class UserImpl extends UnicastRemoteObject implements User {
	
	private static final long serialVersionUID = -1706698189361413223L;
	private final String user;

	protected UserImpl(String user) throws RemoteException {
		super();
		this.user = user;
	}

	@Override
	public void bookBorrowed(Book book) throws RemoteException {
		System.out.println("You got the book "+book.getISBN());
	}
	
	@Override
	public String getUser()  throws RemoteException {
		return user;
	}

}
