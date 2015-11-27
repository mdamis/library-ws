import static javafx.geometry.HPos.RIGHT;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUI extends Application {
	private final static int WIDTH = 600;
	private final static int HEIGHT = 400;
	private LibraryClient library;

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("MLV Library");
		Scene scene = createSignInPage(primaryStage);
		showScene(primaryStage, scene);
	}

	private Scene createSignInPage(Stage primaryStage) {
		GridPane grid = createGrid();

		Text scenetitle = new Text("Welcome to MLVLib");
		scenetitle.setId("welcome-text");
		grid.add(scenetitle, 0, 0, 2, 1);

		TextField userTextField = addTextField(grid, "User name:", 0, 1);
		
		/*
		 * Label pw = new Label("Password:"); grid.add(pw, 0, 2); PasswordField
		 * pwBox = new PasswordField(); grid.add(pwBox, 1, 2);
		 */

		Button btn = new Button("Sign in");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		grid.add(hbBtn, 1, 4);

		final Text actiontarget = new Text();
		actiontarget.setId("actiontarget");
		grid.add(actiontarget, 0, 6);
		GridPane.setColumnSpan(actiontarget, 2);
		GridPane.setHalignment(actiontarget, RIGHT);
		actiontarget.setId("actiontarget");

		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				connetionHandler(primaryStage, userTextField, actiontarget);
			}
		});
		userTextField.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.ENTER) {
					connetionHandler(primaryStage, userTextField, actiontarget);
				}
			}
		});

		Scene scene = new Scene(grid, WIDTH, HEIGHT);
		scene.getStylesheets().add(
				GUI.class.getResource("style.css").toExternalForm());
		return scene;
	}
	
	private void connetionHandler(Stage primaryStage,
			TextField userTextField, final Text actiontarget) {
		String user = userTextField.getText();
		System.out.println("user: "+user);
		if (user.equals("")) {
			actiontarget.setFill(Color.FIREBRICK);
			actiontarget.setText("Enter a valid user name");
		} else {
			try {
				library = new LibraryClient(user, "LibraryService");
			} catch (MalformedURLException | RemoteException
					| NotBoundException e1) {
				e1.printStackTrace();
				// TODO create an error connection page
			}
			Scene scene = createIndexPage(primaryStage);
			showScene(primaryStage, scene);
		}
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

	private Scene createIndexPage(Stage stage) {

		//Get Books from the Library
		List<Book> books = new ArrayList<>();
		try {
			books = library.getAllBooks();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		//Create an ObservableList from the ArrayList
		ObservableList<Book> observableBooks = FXCollections.observableArrayList(books);

		//Create table
		TableView tableView = new TableView();
		tableView.setEditable(false);
		//Create columns
		TableColumn isbnColumn = new TableColumn("ISBN");
		TableColumn titleColumn = new TableColumn("Title");
		TableColumn authorColumn = new TableColumn("Author");
		//Link columns to the corresponding BookImpl properties
		isbnColumn.setCellValueFactory(new PropertyValueFactory<BookImpl, String>("ISBN"));
		titleColumn.setCellValueFactory(new PropertyValueFactory<BookImpl, String>("title"));
		authorColumn.setCellValueFactory(new PropertyValueFactory<BookImpl, String>("author"));

		tableView.setItems(observableBooks);
		tableView.getColumns().addAll(isbnColumn, titleColumn, authorColumn);
		//Customize the TableView
		tableView.setPrefWidth(500);
		tableView.setPrefHeight(400);
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		//Create the grid
		GridPane grid = createGrid();

		Text sceneTitle = new Text("Connected as " + library.getUser());
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
		
		Button btnAddBook = new Button("Add a book");
		grid.add(btnAddBook, 1, 1);
		btnAddBook.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Scene scene = createAddBookPage(stage);
				showScene(stage, scene);
			}
		});
		
		grid.add(tableView, 0, 2, 3, 1);

		Button btnQuit = new Button("Quit");
		grid.add(btnQuit, 0, 3);
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

	private Scene createAddBookPage(Stage stage) {
		GridPane grid = createGrid();
		
		Text sceneTitle = new Text("Add a book");
		sceneTitle.setId("welcome-text");
		grid.add(sceneTitle, 0, 0);
		
		TextField isbnField = addTextField(grid, "ISBN:", 0, 1);
		TextField titleField = addTextField(grid, "Title:", 0, 2);
		TextField authorField = addTextField(grid, "Author:", 0, 3);
		
		Button btnSend = new Button("OK");
		grid.add(btnSend, 0, 5);
		btnSend.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String isbn = isbnField.getText();
				String title = titleField.getText();
				String author = authorField.getText();
				if(isbn.equals("") || title.equals("") || author.equals("")) {
					// TODO show error
					System.err.println("invalid book");
				} else {
					library.addBook(isbn, title, author);
					Scene scene = createIndexPage(stage);
					showScene(stage, scene);
				}
			}
		});
		
		Scene scene = new Scene(grid, WIDTH, HEIGHT);
		scene.getStylesheets().add(GUI.class.getResource("style.css").toExternalForm());

		return scene;
	}

	private TextField addTextField(GridPane grid, String label, int col, int row) {
		Label lab = new Label(label);
		grid.add(lab, col, row);
		TextField textField = new TextField();
		grid.add(textField, col+1, row);
		return textField;
	}

	private void showScene(Stage stage, Scene scene) {
		stage.setScene(scene);
		stage.show();
	}
}
