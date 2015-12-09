import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface User extends Remote {
	public String getUsername() throws RemoteException;
	public void bookBorrowed(Book book) throws RemoteException;
	public void bookReturned(Book book) throws RemoteException;
	public List<Book> getBorrowedBooks() throws RemoteException;
}
