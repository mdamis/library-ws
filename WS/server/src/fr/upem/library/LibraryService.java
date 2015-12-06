package fr.upem.library;

import java.rmi.Naming;

public class LibraryService {
	private final Library library;
	
	public LibraryService() {
		library = (Library) Naming.lookup("LibraryService");
	}
}
