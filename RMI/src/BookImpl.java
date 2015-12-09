import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class BookImpl extends UnicastRemoteObject implements Book {

	private static final long serialVersionUID = -5608017396352341335L;

	private final String isbn;
	private final String title;
	private final String author;
	private User patron = null;
	private String summary = "No summary available";
	private final LocalDate introductionDate;
	private boolean hasBeenBorrowed = false;
	private final ArrayList<String> reviews = new ArrayList<>();

	private BookImpl(String isbn, String title, String author, LocalDate introductionDate) throws RemoteException {
		super();
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.introductionDate = introductionDate;
	}

	public static BookImpl create(String isbn, String title, String author, String introductionDate)
			throws RemoteException {
		LocalDate date;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		try {
			date = LocalDate.parse(introductionDate, formatter);
		} catch (DateTimeParseException e) {
			date = LocalDate.now();
		}
		return new BookImpl(isbn, title, author, date);
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
		return patron == null;
	}

	@Override
	public User getPatron() throws RemoteException {
		return patron;
	}

	@Override
	public void setPatron(User patron) throws RemoteException {
		this.patron = patron;
	}

	@Override
	public void setHasBeenBorrowed(boolean hasBeenBorrowed) throws RemoteException {
		this.hasBeenBorrowed = hasBeenBorrowed;
	}

	@Override
	public boolean isSaleable() throws RemoteException {
		return hasBeenBorrowed && LocalDate.now().getYear() - introductionDate.getYear() >= 2;
	}

	@Override
	public String details() throws RemoteException {
		if (isAvailable()) {
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
		if (reviews.size() == 0) {
			return "No reviews for : " + title;
		}

		StringBuilder sb = new StringBuilder();
		sb.append("Reviews for ").append(title).append(" : \n");

		for (String review : reviews) {
			sb.append(review).append("\n");
		}

		return sb.toString();
	}

	@Override
	public void addReview(String review) throws RemoteException {
		reviews.add(review);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof BookImpl)) {
			return false;
		}
		BookImpl book = (BookImpl) obj;
		return isbn.equals(book.isbn);
	}

}
