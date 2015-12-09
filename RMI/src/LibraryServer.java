import java.rmi.Naming;

public class LibraryServer {

	public static void main(String[] args) {
		try {
			Library library = new LibraryImpl();

			// add some little books
			library.addBook("0451524934", "1984", "George Orwell", "2012-03-13");
			library.addBook("8806203797", "1Q84", "Haruki Murakami", "2012-03-13");
			library.addBook("0460005251", "War and Peace", "Leo Tolstoy", "2012-03-13");
			library.addBook("1906141010", "And Then There Were None",
					"Agatha Christie", "2012-03-13");
		
			// add some little reviews
			Book firstBook = library.getAllBooks().get(0);
			firstBook.setSummary("Summary available");
			firstBook.addReview("Very good !");
			firstBook.addReview("Totally love it <3");
			firstBook.addReview("meh");
			
			Naming.rebind("LibraryService", library);

			System.out.println("Server Ready");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
