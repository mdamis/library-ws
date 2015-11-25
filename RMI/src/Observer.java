import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Observer extends Remote {
	public String getUser() throws RemoteException;
	public void bookBorrowed(Book book) throws RemoteException;
}
