package fr.upem.converter;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import NET.webserviceX.www.Currency;
import NET.webserviceX.www.CurrencyConvertorLocator;
import NET.webserviceX.www.CurrencyConvertorSoap;

public class CurrencyConverterService {

	private double convert(double value, Currency fromCurrency, Currency toCurrency) throws ServiceException, RemoteException {
		CurrencyConvertorSoap ccs = new CurrencyConvertorLocator().getCurrencyConvertorSoap();
		return value * ccs.conversionRate(fromCurrency, toCurrency);
	}
	
	public double convertEURtoUSD(double value) throws RemoteException, ServiceException {
		return convert(value, Currency.EUR, Currency.USD);
	}
	
	public double convertEURtoGBP(double value) throws RemoteException, ServiceException {
		return convert(value, Currency.EUR, Currency.GBP);
	}
	
	public double convertEURtoJPY(double value) throws RemoteException, ServiceException {
		return convert(value, Currency.EUR, Currency.JPY);
	}
	
	public double convertUSDtoEUR(double value) throws RemoteException, ServiceException {
		return convert(value, Currency.USD, Currency.EUR);
	}
	
	public double convertUSDtoGBP(double value) throws RemoteException, ServiceException {
		return convert(value, Currency.USD, Currency.GBP);
	}
	
	public double convertUSDtoJPY(double value) throws RemoteException, ServiceException {
		return convert(value, Currency.USD, Currency.JPY);
	}
	
	public double convertGBPtoEUR(double value) throws RemoteException, ServiceException {
		return convert(value, Currency.GBP, Currency.EUR);
	}
	
	public double convertGBPtoUSD(double value) throws RemoteException, ServiceException {
		return convert(value, Currency.GBP, Currency.USD);
	}
	
	public double convertGBPtoJPY(double value) throws RemoteException, ServiceException {
		return convert(value, Currency.GBP, Currency.JPY);
	}
	
	public double convertJPYtoEUR(double value) throws RemoteException, ServiceException {
		return convert(value, Currency.JPY, Currency.EUR);
	}
	
	public double convertJPYtoUSD(double value) throws RemoteException, ServiceException {
		return convert(value, Currency.JPY, Currency.USD);
	}
	
	public double convertJPYtoGBP(double value) throws RemoteException, ServiceException {
		return convert(value, Currency.JPY, Currency.GBP);
	}
}
