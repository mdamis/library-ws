package fr.upem.client;

import javax.xml.rpc.ServiceException;

import fr.upem.bank.Bank;
import fr.upem.bank.BankServiceLocator;
import fr.upem.library.Library;
import fr.upem.library.LibraryServiceLocator;
import fr.upem.user.UserManager;
import fr.upem.user.UserManagerServiceLocator;

public class Client {
	private final UserManager manager;
	private final Library library;
	private final Bank bank;
	
	public Client() throws ServiceException {
		manager = new UserManagerServiceLocator().getUserManager();
		library = new LibraryServiceLocator().getLibrary();
		bank = new BankServiceLocator().getBank();
	}

	public Library getLibrary() {
		return library;
	}

	public UserManager getManager() {
		return manager;
	}
	
	public Bank getBank() {
		return bank;
	}
}
