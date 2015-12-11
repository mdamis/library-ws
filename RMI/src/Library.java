import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Library extends Remote {
	public boolean addBook(String isbn, String title, String author, String introductionDate) throws RemoteException;

	public void removeBook(Book book) throws RemoteException;

	public List<Book> getAllBooks() throws RemoteException;

	public String borrowBook(Book book, User user) throws RemoteException;

	public boolean returnBook(Book book, User user) throws RemoteException;
	
	public boolean addUser(User user) throws RemoteException;
	
	public List<User> getAllUsers() throws RemoteException;
	
	public User connect(String username) throws RemoteException;

}
