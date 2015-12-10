/**
 * UserManager.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package fr.upem.user;

public interface UserManager extends java.rmi.Remote {
    public boolean disconnect(java.lang.String user) throws java.rmi.RemoteException;
    public boolean exist(java.lang.String user, java.lang.String password) throws java.rmi.RemoteException;
    public java.lang.String getUsernameConnected() throws java.rmi.RemoteException;
    public void registerUser(java.lang.String user, java.lang.String password) throws java.rmi.RemoteException;
    public boolean connect(java.lang.String user, java.lang.String password) throws java.rmi.RemoteException;
}
