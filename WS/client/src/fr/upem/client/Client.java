package fr.upem.client;

import javax.xml.rpc.ServiceException;

import fr.upem.library.Library;
import fr.upem.library.LibraryServiceLocator;
import fr.upem.user.UserManager;
import fr.upem.user.UserManagerServiceLocator;

public class Client {
	private final UserManager manager;
	private final Library library;
	
	public Client() throws ServiceException {
		manager = new UserManagerServiceLocator().getUserManager();
		library = new LibraryServiceLocator().getLibrary();
	}

	public Library getLibrary() {
		return library;
	}

	public UserManager getManager() {
		return manager;
	}
	
	
}
