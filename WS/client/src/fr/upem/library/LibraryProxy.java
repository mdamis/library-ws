package fr.upem.library;

public class LibraryProxy implements fr.upem.library.Library {
  private String _endpoint = null;
  private fr.upem.library.Library library = null;
  
  public LibraryProxy() {
    _initLibraryProxy();
  }
  
  public LibraryProxy(String endpoint) {
    _endpoint = endpoint;
    _initLibraryProxy();
  }
  
  private void _initLibraryProxy() {
    try {
      library = (new fr.upem.library.LibraryServiceLocator()).getLibrary();
      if (library != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)library)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)library)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (library != null)
      ((javax.xml.rpc.Stub)library)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public fr.upem.library.Library getLibrary() {
    if (library == null)
      _initLibraryProxy();
    return library;
  }
  
  public void add(java.lang.String isbn, java.lang.String title, java.lang.String author, float price, java.lang.String introductionDate) throws java.rmi.RemoteException{
    if (library == null)
      _initLibraryProxy();
    library.add(isbn, title, author, price, introductionDate);
  }
  
  public fr.upem.book.Book[] getAllBooks() throws java.rmi.RemoteException{
    if (library == null)
      _initLibraryProxy();
    return library.getAllBooks();
  }
  
  public void sellBook(java.lang.String isbn, int exemplary) throws java.rmi.RemoteException{
    if (library == null)
      _initLibraryProxy();
    library.sellBook(isbn, exemplary);
  }
  
  
}