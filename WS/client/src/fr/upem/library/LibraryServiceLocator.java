/**
 * LibraryServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package fr.upem.library;

public class LibraryServiceLocator extends org.apache.axis.client.Service implements fr.upem.library.LibraryService {

    public LibraryServiceLocator() {
    }


    public LibraryServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public LibraryServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for Library
    private java.lang.String Library_address = "http://localhost:8080/server/services/Library";

    public java.lang.String getLibraryAddress() {
        return Library_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String LibraryWSDDServiceName = "Library";

    public java.lang.String getLibraryWSDDServiceName() {
        return LibraryWSDDServiceName;
    }

    public void setLibraryWSDDServiceName(java.lang.String name) {
        LibraryWSDDServiceName = name;
    }

    public fr.upem.library.Library getLibrary() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(Library_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getLibrary(endpoint);
    }

    public fr.upem.library.Library getLibrary(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            fr.upem.library.LibrarySoapBindingStub _stub = new fr.upem.library.LibrarySoapBindingStub(portAddress, this);
            _stub.setPortName(getLibraryWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setLibraryEndpointAddress(java.lang.String address) {
        Library_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (fr.upem.library.Library.class.isAssignableFrom(serviceEndpointInterface)) {
                fr.upem.library.LibrarySoapBindingStub _stub = new fr.upem.library.LibrarySoapBindingStub(new java.net.URL(Library_address), this);
                _stub.setPortName(getLibraryWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("Library".equals(inputPortName)) {
            return getLibrary();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://library.upem.fr", "LibraryService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://library.upem.fr", "Library"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("Library".equals(portName)) {
            setLibraryEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
