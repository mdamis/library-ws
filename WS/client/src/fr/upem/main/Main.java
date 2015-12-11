package fr.upem.main;

import javafx.application.Application;
import fr.upem.client.GUI;

public class Main {

	public static void main(String[] args) {
		try {
			Application.launch(GUI.class, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
