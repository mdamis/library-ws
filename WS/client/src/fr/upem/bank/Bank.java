/**
 * Bank.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package fr.upem.bank;

public interface Bank extends java.rmi.Remote {
    public fr.upem.bank.Account getAccount(int accountNumber) throws java.rmi.RemoteException;
    public boolean removeAccount(int accountNumber) throws java.rmi.RemoteException;
    public int addAccount(java.lang.String name, java.lang.String firstname, java.lang.String currency) throws java.rmi.RemoteException;
    public boolean deposit(int accountId, double amount) throws java.rmi.RemoteException;
    public java.lang.String getDetailAccount(int accountId) throws java.rmi.RemoteException;
    public boolean withdrawal(int accountId, double amount) throws java.rmi.RemoteException;
    public java.lang.String getAccountCurrency(int accountId) throws java.rmi.RemoteException;
}
