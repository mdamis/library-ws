package fr.upem.library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ws.rs.Path;

import fr.upem.book.Book;
import fr.upem.user.User;

@Path("/User")
public class Library {

	private final HashMap<String, Book> books = new HashMap<String, Book>();

	public void add(String isbn, String title, String author, String introductionDate) {
		Book book = Book.create(isbn, title, author, introductionDate);
		books.put(isbn, book);
	}

	public void delete(String isbn) {
		if (books.containsKey(isbn)) {
			books.remove(isbn);
		}
	}

	public List<Book> getAllBooks() {
		return new ArrayList<Book>(books.values());
	}

	public String borrowBook(String isbn, User requester) {
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
	}

	public boolean returnBook(String isbn, User user) {
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
	}

	public List<Book> getBorrowedBooks(User user) {
		ArrayList<Book> borrowedBooks = new ArrayList<Book>();
		for(String key : books.keySet()) {
			Book book = books.get(key);
			if (book.getCurrentPatron().equals(user.getUser())) {
				borrowedBooks.add(book);
			}
		}
		return borrowedBooks;
	}

}
