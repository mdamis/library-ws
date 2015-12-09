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
import javafx.scene.control.TableRow;
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
	private final static int WIDTH = 720;
	private final static int HEIGHT = 480;
	private LibraryClient client;
	private List<Book> books = new ArrayList<>();

	@Override
	public void start(Stage primaryStage) {
		try {
			client = new LibraryClient();
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		primaryStage.setTitle("MLV Library");
		Scene scene = createSignInPage(primaryStage);
		showScene(primaryStage, scene);
	}

	private Scene createSignInPage(Stage primaryStage) {
		GridPane grid = createGrid();

		Text scenetitle = new Text("Welcome to MLVLib");
		scenetitle.setId("welcome-text");
		grid.add(scenetitle, 0, 0, 2, 1);

		TextField userTextField = addTextField(grid, "Username:", 0, 1);

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
		grid.add(actiontarget, 0, 4);
		GridPane.setColumnSpan(actiontarget, 2);
		GridPane.setHalignment(actiontarget, RIGHT);
		actiontarget.setId("actiontarget");

		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				connectionHandler(primaryStage, userTextField, actiontarget);
			}
		});
		userTextField.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {
					connectionHandler(primaryStage, userTextField, actiontarget);
				}
			}
		});

		Scene scene = new Scene(grid, WIDTH, HEIGHT);
		scene.getStylesheets().add(GUI.class.getResource("style.css").toExternalForm());
		return scene;
	}

	private void connectionHandler(Stage primaryStage, TextField userTextField, final Text actiontarget) {
		String username = userTextField.getText();
		System.out.println("user: " + username);
		if (username.equals("")) {
			actiontarget.setFill(Color.FIREBRICK);
			actiontarget.setText("Enter a valid user name");
		} else {
			try {
				client.setUser(client.getLibrary().connect(username));
			} catch (RemoteException e) {
				e.printStackTrace();
				// TODO create an error connection page
			}
			if (client.getUser() == null) {
				actiontarget.setFill(Color.FIREBRICK);
				actiontarget.setText("Enter a valid user name");
			} else {
				System.out.println("Connected as " + client.getUsername());
				Scene scene = createIndexPage(primaryStage);
				showScene(primaryStage, scene);
			}
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

	@SuppressWarnings("unchecked")
	private Scene createIndexPage(Stage stage) {

		// Get Books from the Library

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
		TableColumn<Book, String> patronColumn = new TableColumn<>("Patron");
		// Link columns to the corresponding BookImpl properties
		isbnColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
		titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
		patronColumn.setCellValueFactory(new PropertyValueFactory<>("patronUsername"));

		tableView.setItems(observableBooks);
		tableView.getColumns().addAll(isbnColumn, titleColumn, authorColumn, patronColumn);
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

		Text sceneTitle = new Text("Connected as " + client.getUsername());
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

		Button btnSwitchBorrowed = new Button("Borrowed Book(s)");
		grid.add(btnSwitchBorrowed, 1, 1);
		btnSwitchBorrowed.setOnAction(new EventHandler<ActionEvent>() {
			private boolean isActive = false;

			@Override
			public void handle(ActionEvent event) {
				if (!isActive) {
					try {
						books = client.getUser().getBorrowedBooks();
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				} else {
					try {
						books = client.getAllBooks();
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
				observableBooks.clear();
				observableBooks.addAll(books);
				isActive = !isActive;
			}
		});

		Button btnAddBook = new Button("Add a book");
		grid.add(btnAddBook, 2, 1);
		btnAddBook.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Scene scene = createAddBookPage(stage);
				showScene(stage, scene);
			}
		});

		Button btnBorrow = new Button("Borrow");
		grid.add(btnBorrow, 5, 0);
		btnBorrow.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Book book = tableView.getSelectionModel().getSelectedItem();
				try {
					client.getLibrary().borrowBook(book, client.getUser());
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		});
		
		Button btnReturn = new Button("Return");
		grid.add(btnReturn, 5, 1);
		btnReturn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Book book = tableView.getSelectionModel().getSelectedItem();
				try {
					client.getLibrary().returnBook(book, client.getUser());
				} catch (RemoteException e) {
					e.printStackTrace();
				}
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

	private Scene createBookDetailsPage(Stage stage, Book book) {
		GridPane grid = createGrid();

		try {
			grid.add(new Text(book.getTitle()), 0, 0);
			grid.add(new Text("ISBN : " + book.getISBN()), 0, 1);
			grid.add(new Text("Author : " + book.getAuthor()), 0, 2);
			grid.add(new Text("Summary :\n" + book.getSummary()), 1, 0);
			grid.add(new Text("Available : " + book.isAvailable()), 0, 3);
			if (!book.isAvailable()) {
				grid.add(new Text("Patron : " + book.getPatron().getUsername()), 1, 3);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		Button backButton = new Button("Back");
		grid.add(backButton, 0, 5);
		backButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Scene scene = createIndexPage(stage);
				showScene(stage, scene);
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
				if (isbn.equals("") || title.equals("") || author.equals("")) {
					// TODO show error
					System.err.println("invalid book");
				} else {
					client.addBook(isbn, title, author);
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
		grid.add(textField, col + 1, row);
		return textField;
	}

	private void showScene(Stage stage, Scene scene) {
		stage.setScene(scene);
		stage.show();
	}
}
