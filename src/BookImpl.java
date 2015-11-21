import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class BookImpl extends UnicastRemoteObject implements Book {

	private static final long serialVersionUID = -5608017396352341335L;

	private final String isbn;
	private final String title;
	private final String author;

	public BookImpl(String isbn, String title, String author) throws RemoteException {
		super();
		this.isbn = isbn;
		this.title = title;
		this.author = author;
	}

	@Override
	public String getISBN() {
		return isbn;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getAuthor() {
		return author;
	}

}
