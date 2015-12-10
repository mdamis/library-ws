package fr.upem.main;

import java.rmi.RemoteException;
import java.util.HashMap;

import javax.xml.rpc.ServiceException;

import FaultContracts.GOTLServices._2008._01.DefaultFaultContract;
import fr.upem.bank.Account;
import fr.upem.bank.Bank;
import fr.upem.bank.BankServiceLocator;
import fr.upem.user.UserManager;
import fr.upem.user.UserManagerServiceLocator;
import net.restfulwebservices.www.DataContracts._2008._01.Currency;
import net.restfulwebservices.www.DataContracts._2008._01.CurrencyCode;
import net.restfulwebservices.www.ServiceContracts._2008._01.CurrencyServiceLocator;
import net.restfulwebservices.www.ServiceContracts._2008._01.ICurrencyService;

public class Main {

	public static double getConvertRate(String fromCurrency, String toCurrency)
			throws ServiceException, DefaultFaultContract, RemoteException, IllegalArgumentException {
		ICurrencyService iCurrency = new CurrencyServiceLocator().getBasicHttpBinding_ICurrencyService();
		Currency c = iCurrency.getConversionRate(CurrencyCode.fromString(fromCurrency),
				CurrencyCode.fromString(toCurrency));
		Double r = c.getRate();
		return r;
	}

	public static void main(String[] args) {
		UserManager manager;
		try {
			manager = new UserManagerServiceLocator().getUserManager();
			try {
				manager.registerUser("bcrochez", "abc");
			} catch (RemoteException e) {
				System.err.println("error register");
			}
			try {
				if (manager.connect("bcrochez", "abc")) {
					System.out.println("bcrochez is now connected");
				}
			} catch (RemoteException e) {
				System.err.println("error connect");
			}
			try {
				if (manager.disconnect("bcrochez")) {
					System.out.println("bcrochez has been disconnected");
				}
			} catch (RemoteException e) {
				System.err.println("error disconnect");
			}

		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		Bank bank;
		try {
			bank = new BankServiceLocator().getBank();
			System.out.println(bank.getSize());
			int myBankId = bank.addAccount("peroumalle", "mourougan", "EUR");
			System.out.println(bank.getSize());
			System.out.println(myBankId);
			String myCurrency = bank.getAccountCurrency(myBankId);
			System.out.println(myCurrency);
			//deposit 5 euro
			bank.deposit(myBankId, 5*getConvertRate("EUR",myCurrency));
			System.out.println(bank.getDetailAccount(myBankId));			
			//deposit 10000 JPY
			bank.deposit(myBankId,10000*getConvertRate("JPY",myCurrency));
			System.out.println(bank.getDetailAccount(myBankId));
			
			//withdrawal 10 GBP
			bank.deposit(myBankId,10*getConvertRate("GBP",myCurrency));
			System.out.println(bank.getDetailAccount(myBankId));
			
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		

	}

}
