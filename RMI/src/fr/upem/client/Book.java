import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Book extends Remote {
	public String getISBN() throws RemoteException;

	public String getTitle() throws RemoteException;

	public String getAuthor() throws RemoteException;

	public boolean isAvailable() throws RemoteException;

	public User getPatron() throws RemoteException;

	public void setPatron(User patron) throws RemoteException;
	
	public String getPatronUsername() throws RemoteException;

	public void setHasBeenBorrowed(boolean hasBeenBorrowed) throws RemoteException;

	public boolean isSaleable() throws RemoteException;

	public String details() throws RemoteException;

	public String getSummary() throws RemoteException;

	public void setSummary(String summary) throws RemoteException;

	public List<String> getReviews() throws RemoteException;

	public void addReview(String review, User user) throws RemoteException;

}
