import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Library extends Remote {
	public void add(String isbn, String title, String author) throws RemoteException;

	public void delete(String isbn) throws RemoteException;

	public List<Book> getAllBooks() throws RemoteException;

	public void borrowBook(String isbn, String user) throws RemoteException;

	public void returnBook(String isbn) throws RemoteException;
}
