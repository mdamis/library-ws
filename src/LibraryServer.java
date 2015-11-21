import java.rmi.Naming;

public class LibraryServer {

	public static void main(String[] args) {
		try {
			Library library = new LibraryImpl();

			Naming.rebind("LibraryService", library);

			System.out.println("Server Ready");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
