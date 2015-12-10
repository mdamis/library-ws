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
	
	public boolean removeAccount(int accountNumber){
		if(mapAccount.containsKey(accountNumber)){
			mapAccount.remove(accountNumber);
			return true;
		}
		return false;
	}
	
	public Account getAccount(int accountNumber) {
		return mapAccount.get(accountNumber);
	}
	
	public String getDetailAccount(int accountId){
		Account account = mapAccount.get(accountId);
		if(account!= null){
			return  account.getName()+" "+account.getFirstname()+"\nbalance :" + account.getBalance() + "\nCurrency :"+account.getCurrency()+"\n";
		}else{
			return null;
		}
	}
	public String getAccountCurrency(int accountId){
		Account account= mapAccount.get(accountId);
		if(account != null){
			return account.getCurrency();
		}else{
			return null;
		}
	}
	public boolean deposit(int accountId,double amount) {
		Account account= mapAccount.get(accountId);
		if(account!= null) {
			return account.deposit(amount);
		}else{
			return false;
		}
		
	}

	public boolean withdrawal(int accountId,double amount) {
		Account account= mapAccount.get(accountId);
		if(account!= null) {
			return account.withdrawal(amount);
		} else {
			return false;
		}
	}
	/*
	public static void main(String[] args) {
		Bank b = new Bank();
		int id = b.addAccount("jo", "ko", "EUR");
	}
	*/
}
