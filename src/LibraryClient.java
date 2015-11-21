import java.rmi.Naming;
import java.util.List;

public class LibraryClient {

	public static void main(String[] args) {
		try {
			Library library = (Library) Naming.lookup("LibraryService");

			library.add("0451524934", "1984", "George Orwell");
			library.add("8806203797", "1Q84", "Haruki Murakami");
			library.add("0460005251", "War and Peace", "Leo Tolstoy");
			library.add("1906141010", "And Then There Were None", "Agatha Christie");

			List<Book> books = library.getAllBooks();
			System.out.println(books.size());

			library.delete("0460005251");

			books = library.getAllBooks();

			library.borrowBook("1906141010", "mdamis");
			library.borrowBook("1906141010", "bcrochez");
			library.returnBook("1906141010");
			library.borrowBook("1906141010", "bcrochez");

			System.out.println(books.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
