package fr.upem.client;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.xml.rpc.ServiceException;

import net.restfulwebservices.www.DataContracts._2008._01.Currency;
import net.restfulwebservices.www.DataContracts._2008._01.CurrencyCode;
import net.restfulwebservices.www.ServiceContracts._2008._01.CurrencyServiceLocator;
import net.restfulwebservices.www.ServiceContracts._2008._01.ICurrencyService;
import FaultContracts.GOTLServices._2008._01.DefaultFaultContract;
import fr.upem.bank.Bank;
import fr.upem.bank.BankServiceLocator;
import fr.upem.library.Book;
import fr.upem.library.Library;
import fr.upem.library.LibraryServiceLocator;
import fr.upem.user.UserManager;
import fr.upem.user.UserManagerServiceLocator;

public class Client {
	private final UserManager manager;
	private final Library library;
	private final Bank bank;
	private String username;
	private int currentAccount;
	private final HashMap<String, Integer> cart = new HashMap<>();

	/**
	 * retrieve the manager, library and bank with the web service
	 * 
	 * @throws ServiceException
	 */
	public Client() throws ServiceException {
		manager = new UserManagerServiceLocator().getUserManager();
		library = new LibraryServiceLocator().getLibrary();
		bank = new BankServiceLocator().getBank();
	}

	/**
	 * Get the convert rate between two currency with a distant web service
	 * 
	 * @param fromCurrency the original currency
	 * @param toCurrency the resulting currency
	 * @return a double representation of the convert rate from the original currency to the new currency
	 * @throws ServiceException
	 * @throws DefaultFaultContract
	 * @throws RemoteException
	 * @throws IllegalArgumentException
	 */
	public static double getConvertRate(String fromCurrency, String toCurrency)
			throws ServiceException, DefaultFaultContract, RemoteException,
			IllegalArgumentException {
		ICurrencyService iCurrency = new CurrencyServiceLocator()
				.getBasicHttpBinding_ICurrencyService();
		Currency c = iCurrency.getConversionRate(
				CurrencyCode.fromString(fromCurrency),
				CurrencyCode.fromString(toCurrency));
		Double r = c.getRate();
		return r;
	}

	/**
	 * ask the usermanager webservice if a certain user exist
	 * 
	 * @param username the username of the user searched
	 * @param passwd the password of the user searched
	 * @return true if user exist else false
	 * @throws IllegalArgumentException
	 * @throws RemoteException
	 */
	public boolean userExist(String username, String passwd)
			throws IllegalArgumentException, RemoteException {
		return manager.exist(username);
	}

	/**
	 * ask the bank webservice if a certain account exist
	 * 
	 * @param accountId the account's id searched
	 * @return true if account exist else false
	 * @throws RemoteException
	 */
	public boolean accountExist(int accountId) throws RemoteException {
		return bank.exist(accountId);
	}

	/**
	 * add a new user the user manager and connect the user
	 * 
	 * @param username the new user's username
	 * @param passwd the new user's password
	 * @return true if signup sucessfully worked else false
	 */
	public boolean signup(String username, String passwd) {
		try {
			manager.registerUser(username, passwd);
			this.username = username;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * connect the user to the client
	 * 
	 * @return true if user sucessfully connected else false
	 */
	public boolean signin(String username, String passwd) {
		try {
			if (manager.connect(username, passwd)) {
				this.username = username;
				return true;
			} else {
				return false;
			}
		} catch (RemoteException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * ask the web service all the books
	 * 
	 * @return a list of Book instance
	 * @throws RemoteException
	 */
	public List<Book> getAllBooks() throws RemoteException {
		return (Arrays.asList(library.getAllBooks()));
	}
	
	/**
	 * Returns a book given his isbn
	 * 
	 * @param isbn book's isbn
	 * @return
	 * @throws RemoteException
	 */
	public Book getBook(String isbn) throws RemoteException {
		return library.getBook(isbn);
	}

	/**
	 * Returns the cart
	 * 
	 * @return
	 */
	public HashMap<String, Integer> getCart() {
		return cart;
	}

	/**
	 * Adds a book to the cart with the number of exemplary added
	 * 
	 * @param isbn book's isbn
	 * @param exemplary 
	 */
	public void addToCart(String isbn, int exemplary) {
		cart.compute(isbn, (k, v) -> v == null ? exemplary : v+exemplary);
	}
	
	/**
	 * Removes a book from the cart with the number of exemplary to remove
	 * 
	 * @param isbn book's isbn
	 * @param exemplary the number of exemplary to remove
	 */
	public void removeFromCart(String isbn, int exemplary) {
		if(cart.containsKey(isbn)) {
			int exempInCart = cart.get(isbn);
			if(exemplary > exempInCart) {
				throw new IllegalArgumentException("wrong number of exemplary to remove from cart");
			} else if (exemplary == exempInCart) {
				cart.remove(isbn);
			} else {
				cart.put(isbn, exempInCart - exemplary);
			}
		} else {
			throw new IllegalArgumentException("wrong isbn");
		}
	}

	/**
	 * Gets the total amount of the cart
	 * 
	 * @return
	 */
	public int getCartAmount() {
		int amount = 0;
		for (String isbn : cart.keySet()) {
			try {
				amount += library.getBook(isbn).getPrice() * cart.get(isbn);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		return amount;
	}

	/**
	 * Gets the number of exemplary of a book  wich is in the cart
	 * 
	 * @param isbn
	 * @return
	 */
	public int getExemplaryInCart(String isbn) {
		if(cart.containsKey(isbn)) {
			return cart.get(isbn);
		} else {
			return 0;
		}
	}
	
	/**
	 * Returns the total number of books present in the cart
	 * 
	 * @return
	 */
	public int getNumberOfBooksInCart() {
		int exempl = 0;
		for(int i : cart.values()) {
			exempl += i;
		}
		return exempl;
	}

	/**
	 * get the current user connected
	 * 
	 * @return
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * set the current bank's account connected
	 * 
	 * @param accountId
	 */
	public void setCurrentAccount(int accountId) {
		this.currentAccount = accountId;
	}

	/**
	 * get the current bank's account connected
	 * 
	 * @return
	 */
	public int getCurrentAccount() {
		return currentAccount;
	}

	/**
	 * ask the webservice a detail of the bank's account
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public String getDetail() throws RemoteException {
		return this.bank.getDetailAccount(this.currentAccount);
	}

	/**
	 * disconnect the current user connected from the client
	 */
	public void disconnect() {
		try {
			if (manager.disconnect(username)) {
				System.out.println(username + " has been disconnected");
			} else {
				System.err.println("Failed to disconnect");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Ask the web service if the user have enough money to buy the book, the
	 * money is converted to the correct currency if needed with the converter
	 * web service
	 * 
	 * @param book the book the user want to buy
	 * @return true if the user can buy this book else false
	 * @throws RemoteException
	 * @throws IllegalArgumentException
	 * @throws ServiceException
	 */
	public boolean isAbleToBuyBook(Book book) throws RemoteException,
			IllegalArgumentException, ServiceException {
		Double rate = getConvertRate(book.getCurrency(),
				bank.getAccountCurrency(currentAccount));
		Double priceConverted = book.getPrice() * rate;
		return (bank.getAccountBalance(currentAccount) >= priceConverted);
	}

	public boolean isAbleToBuyCommand(int totalAmount, String bookCurrency)
			throws DefaultFaultContract, RemoteException,
			IllegalArgumentException, ServiceException {
		Double rate = getConvertRate(bookCurrency,
				bank.getAccountCurrency(currentAccount));
		Double priceConverted = totalAmount * rate;
		return (bank.getAccountBalance(currentAccount) >= priceConverted);
	}

	/**
	 * Action of buying the book, withdraw money and remove some exemplary of
	 * the book
	 * 
	 * @param book the book bought
	 * @param exemplary 
	 * @throws RemoteException
	 * @throws IllegalArgumentException
	 * @throws ServiceException
	 */
	public void buyBook(Book book, int exemplary) throws RemoteException,
			IllegalArgumentException, ServiceException {
		Double rate = getConvertRate(book.getCurrency(),
				bank.getAccountCurrency(currentAccount));
		Double priceConverted = book.getPrice() * exemplary * rate;
		bank.withdrawal(currentAccount, priceConverted);
		library.sellBook(book.getISBN(), exemplary);
	}

	/**
	 * Buy all the books in the cart
	 */
	public void buyCart() {
		for (String isbn : cart.keySet()) {
			try {
				buyBook(library.getBook(isbn), cart.get(isbn));
			} catch (RemoteException | IllegalArgumentException
					| ServiceException e) {
				e.printStackTrace();
			}
		}
		cart.clear();
	}

	/**
	 * round a double value
	 * 
	 * @param value
	 * @param places
	 * @return
	 */
	private static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	/**
	 * deposit money the current connected bank account
	 * 
	 * @param amount the amount inserted
	 * @param newCurency the currency of the amount of money
	 * @throws RemoteException
	 * @throws IllegalArgumentException
	 * @throws ServiceException
	 */
	public void deposit(double amount, String newCurency)
			throws RemoteException, IllegalArgumentException, ServiceException {
		Double rate = getConvertRate(newCurency,
				bank.getAccountCurrency(currentAccount));
		Double depositAmmountConverted = amount * rate;

		bank.deposit(currentAccount, round(depositAmmountConverted, 2));
	}

	/**
	 * ask bank web service the current connected account's name
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public String getAccountName() throws RemoteException {
		return bank.getAccountName(currentAccount);
	}

	/**
	 * ask bank web service the current connected account's firstname
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public String getAccountFirstname() throws RemoteException {
		return bank.getAccountFirstName(currentAccount);
	}

	/**
	 * ask bank web service the current connected account's balance
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public String getAccountBalance() throws RemoteException {
		return Double.toString(bank.getAccountBalance(currentAccount));
	}

	/**
	 * ask bank web service the current connected account's id
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public String getAccountId() throws RemoteException {
		return Integer.toString(bank.getAccountId(currentAccount));
	}

	/**
	 * ask bank web service the current connected account's currency
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public String getAccountCurrency() throws RemoteException {
		return bank.getAccountCurrency(currentAccount);
	}

	/**
	 * ask bank web service to create a new account and adding it
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public int createAccount(String name, String firstname, String currency)
			throws RemoteException {
		return bank.addAccount(name, firstname, currency);
	}

}
