package fr.upem.user;

public class UserManagerProxy implements fr.upem.user.UserManager {
  private String _endpoint = null;
  private fr.upem.user.UserManager userManager = null;
  
  public UserManagerProxy() {
    _initUserManagerProxy();
  }
  
  public UserManagerProxy(String endpoint) {
    _endpoint = endpoint;
    _initUserManagerProxy();
  }
  
  private void _initUserManagerProxy() {
    try {
      userManager = (new fr.upem.user.UserManagerServiceLocator()).getUserManager();
      if (userManager != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)userManager)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)userManager)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (userManager != null)
      ((javax.xml.rpc.Stub)userManager)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public fr.upem.user.UserManager getUserManager() {
    if (userManager == null)
      _initUserManagerProxy();
    return userManager;
  }
  
  public void registerUser(java.lang.String user, java.lang.String password) throws java.rmi.RemoteException{
    if (userManager == null)
      _initUserManagerProxy();
    userManager.registerUser(user, password);
  }
  
  public boolean disconnect(java.lang.String user) throws java.rmi.RemoteException{
    if (userManager == null)
      _initUserManagerProxy();
    return userManager.disconnect(user);
  }
  
  public boolean connect(java.lang.String user, java.lang.String password) throws java.rmi.RemoteException{
    if (userManager == null)
      _initUserManagerProxy();
    return userManager.connect(user, password);
  }
  
  
}