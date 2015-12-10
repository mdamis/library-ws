package fr.upem.client;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.rpc.ServiceException;

import fr.upem.bank.Bank;
import fr.upem.bank.BankServiceLocator;
import fr.upem.book.Book;
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
	
	public boolean userExist(String username,String passwd)throws IllegalArgumentException, RemoteException {
		return manager.exist(username,passwd);
	}
	
	public boolean signin(String username,String passwd) {
		return true;
	}
	
	public List<Book> getAllBooks() throws RemoteException{
		List<Book> books = (Arrays.asList(library.getAllBooks()));
		return books;
	}
	
	public String getUsername() throws RemoteException{
		String userName = manager.getUsernameConnected();
		if(userName == null) {
			throw new IllegalStateException("No user connected");
		}
		return userName;
	}
	
}
