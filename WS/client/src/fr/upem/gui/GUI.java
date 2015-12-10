package fr.upem.gui;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.rpc.ServiceException;

import fr.upem.client.Client;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import fr.upem.book.Book;

public class GUI extends Application {
	private final static int WIDTH = 720;
	private final static int HEIGHT = 480;
	private Client client;
	private List<Book> books = new ArrayList<>();

	@Override
	public void start(Stage primaryStage) {
		try {
			client = new Client();
		} catch (ServiceException e) {
			System.err.println("Gui.java : start method failed");
		}
		primaryStage.setTitle("MLV Store");
		Scene scene = createSignInPage(primaryStage);
		showScene(primaryStage, scene);
	}

	private Scene createSignInPage(Stage primaryStage) {
		GridPane grid = createGrid();

		Text scenetitle = new Text("Welcome to MLVLib Store");
		scenetitle.setId("welcome-text");
		grid.add(scenetitle, 0, 0, 2, 1);

		VBox vb = new VBox();
		HBox hb = new HBox();
		hb.setSpacing(0);
		Label label = new Label("Username:");
		TextField userTextField = new TextField();
		hb.getChildren().addAll(label, userTextField);
		vb.getChildren().addAll(hb);

		hb = new HBox();
		hb.setSpacing(0);
		label = new Label("Password: ");
		PasswordField pb = new PasswordField();
		hb.getChildren().addAll(label, pb);
		vb.getChildren().addAll(hb);

		final Text message = new Text("");
		vb.getChildren().addAll(message);
		grid.add(vb, 1, 1);

		Button btnSI = new Button("Sign in");
		Button btnSU = new Button("Sign up");
		HBox hbBtn = new HBox(10);

		hbBtn.getChildren().addAll(btnSU, btnSI);
		grid.add(hbBtn, 1, 4);

		Button btnQuit = new Button("Quit");
		HBox hbBtnQuit = new HBox(10);
		hbBtnQuit.setAlignment(Pos.BOTTOM_LEFT);
		hbBtnQuit.getChildren().add(btnQuit);
		grid.add(hbBtnQuit, 0, 5);

		btnSI.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				signinHandler(primaryStage, userTextField, pb, message);
			}
		});

		btnSU.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				signupHandler(primaryStage, userTextField, pb, message);
			}
		});

		btnQuit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				primaryStage.close();
				System.out.println("bye");
			}
		});

		pb.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {
					signinHandler(primaryStage, userTextField, pb, message);
				}
			}
		});

		Scene scene = new Scene(grid, WIDTH, HEIGHT);
		scene.getStylesheets().add(GUI.class.getResource("style.css").toExternalForm());
		return scene;
	}

	private void signinHandler(Stage primaryStage, TextField userTextField, PasswordField pb, final Text actiontarget) {
		String username = userTextField.getText();
		String passwd = pb.getText();
		System.out.println("signin user: " + username);
		System.out.println("signin passwd: " + passwd);
		try {
			if (!client.userExist(username, passwd)) {
				System.out.println("the mofo is out");
				actiontarget.setFill(Color.FIREBRICK);
				actiontarget.setText("Enter a valid username and passwd or signup");
			} else {
				System.out.println("the mofo is in");
				Scene scene = createIndexPage(primaryStage);
				showScene(primaryStage, scene);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void signupHandler(Stage primaryStage, TextField userTextField, PasswordField pb, final Text actiontarget) {
		String username = userTextField.getText();
		String passwd = pb.getText();
		System.out.println("signup user: " + username);
		System.out.println("signup passwd: " + passwd);
		try {
			if (!client.userExist(username, passwd)) {
				System.out.println("the mofo is in");
				System.out.println(client.signin(username, passwd));
				Scene scene = createIndexPage(primaryStage);
				showScene(primaryStage, scene);
			} else {
				System.out.println("the mofo is already in");
				actiontarget.setFill(Color.FIREBRICK);
				actiontarget.setText("You already have an account");
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Scene createIndexPage(Stage stage) {
		System.out.println("Creation en cours ...");

		try {
			books = client.getAllBooks();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		// Create an ObservableList from the ArrayList
		ObservableList<Book> observableBooks = FXCollections.observableArrayList(books);

		// Create table
		TableView<Book> tableView = new TableView<>();
		tableView.setEditable(false);
		// Create columns
		TableColumn<Book, String> isbnColumn = new TableColumn<>("ISBN");
		TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
		TableColumn<Book, String> authorColumn = new TableColumn<>("Author");

		// Link columns to the corresponding BookImpl properties
		isbnColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
		titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

		tableView.setItems(observableBooks);
		tableView.getColumns().addAll(isbnColumn, titleColumn, authorColumn);
		// Customize the TableView
		tableView.setPrefWidth(600);
		tableView.setPrefHeight(400);
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		// Handle double click on a table row
		tableView.setRowFactory(tv -> {
			TableRow<Book> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					Book book = row.getItem();
					Scene scene = createBookDetailsPage(stage, book);
					showScene(stage, scene);
				}
			});
			return row;
		});
		// Create the grid
		GridPane grid = createGrid();

		Text sceneTitle;
		try {
			sceneTitle = new Text("Connected as " + client.getUsername());
		} catch (RemoteException e) {
			System.err.println("GUI.java, client.getUserName RemoteException :"+e);
		} finally {
			sceneTitle = new Text("Not Connected ");
		}
		sceneTitle.setId("user-name");
		grid.add(sceneTitle, 0, 0);

		Button btnRefresh = new Button("Refresh");
		grid.add(btnRefresh, 0, 1);
		btnRefresh.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Scene scene = createIndexPage(stage);
				showScene(stage, scene);
			}
		});

		Button btnBuyBook = new Button("Buy the book");
		grid.add(btnBuyBook, 2, 1);
		btnBuyBook.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Scene scene = createBuyBookPage(stage);
				showScene(stage, scene);
			}
		});
	
	
		grid.add(tableView, 0, 2, 3, 1);

		Button btnQuit = new Button("Quit");
		grid.add(btnQuit, 5, 3);
		btnQuit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stage.close();
			}
		});

		Scene scene = new Scene(grid, WIDTH, HEIGHT);
		scene.getStylesheets().add(GUI.class.getResource("style.css").toExternalForm());
		return scene;
		

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

	private Scene createBuyBookPage(Stage stage){
		//TODO createBuyBook
		return null;
	}
	
	private Scene createBookDetailsPage(Stage stage,Book book){
		//TODO createBookDetail
		return null;
	}
	/*
	 * 
	 * private Scene createAddBookPage(Stage stage) { GridPane grid =
	 * createGrid();
	 * 
	 * Text sceneTitle = new Text("Add a book");
	 * sceneTitle.setId("welcome-text"); grid.add(sceneTitle, 0, 0, 2, 1);
	 * 
	 * TextField isbnField = addTextField(grid, "ISBN:", 0, 1); TextField
	 * titleField = addTextField(grid, "Title:", 0, 2); TextField authorField =
	 * addTextField(grid, "Author:", 0, 3);
	 * 
	 * Button backButton = new Button("Back"); grid.add(backButton, 0, 5);
	 * backButton.setOnAction(new EventHandler<ActionEvent>() {
	 * 
	 * @Override public void handle(ActionEvent event) { Scene scene =
	 * createIndexPage(stage); showScene(stage, scene); } });
	 * 
	 * Button btnSend = new Button("OK"); HBox hbBtnSend = new HBox(10);
	 * hbBtnSend.setAlignment(Pos.BOTTOM_RIGHT);
	 * hbBtnSend.getChildren().add(btnSend); grid.add(hbBtnSend, 1, 5);
	 * btnSend.setOnAction(new EventHandler<ActionEvent>() {
	 * 
	 * @Override public void handle(ActionEvent event) { String isbn =
	 * isbnField.getText(); String title = titleField.getText(); String author =
	 * authorField.getText(); if (isbn.equals("") || title.equals("") ||
	 * author.equals("")) { // TODO show error System.err.println("invalid book"
	 * ); } else { client.addBook(isbn, title, author); Scene scene =
	 * createIndexPage(stage); showScene(stage, scene); } } });
	 * 
	 * Scene scene = new Scene(grid, WIDTH, HEIGHT);
	 * scene.getStylesheets().add(GUI.class.getResource("style.css").
	 * toExternalForm());
	 * 
	 * return scene; }
	 */
	private TextField addTextField(GridPane grid, String label, int col, int row) {
		Label lab = new Label(label);
		grid.add(lab, col, row);
		TextField textField = new TextField();
		grid.add(textField, col + 1, row);
		return textField;
	}

	private void showScene(Stage stage, Scene scene) {
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		try {
			Application.launch(GUI.class, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
