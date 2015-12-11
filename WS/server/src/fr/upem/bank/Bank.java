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
	 * @throws IllegalStateException if account does not exist (recommended to used method exist before)
	 */
	public String getDetailAccount(int accountId){
		Account account = mapAccount.get(accountId);
		if(account!= null){
			return  account.getName()+" "+account.getFirstname()+" " + account.getBalance() + " "+account.getCurrency();
		}else{
			throw new IllegalStateException("account does not exist");
		}
	}
	
	/**
	 * get the currency of an account
	 * @param accountId the accountID to seek
	 * @return the currency of the Account
	 * @throws IllegalStateException if account does not exist (recommended to used method exist before)
	 */
	public String getAccountCurrency(int accountId){
		Account account= mapAccount.get(accountId);
		if(account != null){
			return account.getCurrency();
		}else{
			throw new IllegalStateException("account does not exist");
		}
	}
	
	/**
	 * get the currency of an account
	 * @param accountId the accountID to seek
	 * @return the currency of the Account
	 * @throws IllegalStateException if account does not exist (recommended to used method exist before)
	 */
	public double getAccountBalance(int accountId){
		Account account= mapAccount.get(accountId);
		if(account != null){
			return account.getBalance();
		}else{
			throw new IllegalStateException("account does not exist");
		}
	}
	
	/**
	 * get the account id of an account
	 * @param accountId the accountID to seek
	 * @return the id of the Account
	 * @throws IllegalStateException if account does not exist (recommended to used method exist before)
	 */
	public int getAccountId(int accountId){
		Account account= mapAccount.get(accountId);
		if(account != null){
			return account.getNumber();
		}else{
			throw new IllegalStateException("account does not exist");
		}
	}
	
	/**
	 * get the name's owner of an account
	 * @param accountId the accountID to seek
	 * @return the name's owner of the Account
	 * @throws IllegalStateException if account does not exist (recommended to used method exist before)
	 */
	public String getAccountName(int accountId){
		Account account= mapAccount.get(accountId);
		if(account != null){
			return account.getName();
		}else{
			throw new IllegalStateException("account does not exist");
		}
	}
	
	/**
	 * get the owner's firstname of an account
	 * @param accountId the accountID to seek
	 * @return owner's firstname of the Account
	 * @throws IllegalStateException if account does not exist (recommended to used method exist before)
	 */
	public String getAccountFirstName(int accountId){
		Account account= mapAccount.get(accountId);
		if(account != null){
			return account.getFirstname();
		}else{
			throw new IllegalStateException("account does not exist");
		}
	}
	
	/**
	 * Deposit the amount of money to the an account
	 * @param accountId the account Id of the account wanted
	 * @param amount the amount deposit
	 * @return true if the function sucessfully deposit else false
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
