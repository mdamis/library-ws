import java.rmi.Naming;

public class LibraryServer {

	public static void main(String[] args) {
		try {
			Library library = new LibraryImpl();

			// add some little books
			library.add("0451524934", "1984", "George Orwell");
			library.add("8806203797", "1Q84", "Haruki Murakami");
			library.add("0460005251", "War and Peace", "Leo Tolstoy");
			library.add("1906141010", "And Then There Were None",
					"Agatha Christie");
		
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
