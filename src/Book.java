import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Book extends Remote {
	public String getISBN() throws RemoteException;

	public String getTitle() throws RemoteException;

	public String getAuthor() throws RemoteException;
}
