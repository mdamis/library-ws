package fr.upem.bank;

public class Account{

	private double balance;
	private int number;
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

	public Account(int number, String name, String firstname, String currency) {
		this.balance = 0;
		this.currency = currency;
		this.name = name;
		this.firstname = firstname;
		this.number = number;
	}
	
	public static Account createAccount(int number, String name, String firstname, String currency){
		return new Account(number, name, firstname, currency);
	}

	public String getCurrency() {
		return currency;
	}

	public boolean isValid(long number, String name, String firstname) {
		return (this.number == number && this.name.equals(name) && this.firstname.equals(firstname));
	}

	public boolean deposit(double amount){
		/*
		ICurrencyService iCurrency = new CurrencyServiceLocator().getBasicHttpBinding_ICurrencyService();
		Currency c = iCurrency.getConversionRate(CurrencyCode.fromString(newCurency),
				CurrencyCode.fromString(this.currency));
		Double r = c.getRate();
		*/
		if (amount >= 0) {
			balance += (amount);
			return true;
		} else {
			return false;
		}

	}

	public boolean withdrawal(double amount){
		/*ICurrencyService iCurrency = new CurrencyServiceLocator().getBasicHttpBinding_ICurrencyService();
		Currency c = iCurrency.getConversionRate(CurrencyCode.fromString(newCurency),
				CurrencyCode.fromString(this.currency));
		Double rate = c.getRate();
		*/
		if (authorizedPayment(amount)) {
			balance -= (amount);
			return true;
		} else {
			return false;
		}

	}

	public boolean authorizedPayment(double amount) {
		return (balance - amount >= 0);
	}

	public double valueOfBalance() {
		return balance;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/*
	 * public static void main(String[] args) throws DefaultFaultContract,
	 * RemoteException, IllegalArgumentException, ServiceException { Account a =
	 * new Account(); System.out.println(a.valueOfBalance());
	 * System.out.println(a.getCurrency()); try { a.deposit(567, "JPY"); } catch
	 * (DefaultFaultContract e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (RemoteException e) { // TODO Auto-generated
	 * catch block e.printStackTrace(); } catch (IllegalArgumentException e) {
	 * // TODO Auto-generated catch block e.printStackTrace(); } catch
	 * (ServiceException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * System.out.println(a.valueOfBalance());
	 * System.out.println(a.getCurrency());
	 * 
	 * a.withdrawal(2); System.out.println(a.valueOfBalance());
	 * a.withdrawal(700,"JPY"); System.out.println(a.valueOfBalance()); }
	 */
}
