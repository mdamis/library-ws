package fr.upem.bank;

public class Account{

	private double balance;
	private int number;
	private final String name;
	private final String firstname;
	private final String currency;
	
	/**
	 * Default constructor
	 */
	public Account() {
		this.balance = 0;
		this.number = 0;
		this.name = "";
		this.firstname = "";
		this.currency = "EUR";
	}

	/**
	 * Constructor more developped
	 * @param number the account id
	 * @param name the name of the owner
	 * @param firstname the firstname of the owner
	 * @param currency the currency chosen for the account
	 */
	public Account(int number, String name, String firstname, String currency) {
		this.balance = 0;
		this.currency = currency;
		this.name = name;
		this.firstname = firstname;
		this.number = number;
	}
	
	/**
	 *  Factory method to create an Account
	 * @param number the account id
	 * @param name the name of the owner
	 * @param firstname the firstname of the owner
	 * @param currency the currency chosen for the account
	 * @return an Account instance
	 */
	public static Account createAccount(int number, String name, String firstname, String currency){
		return new Account(number, name, firstname, currency);
	}

	/**
	 *  Currency getter
	 * @return String:currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 *  test if the user exist
	 * @param number the account id
	 * @param name the name of the owner
	 * @param firstname the firstname of the owner
	 * @return true of false
	 */
	public boolean isValid(long number, String name, String firstname) {
		return (this.number == number && this.name.equals(name) && this.firstname.equals(firstname));
	}

	/**
	 * deposit an amount of money to the account
	 * @param amount Double to deposit into account, must be positif
	 * @return true if function sucessfully deposit else false
	 */
	public boolean deposit(double amount){
		if (amount >= 0) {
			balance += (amount);
			return true;
		} else {
			return false;
		}

	}

	/**
	 * withdraw an amount of money from the account
	 * @param amount Double to withdraw account, must be positif
	 * @return true if function sucessfully withdraw else false
	 */
	public boolean withdrawal(double amount){
		if (amount < 0) {
			return false;
		} 
		if (authorizedPayment(amount)) {
			balance -= (amount);
			return true;
		} else {
			return false;
		}

	}

	/**
	 * function used to check if the amount doesn't break the balance
	 */
	private boolean authorizedPayment(double amount) {
		return (balance - amount >= 0);
	}

	/**
	 * 
	 * @return the balance of account
	 */
	public double getBalance() {
		return balance;
	}

	/**
	 * 
	 * @param balance
	 */
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	/**
	 * 
	 * @return account ID
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * 
	 * @param number the new account ID
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * 
	 * @return the name of the owner
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return the firstname of the owner
	 */
	public String getFirstname() {
		return firstname;
	}

}
