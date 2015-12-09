package fr.upem.bank;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import FaultContracts.GOTLServices._2008._01.DefaultFaultContract;
import net.restfulwebservices.www.DataContracts._2008._01.Currency;
import net.restfulwebservices.www.DataContracts._2008._01.CurrencyCode;
import net.restfulwebservices.www.ServiceContracts._2008._01.CurrencyServiceLocator;
import net.restfulwebservices.www.ServiceContracts._2008._01.ICurrencyService;

public class Account {
	private double balance;
	private long number;
	private String name;
	private String firstname;
	private String currency;

	public Account() {
		this.balance = 0;
		this.number = 0;
		this.name = "";
		this.firstname = "";
		this.currency = "EUR";
	}

	public Account(long number, String name, String firstname, String currency, double balance) {
		this.balance = balance;
		this.currency = currency;
		this.name = name;
		this.firstname = firstname;
		this.number = number;
	}

	public String getCurrency() {
		return currency;
	}

	public boolean isValid(long number, String name, String firstname) {
		return (this.number == number && this.name.equals(name) && this.firstname.equals(firstname));

	}

	public void deposit(double amount,String newCurency) throws  DefaultFaultContract, RemoteException, IllegalArgumentException, ServiceException {
		ICurrencyService iCurrency = new CurrencyServiceLocator().getBasicHttpBinding_ICurrencyService();
		Currency c = iCurrency.getConversionRate( CurrencyCode.fromString(newCurency), CurrencyCode.fromString(this.currency));
		Double r= c.getRate();
		if(amount>=0){
			balance += (amount*r);
		}
		else
			throw new IllegalArgumentException("deposit : amount should be positif");
	
	}

	public boolean withdrawal(double amount) {
		if (authorizedPayment(amount)) {
			balance -= amount;
			return true;
		} else
			return false;
	}
	
	public boolean withdrawal(double amount,String newCurency) throws ServiceException, DefaultFaultContract, RemoteException, IllegalArgumentException {
		ICurrencyService iCurrency = new CurrencyServiceLocator().getBasicHttpBinding_ICurrencyService();
		Currency c = iCurrency.getConversionRate( CurrencyCode.fromString(newCurency), CurrencyCode.fromString(this.currency));
		Double rate= c.getRate();
		if (authorizedPayment(amount*rate)) {
			balance -= (amount*rate);
			return true;
		} else
			return false;
	}

	public boolean authorizedPayment(double amount) {
		return (balance - amount >= 0);
	}

	public double valueOfBalance() {
		return balance;
	}
	
	/*
	public static void main(String[] args) throws DefaultFaultContract, RemoteException, IllegalArgumentException, ServiceException {
		Account a = new Account();
		System.out.println(a.valueOfBalance());
		System.out.println(a.getCurrency());
		try {
			a.deposit(567, "JPY");
		} catch (DefaultFaultContract e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		System.out.println(a.valueOfBalance());
		System.out.println(a.getCurrency());
		
		a.withdrawal(2);
		System.out.println(a.valueOfBalance());
		a.withdrawal(700,"JPY");
		System.out.println(a.valueOfBalance());
	}
	*/
}
