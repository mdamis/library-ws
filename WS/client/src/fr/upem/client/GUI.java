package fr.upem.client;

import static javafx.geometry.HPos.RIGHT;

import java.rmi.RemoteException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUI extends Application {
	private final static int WIDTH = 720;
	private final static int HEIGHT = 480;
	private Client client;

	@Override
	public void start(Stage primaryStage) throws Exception {
		client = new Client();

		primaryStage.setTitle("MLV Library");
		Scene scene = createSignInPage(primaryStage);
		showScene(primaryStage, scene);
	}

	private TextField addTextField(GridPane grid, String label, int col, int row) {
		Label lab = new Label(label);
		grid.add(lab, col, row);
		TextField textField = new TextField();
		grid.add(textField, col + 1, row);
		return textField;
	}

	private GridPane createGrid() {
		return createGrid(10, 25);
	}

	private GridPane createGrid(double gap, double padding) {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(gap);
		grid.setVgap(gap);
		grid.setPadding(new Insets(padding, padding, padding, padding));
		return grid;
	}

	private void showScene(Stage stage, Scene scene) {
		stage.setScene(scene);
		stage.show();
	}

	private Scene createSignInPage(Stage primaryStage) {
		GridPane grid = createGrid();

		Text scenetitle = new Text("Welcome to MLVLib");
		scenetitle.setId("welcome-text");
		grid.add(scenetitle, 0, 0, 2, 1);

		TextField userTextField = addTextField(grid, "Username:", 0, 1);

		Label pw = new Label("Password:");
		grid.add(pw, 0, 2);
		PasswordField pwBox = new PasswordField();
		grid.add(pwBox, 1, 2);

		Button btn = new Button("Sign in");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		grid.add(hbBtn, 1, 4);

		Button btnQuit = new Button("Quit");
		HBox hbBtnQuit = new HBox(10);
		hbBtnQuit.setAlignment(Pos.BOTTOM_LEFT);
		hbBtnQuit.getChildren().add(btnQuit);
		grid.add(hbBtnQuit, 0, 4);

		final Text actiontarget = new Text();
		actiontarget.setId("actiontarget");
		grid.add(actiontarget, 0, 5);
		GridPane.setColumnSpan(actiontarget, 2);
		GridPane.setHalignment(actiontarget, RIGHT);
		actiontarget.setId("actiontarget");

		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				connectionHandler(primaryStage, userTextField, pwBox,
						actiontarget);
			}
		});
		btnQuit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				primaryStage.close();
			}
		});
		userTextField.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {
					connectionHandler(primaryStage, userTextField, pwBox,
							actiontarget);
				}
			}
		});
		pwBox.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {
					connectionHandler(primaryStage, userTextField, pwBox,
							actiontarget);
				}
			}
		});

		Scene scene = new Scene(grid, WIDTH, HEIGHT);
		scene.getStylesheets().add(
				GUI.class.getResource("style.css").toExternalForm());
		return scene;
	}

	private void connectionHandler(Stage primaryStage, TextField userTextField,
			PasswordField pwField, final Text actiontarget) {
		String username = userTextField.getText();
		String password = pwField.getText();
		System.out.println("user: " + username);
		if (username.equals("")) {
			actiontarget.setFill(Color.FIREBRICK);
			actiontarget.setText("Enter a valid user name");
		} else {
			try {
				client.getManager().connect(username, password);
				System.out.println("Connected as " + username);
				// Scene scene = createIndexPage(primaryStage);
				// showScene(primaryStage, scene);
			} catch (RemoteException e) {
				e.printStackTrace();
				// TODO create an error connection page
				actiontarget.setFill(Color.FIREBRICK);
				actiontarget.setText("Enter a valid user name");
			}
		}
	}

}
