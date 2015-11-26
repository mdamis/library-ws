import java.rmi.Naming;
import java.util.List;

import javafx.application.Application;

public class LibraryClient {

	public static void main(String[] args) {
		try {
			/*if(args.length == 0) {
				System.err.println("usage: java LibraryClient username\n");
				return;
			}*/
			Library library = (Library) Naming.lookup("LibraryService");
			
			// some little users
			//Observer me = new ObserverImpl(args[0]);
			Observer me = new ObserverImpl("bcrochez");
			//Observer you = new ObserverImpl("mdamis");
			
			// add some little books
			library.add("0451524934", "1984", "George Orwell");
			library.add("8806203797", "1Q84", "Haruki Murakami");
			library.add("0460005251", "War and Peace", "Leo Tolstoy");
			library.add("1906141010", "And Then There Were None", "Agatha Christie");

			List<Book> books = library.getAllBooks();
			System.out.println(books.size());
			library.delete("0460005251");
			books = library.getAllBooks();
			System.out.println(books.size());

			for (Book book : books) {
				System.out.println(book.details());
				System.out.println(book.getSummary());
			}
			System.out.println("");

			// add some little reviews
			Book firstBook = books.get(0);
			firstBook.setSummary("Summary available");
			firstBook.addReview("Very good !");
			firstBook.addReview("Totally love it <3");
			firstBook.addReview("meh");

			//System.out.println(library.borrowBook("1906141010", you));

			books = library.getAllBooks();
			for (Book book : books) {
				System.out.println(book.details());
				System.out.println(book.getSummary());
			}

			System.out.println(library.borrowBook("1906141010", me));
			library.returnBook("1906141010", me);
			System.out.println(library.borrowBook("1906141010", me));
			//library.returnBook("1906141010", you);

			books = library.getAllBooks();
			for (Book book : books) {
				System.out.println(book.getReviews());
			}
			
			Application.launch(GUI.class, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
