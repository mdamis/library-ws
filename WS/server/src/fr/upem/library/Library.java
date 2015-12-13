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
		Book newBook = Book.create("1", "War and Peace", "Leo Tolstoy", 25,
				"03-01-1982");
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
	public void add(String isbn, String title, String author, float price,
			String introductionDate) throws IllegalArgumentException {
		if (books.containsKey(isbn)) {
			Book book = books.get(isbn);
			if (title.equals(book.getTitle())
					&& author.equals(book.getAuthor())) {
				book.setPrice(price);
				book.addExemplary(1);
			} else {
				throw new IllegalArgumentException(
						"A book with the same ISBN already exists");
			}
		} else {
			Book book = Book.create(isbn, title, author, price,
					introductionDate);
			books.put(isbn, book);
		}
	}

	/**
	 * Sells the book specified by the isbn
	 * 
	 * @param isbn book's isbn
	 * @param exemplary number of exemplary sold
	 */
	public void sellBook(String isbn, int exemplary) {
		if (!books.containsKey(isbn)) {
			throw new IllegalArgumentException("This book doesn't exist");
		} else {
			books.get(isbn).removeExemplary(exemplary);
		}
	}

	/**
	 * get all the books in a Array of Book
	 * 
	 * @return an array of all the library's book
	 */
	public Book[] getAllBooks() {
		Book[] tmp = new Book[books.size()];
		return books.values().toArray(tmp);
	}

	/**
	 * Get a book by its ISBN
	 * 
	 * @param isbn book's ISBN
	 * @return the book
	 */
	public Book getBook(String isbn) {
		if (books.containsKey(isbn)) {
			return books.get(isbn);
		} else {
			throw new IllegalArgumentException("invalid isbn");
		}
	}

}
