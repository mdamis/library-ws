package fr.upem.client;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;

import javax.xml.rpc.ServiceException;

import FaultContracts.GOTLServices._2008._01.DefaultFaultContract;
import fr.upem.bank.Bank;
import fr.upem.bank.BankServiceLocator;
import fr.upem.book.Book;
import fr.upem.library.Library;
import fr.upem.library.LibraryServiceLocator;
import fr.upem.user.UserManager;
import fr.upem.user.UserManagerServiceLocator;
import net.restfulwebservices.www.DataContracts._2008._01.Currency;
import net.restfulwebservices.www.DataContracts._2008._01.CurrencyCode;
import net.restfulwebservices.www.ServiceContracts._2008._01.CurrencyServiceLocator;
import net.restfulwebservices.www.ServiceContracts._2008._01.ICurrencyService;

public class Client {
	private final UserManager manager;
	private final Library library;
	private final Bank bank;
	private String username;
	private int currentAccount;
	
	
	public Client() throws ServiceException {
		manager = new UserManagerServiceLocator().getUserManager();
		library = new LibraryServiceLocator().getLibrary();
		bank = new BankServiceLocator().getBank();
	}

	public static double getConvertRate(String fromCurrency, String toCurrency)
			throws ServiceException, DefaultFaultContract, RemoteException, IllegalArgumentException {
		ICurrencyService iCurrency = new CurrencyServiceLocator().getBasicHttpBinding_ICurrencyService();
		Currency c = iCurrency.getConversionRate(CurrencyCode.fromString(fromCurrency),
				CurrencyCode.fromString(toCurrency));
		Double r = c.getRate();
		return r;
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
	
	public boolean accountExist(int accountId) throws RemoteException{
		return bank.exist(accountId);
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
	
	public void setCurrentAccount(int accountId) {
		this.currentAccount = accountId;
	}
	
	public String getDetail() throws RemoteException {
		return this.bank.getDetailAccount(this.currentAccount);
	}
	
	public void disconnect() {
		try {
			if(manager.disconnect(username)) {
				System.out.println(username + " has been disconnected");
			} else {
				System.err.println("Failed to disconnect");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isAbleToBuyBook(Book book) throws RemoteException, IllegalArgumentException, ServiceException {
		Double rate = getConvertRate(book.getCurrency(), bank.getAccountCurrency(currentAccount));
		Double priceConverted = book.getPrice() * rate;
		return (bank.getAccountBalance(currentAccount) > priceConverted);
	}
	
	public void buyBook(Book book) throws RemoteException, IllegalArgumentException, ServiceException {
		Double rate = getConvertRate(book.getCurrency(), bank.getAccountCurrency(currentAccount));
		Double priceConverted = book.getPrice() * rate;
		bank.withdrawal(currentAccount, priceConverted);
	}
	
	public void deposit(double amount,String newCurency) throws RemoteException, IllegalArgumentException, ServiceException{
		Double rate = getConvertRate(newCurency, bank.getAccountCurrency(currentAccount));
		Double depositAmmountConverted = amount * rate;
		bank.deposit(currentAccount, depositAmmountConverted);
	}
	
	public String getAccountName() throws RemoteException{
		return bank.getAccountName(currentAccount);
	}
	
	public String getAccountFirstname() throws RemoteException{
		return bank.getAccountFirstName(currentAccount);
	}
	
	public String getAccountBalance() throws RemoteException{
		return bank.getAccountName(currentAccount).toString();
	}
	
	public String getAccountId() throws RemoteException{
		return Integer.toString(bank.getAccountId(currentAccount));
	}
	
	public String getAccountCurrency() throws RemoteException{
		return bank.getAccountCurrency(currentAccount);
	}
	
	public int createAccount(String name,String firstname,String currency) throws RemoteException {
		return bank.addAccount(name, firstname, currency);
	}
}
