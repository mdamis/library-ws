package fr.upem.main;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.rpc.ServiceException;

import fr.upem.client.Client;
import fr.upem.library.Book;

public class MainScenario {
	public static void main(String[] args) throws ServiceException,
			RemoteException {
		// creation des clients
		Client clientA = new Client();
		Client clientB = new Client();

		// connexion des utilisateurs
		clientA.signin("bcrochez", "abc");
		clientB.signup("newUser", "mdp");

		// creation des comptes
		int accountA = clientA.createAccount("crochez", "bastien", "EUR");
		clientA.setCurrentAccount(accountA);
		clientA.deposit(1000, "EUR");

		int accountB = clientB.createAccount("new", "user", "JPY");
		clientB.setCurrentAccount(accountB);
		clientB.deposit(10000, "JPY");
		
		List<Book> books;

		System.out.println("Ajout de livres au panier du clientA :");
		try {
			books = clientA.getAllBooks();
		} catch (RemoteException e) {
			books = new ArrayList<Book>();
		}
		for (Book book : books) {
			if (book.isAvailable()) {
				System.out.println(book.getISBN() + " : " + book.getTitle()
						+ " par " + book.getAuthor() + " ajoute au panier");
				clientA.addToCart(book.getISBN(), 1);
			} else {
				System.out.println(book.getISBN() + " : " + book.getTitle()
						+ " par " + book.getAuthor() + " non disponible");
			}
		}
		System.out.println("Panier : " + clientA.getCart());
		System.out.println("Compte A : " + clientA.getAccountBalance() + " "
				+ clientA.getAccountCurrency());
		clientA.buyCart();
		System.out.println("Panier apres achat : " + clientA.getCart());
		System.out.println("Compte A : " + clientA.getAccountBalance() + " "
				+ clientA.getAccountCurrency());
		
		System.out.println("");
		
		System.out.println("Ajout de livres au panier du clientB :");
		try {
			books = clientA.getAllBooks();
		} catch (RemoteException e) {
			books = new ArrayList<Book>();
		}
		for (Book book : books) {
			if (book.isAvailable()) {
				System.out.println(book.getISBN() + " : " + book.getTitle()
						+ " par " + book.getAuthor() + " ajoute au panier");
				clientB.addToCart(book.getISBN(), 1);
			} else {
				System.out.println(book.getISBN() + " : " + book.getTitle()
						+ " par " + book.getAuthor() + " non disponible");
			}
		}
		System.out.println("Panier : " + clientB.getCart());
		System.out.println("Compte B : " + clientB.getAccountBalance() + " "
				+ clientB.getAccountCurrency());
		clientB.buyCart();
		System.out.println("Panier apres achat : " + clientB.getCart());
		System.out.println("Compte B : " + clientB.getAccountBalance() + " "
				+ clientB.getAccountCurrency());
		
		System.out.println();
		
		// deconnexion
		clientA.disconnect();
		clientB.disconnect();

	}
}
