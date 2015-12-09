import java.rmi.Naming;
import java.util.List;

public class LibraryClientScenario {
	public static void main(String[] args) {
		try {
			Library library = (Library) Naming.lookup("LibraryService");
			
			User bcrochez = new UserImpl("bcrochez");
			User mdamis = new UserImpl("mdamis");
			User mperouma = new UserImpl("mperouma");
			
			List<Book> books = library.getAllBooks();

			for (Book book : books) {
				System.out.println(book.details());
				System.out.println(book.getSummary());
				if(book.isSaleable()) {
					System.out.println(book.getTitle() + " is saleable.");
				} else {
					System.out.println(book.getTitle() + " is not saleable.");
				}
			}
			System.out.println("");

			System.out.println(library.borrowBook(books.get(0), mdamis));
			System.out.println(library.borrowBook(books.get(0), mperouma));

			for(Book book : books) {
				System.out.println(library.borrowBook(book, bcrochez));
				System.out.println(library.borrowBook(book, mperouma));
			}
			
			books = library.getAllBooks();
			for (Book book : books) {
				if(book.isSaleable()) {
					System.out.println(book.getTitle() + " is saleable.");
				} else {
					System.out.println(book.getTitle() + " is not saleable.");
				}
			}

			for(Book book : bcrochez.getBorrowedBooks()) {
				System.out.println(book.details());
			}

			for (Book book : books) {
				System.out.println(book.getReviews());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
