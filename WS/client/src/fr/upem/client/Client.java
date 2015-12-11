package fr.upem.client;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;

import javafx.application.Application;

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
	private String username;
	
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
	
	public boolean userExist(String username, String passwd)throws IllegalArgumentException, RemoteException {
		return manager.exist(username);
	}
	
	public boolean signup(String username,String passwd) {
		try {
			manager.registerUser(username, passwd);
			this.username = username;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	

	public boolean signin(String username, String passwd) {
		try {
			if(manager.connect(username, passwd)) {
				this.username = username;
				return true;
			} else {
				return false;
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public List<Book> getAllBooks() throws RemoteException {
		List<Book> books = (Arrays.asList(library.getAllBooks()));
		return books;
	}
	
	public String getUsername() {
		return username;
	}
	


	public static void main(String[] args) {
		try {
			Application.launch(GUI.class, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
