package fr.upem.library;

import java.util.HashMap;

public class Library {

	private final HashMap<String, Book> books = new HashMap<String, Book>();
	
	/**
	 * Default constructor
	 */
	public Library() {
		initLibrary();
	}

	/**
	 * Initialize the library with some books
	 */
	private void initLibrary() {
		Book newBook = Book.create("1", "War and Peace", "Leo Tolstoy", 25, "03-01-1982");
		books.put(newBook.getISBN(), newBook);
		newBook = Book.create("2", "1984", "George Orwell", 10, "01-01-2010");
		newBook.addExemplary(2);
		books.put(newBook.getISBN(), newBook);
	}

	/**
	 * Add a book to the library
	 * 
	 * @param isbn book's isbn
	 * @param title book's title
	 * @param author book's author
	 * @param price book's price
	 * @param introductionDate 
	 * @throws IllegalArgumentException
	 */
	public void add(String isbn, String title, String author, float price, String introductionDate) throws IllegalArgumentException {
		if(books.containsKey(isbn)) {
			Book book = books.get(isbn);
			if(title.equals(book.getTitle()) && author.equals(book.getAuthor())) {
				book.setPrice(price);
				book.addExemplary(1);
			} else {
				throw new IllegalArgumentException("A book with the same ISBN already exists");
			}
		} else {
			Book book = Book.create(isbn, title, author, price, introductionDate);
			books.put(isbn, book);
		}
	}
	
	/**
	 * Sells the book specified by the isbn
	 * @param isbn book's isbn
	 * @param exemplary number of exemplary sold
	 */
	public void sellBook(String isbn, int exemplary) {
		if(!books.containsKey(isbn)) {
			throw new IllegalArgumentException("This book doesn't exist");
		} else {
			books.get(isbn).removeExemplary(exemplary);
		}
	}

	/**
	 * get all the books in a Array of Book
	 * @return an array of all the library's book
	 */
	public Book[] getAllBooks() {
		Book[] tmp = new Book[books.size()];
		return books.values().toArray(tmp);
	}

	/*public String borrowBook(String isbn, User requester) {
		System.out.println(requester.getUser() +" is requesting book "+isbn);
		if (books.containsKey(isbn)) {
			Book book = books.get(isbn);
			book.setHasBeenBorrowed(true);
			String user = requester.getUser();
			if (book.isAvailable()) {
				book.setAvailable(false);
				book.setCurrentPatron(user);
				return "Book borrowed";
			} else {
				if(book.getCurrentPatron().equals(user)) {
					return "You already borrowed that book!";
				}
				book.addToQueue(requester);
				return "You are in the waiting queue";
			}
		}
		return "This book does not exists";
	}*/

	/*public boolean returnBook(String isbn, User user) {
		System.out.println(user.getUser() + " is trying to return the book " + isbn);
		if (books.containsKey(isbn)) {
			Book book = books.get(isbn);
			if(!user.getUser().equals(book.getCurrentPatron())) {
				return false;
			}
			System.out.println(book.getCurrentPatron()+" is returning book "+isbn);
			book.setCurrentPatron();
			if(book.getCurrentPatron().equals("")) {
				book.setAvailable(true);
			} else {
				book.setAvailable(false);
			}
			System.out.println("new patron is "+book.getCurrentPatron());
			return true;
		}
		return false;
	}*/

	/*public List<Book> getBorrowedBooks(User user) {
		ArrayList<Book> borrowedBooks = new ArrayList<Book>();
		for(String key : books.keySet()) {
			Book book = books.get(key);
			if (book.getCurrentPatron().equals(user.getUser())) {
				borrowedBooks.add(book);
			}
		}
		return borrowedBooks;
	}*/

}
