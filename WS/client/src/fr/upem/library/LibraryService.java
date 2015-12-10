/**
 * LibraryService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package fr.upem.library;

public interface LibraryService extends javax.xml.rpc.Service {
    public java.lang.String getLibraryAddress();

    public fr.upem.library.Library getLibrary() throws javax.xml.rpc.ServiceException;

    public fr.upem.library.Library getLibrary(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
