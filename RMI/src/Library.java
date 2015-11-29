import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Library extends Remote {
	public void add(String isbn, String title, String author) throws RemoteException;

	public void delete(String isbn) throws RemoteException;

	public List<Book> getAllBooks() throws RemoteException;

	public String borrowBook(String isbn, Observer obs) throws RemoteException;

	public boolean returnBook(String isbn, Observer obs) throws RemoteException;

	public List<Book> getBorrowedBooks(Observer obs) throws RemoteException;

}
