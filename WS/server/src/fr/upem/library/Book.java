package fr.upem.library;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Book {

	private final String isbn;
	private final String title;
	private final String author;
	private final LocalDate introductionDate;
	private final ArrayList<String> reviews = new ArrayList<String>();
	private final String currency = "EUR";

	private float price;
	private int nbExemplary = 1;
	private String summary = "No summary available";

	/**
	 * Default constructor
	 */
	public Book() {
		isbn = "0";
		title = "newTitle";
		author = "newAuthor";
		introductionDate = LocalDate.now();
	}

	/**
	 * 
	 * @param isbn the book's isbn
	 * @param title the book's title
	 * @param author the book's author name
	 * @param price the book's price
	 * @param introductionDate the book's introductionDate
	 */
	public Book(String isbn, String title, String author, float price,
			LocalDate introductionDate) {
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.price = price;
		this.introductionDate = introductionDate;
	}

	/**
	 * 
	 * @param isbn the book's isbn
	 * @param title the book's title
	 * @param author the book's author name
	 * @param price the book's price
	 * @param introductionDate the book's introductionDate
	 * @return an instance of Book
	 */
	public static Book create(String isbn, String title, String author,
			float price, String introductionDate) {
		LocalDate date;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		try {
			date = LocalDate.parse(introductionDate, formatter);
		} catch (DateTimeParseException e) {
			date = LocalDate.now();
		}
		return new Book(isbn, title, author, price, date);
	}

	/**
	 * 
	 * @return the book's isbn
	 */
	public String getISBN() {
		return isbn;
	}

	/**
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * Add exemplary of this book
	 * 
	 * @param exemplary number of book added
	 * @throws IllegalArgumentException when the number to add is invalid
	 */
	public void addExemplary(int exemplary) throws IllegalArgumentException {
		if (exemplary <= 0) {
			throw new IllegalArgumentException(
					"Invalid number of exemplary to add");
		} else {
			nbExemplary += exemplary;
		}
	}

	/**
	 * removed some exemplary of this book
	 * 
	 * @param exemplary the number of exemplary removed
	 * @throws IllegalArgumentException when exemplary is invalid
	 */
	public void removeExemplary(int exemplary) throws IllegalArgumentException {
		if (exemplary > nbExemplary || exemplary <= 0) {
			throw new IllegalArgumentException(
					"Invalid number of exemplary to remove");
		} else {
			nbExemplary -= exemplary;
		}
	}

	/**
	 * test if an exemplary is still available
	 * 
	 * @return true if book is available else false
	 */
	public boolean isAvailable() {
		return nbExemplary > 0;
	}

	/**
	 * test if we can sell this book
	 * 
	 * @return true if this book is sellable else false
	 */
	public boolean isSaleable() {
		return /* hasBeenBorrowed && LocalDate.now().getYear() - */introductionDate.getYear() >= 2;
	}

	/**
	 * Get information of this book
	 * 
	 * @return a string representation describing the book
	 */
	public String details() {
		if (isAvailable()) {
			return title + " written by " + author + " is available";
		} else {
			return title + " written by " + author + " is not available";
		}
	}

	/**
	 * 
	 * @return the summary of this book
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * 
	 * @param summary set the summary
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * 
	 * @return a String representation of all the review
	 */
	public String getReviews() {
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

	/**
	 * 
	 * @param review add the review to this book
	 */
	public void addReview(String review) {
		reviews.add(review);
	}

	/**
	 * 
	 * @param newPrice set a newPrice
	 */
	public void setPrice(float newPrice) {
		price = newPrice;
	}

	/**
	 * 
	 * @return get the book's price
	 */
	public float getPrice() {
		return price;
	}

	/**
	 * 
	 * @return the currency of the book
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * 
	 * @return the numbre of exemplary of this book
	 */
	public int getExemplary() {
		return nbExemplary;
	}

}
