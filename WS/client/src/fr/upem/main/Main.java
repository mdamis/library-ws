package fr.upem.main;

import java.rmi.RemoteException;
import java.util.HashMap;

import javax.xml.rpc.ServiceException;

import fr.upem.user.UserManager;
import fr.upem.user.UserManagerServiceLocator;

public class Main {
	public static void main(String[] args) {
		UserManager manager;
		try {
			manager = new UserManagerServiceLocator().getUserManager();
			try {
				manager.registerUser("bcrochez", "abc");
			} catch (RemoteException e) {
				System.err.println("error register");
			}
			try {
				if (manager.connect("bcrochez", "abc")) {
					System.out.println("bcrochez is now connected");
				}
			} catch (RemoteException e) {
				System.err.println("error connect");
			}
			try {
				if (manager.disconnect("bcrochez")) {
					System.out.println("bcrochez has been disconnected");
				}
			} catch (RemoteException e) {
				System.err.println("error disconnect");
			}

		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

}
