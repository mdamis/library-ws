/**
 * Library.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package fr.upem.library;

public interface Library extends java.rmi.Remote {
    public void add(java.lang.String isbn, java.lang.String title, java.lang.String author, float price, java.lang.String introductionDate) throws java.rmi.RemoteException;
    public fr.upem.book.Book[] getAllBooks() throws java.rmi.RemoteException;
    public void sellBook(java.lang.String isbn, int exemplary) throws java.rmi.RemoteException;
}
