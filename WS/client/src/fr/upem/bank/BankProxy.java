package fr.upem.bank;

public class BankProxy implements fr.upem.bank.Bank {
  private String _endpoint = null;
  private fr.upem.bank.Bank bank = null;
  
  public BankProxy() {
    _initBankProxy();
  }
  
  public BankProxy(String endpoint) {
    _endpoint = endpoint;
    _initBankProxy();
  }
  
  private void _initBankProxy() {
    try {
      bank = (new fr.upem.bank.BankServiceLocator()).getBank();
      if (bank != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)bank)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)bank)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (bank != null)
      ((javax.xml.rpc.Stub)bank)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public fr.upem.bank.Bank getBank() {
    if (bank == null)
      _initBankProxy();
    return bank;
  }
  
  public int addAccount(java.lang.String name, java.lang.String firstname, java.lang.String currency) throws java.rmi.RemoteException{
    if (bank == null)
      _initBankProxy();
    return bank.addAccount(name, firstname, currency);
  }
  
  public boolean removeAccount(int accountNumber) throws java.rmi.RemoteException{
    if (bank == null)
      _initBankProxy();
    return bank.removeAccount(accountNumber);
  }
  
  public boolean deposit(int accountId, double amount) throws java.rmi.RemoteException{
    if (bank == null)
      _initBankProxy();
    return bank.deposit(accountId, amount);
  }
  
  public boolean withdrawal(int accountId, double amount) throws java.rmi.RemoteException{
    if (bank == null)
      _initBankProxy();
    return bank.withdrawal(accountId, amount);
  }
  
  public java.lang.String getAccountCurrency(int accountId) throws java.rmi.RemoteException{
    if (bank == null)
      _initBankProxy();
    return bank.getAccountCurrency(accountId);
  }
  
  public java.lang.String getDetailAccount(int accountId) throws java.rmi.RemoteException{
    if (bank == null)
      _initBankProxy();
    return bank.getDetailAccount(accountId);
  }
  
  public int getSize() throws java.rmi.RemoteException{
    if (bank == null)
      _initBankProxy();
    return bank.getSize();
  }
  
  
}