package fr.upem.library;

/**
 * This class must be instancied to borrow a book
 * @author bcrochez
 *
 */
public class User {

	private final String user;

	protected User(String user) {
		this.user = user;
	}

	public void bookBorrowed(Book book) {
		System.out.println("You got the book "+book.getISBN());
	}
	
	public String getUser() {
		return user;
	}

}
