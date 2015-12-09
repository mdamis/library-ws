package fr.upem.book;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import fr.upem.user.User;

public class Book {

	private final String isbn;
	private final String title;
	private final String author;
	private boolean available = true;
	private String currentPatron = "";
	private String summary = "No summary available";
	private final LocalDate introductionDate;
	private boolean hasBeenBorrowed = false;
	private final ArrayList<String> reviews = new ArrayList<String>();
	private final ArrayList<User> borrowList = new ArrayList<User>();

	private Book(String isbn, String title, String author, LocalDate introductionDate) {
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.introductionDate = introductionDate;
	}

	public static Book create(String isbn, String title, String author, String introductionDate) {
		LocalDate date;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		try {
			date = LocalDate.parse(introductionDate, formatter);
		} catch(DateTimeParseException e) {
			date = LocalDate.now();
		}
		return new Book(isbn, title, author, date);
	}

	public String getISBN() {
		return isbn;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public String getCurrentPatron() {
		return currentPatron;
	}

	public void setCurrentPatron(String currentPatron) {
		this.currentPatron = currentPatron;
	}

	public void setHasBeenBorrowed(boolean hasBeenBorrowed) {
		this.hasBeenBorrowed = hasBeenBorrowed;
	}

	public boolean isSaleable() {
		return hasBeenBorrowed && LocalDate.now().getYear() - introductionDate.getYear() >= 2;
	}

	public String details() {
		if (available) {
			return title + " written by " + author + " is available";
		} else {
			return title + " written by " + author + " is not available";
		}
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getReviews() {
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

	public void addReview(String review) {
		reviews.add(review);
	}

	public void addToQueue(User requester) {
		for(User user : borrowList) {
			if(user.getUser().equals(requester.getUser())) {
				System.out.println(requester.getUser() +" already in the queue");
				return;
			}
		}
		System.out.println(requester.getUser() +" added to the queue");
		borrowList.add(requester);
	}

	public void setCurrentPatron() {
		if(!borrowList.isEmpty()) {
			System.out.println("borrow list is not empty");
			User user = borrowList.remove(0);
			currentPatron = user.getUser();
			user.bookBorrowed(this);
		} else {
			currentPatron = "";
		}
	}

}
