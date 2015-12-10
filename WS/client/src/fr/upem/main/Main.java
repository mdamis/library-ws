package fr.upem.main;

import java.rmi.RemoteException;

import javafx.application.Application;

import javax.xml.rpc.ServiceException;

import net.restfulwebservices.www.DataContracts._2008._01.Currency;
import net.restfulwebservices.www.DataContracts._2008._01.CurrencyCode;
import net.restfulwebservices.www.ServiceContracts._2008._01.CurrencyServiceLocator;
import net.restfulwebservices.www.ServiceContracts._2008._01.ICurrencyService;
import FaultContracts.GOTLServices._2008._01.DefaultFaultContract;
import fr.upem.client.GUI;

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
		try {
			Application.launch(GUI.class, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
