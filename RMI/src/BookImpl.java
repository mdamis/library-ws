import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class BookImpl extends UnicastRemoteObject implements Book {

	private static final long serialVersionUID = -5608017396352341335L;

	private final String isbn;
	private final String title;
	private final String author;
	private boolean available = true;
	private String currentPatron = "";
	private String summary = "No summary available";
	private final ArrayList<String> reviews = new ArrayList<>();
	private final ArrayList<Observer> borrowList = new ArrayList<>();

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

	@Override
	public boolean isAvailable() throws RemoteException {
		return available;
	}

	@Override
	public void setAvailable(boolean available) throws RemoteException {
		this.available = available;
	}

	@Override
	public String getCurrentPatron() throws RemoteException {
		return currentPatron;
	}

	@Override
	public void setCurrentPatron(String currentPatron) throws RemoteException {
		this.currentPatron = currentPatron;
	}

	@Override
	public String details() {
		if (available) {
			return title + " written by " + author + " is available";
		} else {
			return title + " written by " + author + " is not available";
		}
	}

	@Override
	public String getSummary() throws RemoteException {
		return summary;
	}

	@Override
	public void setSummary(String summary) throws RemoteException {
		this.summary = summary;
	}

	@Override
	public String getReviews() throws RemoteException {
		if(reviews.size() == 0) {
			return "No reviews for : " + title;
		}

		StringBuilder sb = new StringBuilder();
		sb.append("Reviews for ").append(title).append(" : \n");

		for(String review : reviews) {
			sb.append(review).append("\n");
		}

		return sb.toString();
	}

	@Override
	public void addReview(String review) throws RemoteException {
		reviews.add(review);
	}

	@Override
	public void addToQueue(Observer obs) throws RemoteException {
		System.out.println(obs.getUser() +" add to queue");
		borrowList.add(obs);
	}

	@Override
	public void setCurrentPatron() throws RemoteException {
		if(!borrowList.isEmpty()) {
			System.out.println("borrow list is not empty");
			Observer obs = borrowList.remove(0);
			currentPatron = obs.getUser();
			obs.bookBorrowed(this);
		} else {
			currentPatron = "";
		}
	}

}
