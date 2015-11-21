import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Book extends Remote {
	public String getISBN() throws RemoteException;

	public String getTitle() throws RemoteException;

	public String getAuthor() throws RemoteException;

	public boolean isAvailable() throws RemoteException;

	public void setAvailable(boolean available) throws RemoteException;

	public String getCurrentPatron() throws RemoteException;

	public void setCurrentPatron(String currentPatron) throws RemoteException;

	public String details() throws RemoteException;
}
