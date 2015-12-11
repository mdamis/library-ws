package fr.upem.bank;

import java.util.HashMap;
import java.util.Random;

public class Bank {
	private final HashMap<Integer,Account> mapAccount = new HashMap<>();

	/**
	 * Default constructor
	 */
	public Bank(){
		
	}
	
	/**
	 *  check if the accountID exist in the bank
	 * @param accountID the accountId tested
	 * @return true if the accountID is known else false
	 */
	public boolean exist(int accountID){
		return mapAccount.containsKey(accountID);
	}
	
	/**
	 * Add an account to the Bank
	 * @param name the name of the owner of the new account
	 * @param firstname the firstname of the owner of the new account
	 * @param currency the currency of the new account
	 * @return the accountId automaticaly generated
	 */
	public int addAccount(String name,String firstname,String currency) {
		int randomAccountId;
	    do {
	    	Random randomGenerator = new Random();
		    randomAccountId = randomGenerator.nextInt(999999);
	    }while(mapAccount.containsKey(randomAccountId));
	    Account account = Account.createAccount(randomAccountId, name, firstname, currency);
		mapAccount.put(randomAccountId, account);
		//return randomAccountId;
		return account.getNumber();
	}
	
	/**
	 * Remove an account from the Bank
	 * @param accountNumber the account Id that will be removed
	 * @return true if function sucessfuly removed account else false
	 */
	public boolean removeAccount(int accountNumber){
		if(mapAccount.containsKey(accountNumber)){
			mapAccount.remove(accountNumber);
			return true;
		}
		return false;
	}

	/**
	 * get string representation of the basic information of the account
	 * @param accountId the account ID we get the detail from
	 * @return a string representation of the basic information of the account
	 */
	public String getDetailAccount(int accountId){
		Account account = mapAccount.get(accountId);
		if(account!= null){
			return  account.getName()+" "+account.getFirstname()+" " + account.getBalance() + " "+account.getCurrency();
		}else{
			return null;
		}
	}
	
	/**
	 * get the currency of an account
	 * @param accountId the accountID to seek
	 * @return the currency of the Account, carefull it a blank String if no Currency was found
	 */
	public String getAccountCurrency(int accountId){
		Account account= mapAccount.get(accountId);
		if(account != null){
			return account.getCurrency();
		}else{
			return "";
		}
	}
	
	/**
	 * get the currency of an account
	 * @param accountId the accountID to seek
	 * @return the currency of the Account, carefull it return a blank String if no Currency was found
	 */
	public double getAccountBalance(int accountId){
		Account account= mapAccount.get(accountId);
		if(account != null){
			return account.getBalance();
		}else{
			return 0;
		}
	}
	
	/**
	 * get the account id of an account
	 * @param accountId the accountID to seek
	 * @return the id of the Account, carefull it return 0 if no account was found was found
	 */
	public int getAccountId(int accountId){
		Account account= mapAccount.get(accountId);
		if(account != null){
			return account.getNumber();
		}else{
			return 0;
		}
	}
	
	/**
	 * get the name's owner of an account
	 * @param accountId the accountID to seek
	 * @return the name's owner of the Account, carefull it return null if no Account was found
	 */
	public String getAccountName(int accountId){
		Account account= mapAccount.get(accountId);
		if(account != null){
			return account.getName();
		}else{
			return null;
		}
	}
	
	/**
	 * get the owner's firstname of an account
	 * @param accountId the accountID to seek
	 * @return owner's firstname of the Account, carefull it return null if no Account was found
	 */
	public String getAccountFirstName(int accountId){
		Account account= mapAccount.get(accountId);
		if(account != null){
			return account.getFirstname();
		}else{
			return null;
		}
	}
	
	/**
	 * Deposit the amount of money to the an account
	 * @param accountId the account Id of the account wanted
	 * @param amount the amount deposite
	 * @return true if the function sucessfuly deposit else false
	 */
	public boolean deposit(int accountId,double amount){
		Account account= mapAccount.get(accountId);
		if(account!= null) {
			return account.deposit(amount);
		}else{
			return false;
		}
	}

	/**
	 * Withdraw the amount of money from the an account
	 * @param accountId the account Id of the account wanted
	 * @param amount the amount to withdraw
	 * @return true if the function sucessfully withdraw else false
	 */
	public boolean withdrawal(int accountId,double amount){
		Account account= mapAccount.get(accountId);
		if(account!= null) {
			return account.withdrawal(amount);
		} else {
			return false;
		}
	}

}
