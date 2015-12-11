import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.util.List;

@SuppressWarnings("deprecation")
public class LibraryClientScenario {
	public static void main(String[] args) {
		try {
			String codebase = "file:../server/";
			System.setProperty("java.rmi.server.codebase", codebase);
			System.setProperty("java.security.policy", "library.policy");
			System.setSecurityManager(new RMISecurityManager());
			
			Library library = (Library) Naming.lookup("rmi://localhost:1099/LibraryService");

			
			User bcrochez = library.connect("bcroche");
			if(bcrochez == null) {
				System.out.println("Authentication failed.");
			}
			bcrochez = library.connect("bcrochez");
			User mdamis = library.connect("mdamis");
			User mperouma = library.connect("mperouma");
			
			List<Book> books = library.searchBook("");
			
			if (books.isEmpty()) {
				System.out.println("No book corresponding to your query");
			}
			
			books = library.searchBook("84");
			System.out.println("Query : 84");
			for (Book book : books) {
				System.out.println(book.details());
				System.out.println(book.getSummary());
			}
			
			books = library.searchBook("st");
			System.out.println("Query : st");
			for (Book book : books) {
				System.out.println(book.details());
				System.out.println(book.getSummary());
			}	
			
			books = library.getAllBooks();

			for (Book book : books) {
				if(book.isSaleable()) {
					System.out.println(book.getTitle() + " is saleable.");
				} else {
					System.out.println(book.getTitle() + " is not saleable.");
				}
			}
			
			if(!library.addBook("0451524934", "1984", "George Orwell", "2012-03-13")) {
				System.out.println("There is already a book with an ISBN of 0451524934 in the library");
			}
			System.out.println("");

			System.out.println(library.borrowBook(books.get(0), mdamis));
			System.out.println(library.borrowBook(books.get(0), mperouma));

			for(Book book : books) {
				System.out.println(library.borrowBook(book, bcrochez));
				System.out.println(library.borrowBook(book, mperouma));
			}
			
			for(Book book : books) {
				System.out.println(library.returnBook(book, bcrochez));
				System.out.println(library.returnBook(book, mdamis));
				System.out.println(library.returnBook(book, mperouma));
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
