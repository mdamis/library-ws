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
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
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

		Button btnSignIn = new Button("Sign in");
		HBox hBox = new HBox(25);
		
		Button btnQuit = new Button("Quit");
		HBox hbBtnQuit = new HBox(10);
		hBox.getChildren().addAll(btnQuit, btnSignIn);
		grid.add(hBox, 1, 5);

		final Text actiontarget = new Text();
		actiontarget.setId("actiontarget");
		grid.add(actiontarget, 0, 3, 2, 1);
		GridPane.setHalignment(actiontarget, RIGHT);

		btnSignIn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				connectionHandler(primaryStage, userTextField, actiontarget);
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
					connectionHandler(primaryStage, userTextField, actiontarget);
				}
			}
		});

		Scene scene = new Scene(grid, WIDTH, HEIGHT);
		scene.getStylesheets().add(GUI.class.getResource(client.getStyleSheetPath()).toExternalForm());
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
		
		TextField searchTextField = new TextField();
		searchTextField.setPromptText("Search a book, an author, ...");
		grid.add(searchTextField, 2, 0);
		
		Button searchButton = new Button("Search");
		grid.add(searchButton, 5, 0);
		searchButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					books = client.getLibrary().searchBook(searchTextField.getText());
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				observableBooks.clear();
				observableBooks.addAll(books);
			}
		});
		
		Button btnRefresh = new Button("Refresh");
		btnRefresh.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				try {
					books = client.getAllBooks();
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				observableBooks.clear();
				observableBooks.addAll(books);
			}
		});

		Button btnSwitchBorrowed = new Button("Borrowed Book(s)");
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
		btnAddBook.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Scene scene = createAddBookPage(stage);
				showScene(stage, scene);
			}
		});
		
		HBox hBox = new HBox();
		hBox.setSpacing(15);
		hBox.getChildren().addAll(btnRefresh, btnSwitchBorrowed, btnAddBook);
		grid.add(hBox, 0, 1);

		Button btnBorrow = new Button("Borrow");
		btnBorrow.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(tableView.getSelectionModel().getSelectedItem() != null) {
					Book book = tableView.getSelectionModel().getSelectedItem();
					try {
						client.getLibrary().borrowBook(book, client.getUser());
						books = client.getAllBooks();
					} catch (RemoteException e) {
						e.printStackTrace();
					}
					observableBooks.clear();
					observableBooks.addAll(books);
				}
			}
		});
		
		Button btnReturn = new Button("Return");
		btnReturn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(tableView.getSelectionModel().getSelectedItem() != null) {
					Book book = tableView.getSelectionModel().getSelectedItem();
					try {
						client.getLibrary().returnBook(book, client.getUser());
						books = client.getAllBooks();
					} catch (RemoteException e) {
						e.printStackTrace();
					}
					observableBooks.clear();
					observableBooks.addAll(books);
				}
			}
		});
		
		VBox vBox = new VBox();
		vBox.setSpacing(25);
		vBox.getChildren().addAll(btnBorrow, btnReturn);
		grid.add(vBox, 5, 2);

		grid.add(tableView, 0, 2, 3, 1);

		Button switchStyleButton = new Button("Switch Style");
		grid.add(switchStyleButton, 0, 3);
		switchStyleButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				client.switchStyleSheet();
				Scene scene = createIndexPage(stage);
				showScene(stage, scene);
			}
		});
		
		Button btnQuit = new Button("Quit");
		grid.add(btnQuit, 5, 3);
		btnQuit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stage.close();
			}
		});

		Scene scene = new Scene(grid, WIDTH, HEIGHT);
		scene.getStylesheets().add(GUI.class.getResource(client.getStyleSheetPath()).toExternalForm());
		return scene;
	}

	private Scene createBookDetailsPage(Stage stage, Book book) {
		GridPane grid = createGrid();

		final VBox vBox = new VBox();
		vBox.setSpacing(10);
		vBox.setPrefSize(300, 300);
		
		final ScrollPane scrollPane = new ScrollPane();
		scrollPane.setPrefSize(300, 150);
		
		try {
			Text title = new Text(book.getTitle());
			title.setId("title");
			grid.add(title, 0, 0);
			Text author = new Text("by " + book.getAuthor());
			author.setFont(Font.font("Helvetica", FontPosture.ITALIC, 12));
			author.setId("author");
			grid.add(author, 0, 1);
			vBox.getChildren().add(new Text("ISBN : " + book.getISBN()));
			if(book.isAvailable()) {
				vBox.getChildren().add(new Text("This book is available"));
			} else {
				vBox.getChildren().add(new Text("This book is not available"));
				vBox.getChildren().add(new Text("Borrowed by " + book.getPatron().getUsername()));
			}
			vBox.getChildren().add(new Text("Summary :"));
			Text summary = new Text(book.getSummary());
			scrollPane.setContent(summary);
			summary.setWrappingWidth(250);
			vBox.getChildren().add(scrollPane);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		Button editSummaryButton = new Button("Edit Summary");
		editSummaryButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Scene scene = createUpdateSummaryPage(stage, book);
				showScene(stage, scene);
			}
		});
		vBox.getChildren().add(editSummaryButton);
		
		grid.add(vBox, 0, 3);

		List<String> reviews = new ArrayList<>();
		try {
			reviews = book.getReviews();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		ObservableList<String> observableReviews = FXCollections.observableArrayList(reviews);
		
		ListView<String> listView = new ListView<>(observableReviews);
		listView.setPrefSize(300, 300);
		
		grid.add(new Text("Reviews"), 1, 2);
		grid.add(listView, 1, 3);
		
		Button addReviewButton = new Button("Add review");
		grid.add(addReviewButton, 1, 4);
		addReviewButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Scene scene = createAddReviewPage(stage, book);
				showScene(stage, scene);
			}
		});
		
		Button backButton = new Button("Back");
		grid.add(backButton, 0, 6);
		backButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Scene scene = createIndexPage(stage);
				showScene(stage, scene);
			}
		});

		Scene scene = new Scene(grid, WIDTH, HEIGHT);
		scene.getStylesheets().add(GUI.class.getResource(client.getStyleSheetPath()).toExternalForm());

		return scene;
	}
	
	private Scene createUpdateSummaryPage(Stage stage, Book book) {
		GridPane grid = createGrid();
		
		Text sceneTitle = new Text("Edit summary");
		sceneTitle.setId("welcome-text");
		HBox hBox = new HBox();
		hBox.getChildren().add(sceneTitle);
		hBox.setAlignment(Pos.CENTER);
		grid.add(hBox, 0, 0);
		
		TextArea summaryTextArea = new TextArea();
		summaryTextArea.setPrefSize(350, 150);
		try {
			summaryTextArea.setText(book.getSummary());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		grid.add(summaryTextArea, 0, 2);
		
		Button backButton = new Button("Back");
		backButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Scene scene = createBookDetailsPage(stage, book);
				showScene(stage, scene);
			}
		});
		
		Button sendButton = new Button("Edit");
		sendButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String summary = summaryTextArea.getText();
				if (summary.equals("")) {
					summary = "No summary available";
				}
				try {
					book.setSummary(summary);
				} catch (RemoteException e) {
					e.printStackTrace();
				}

				Scene scene = createBookDetailsPage(stage, book);
				showScene(stage, scene);
			}
		});
		
		hBox = new HBox();
		hBox.setSpacing(40);
		hBox.setAlignment(Pos.CENTER);
		hBox.getChildren().addAll(sendButton, backButton);
		grid.add(hBox, 0, 3);
		
		Scene scene = new Scene(grid, WIDTH, HEIGHT);
		scene.getStylesheets().add(GUI.class.getResource(client.getStyleSheetPath()).toExternalForm());

		return scene;
	}

	private Scene createAddReviewPage(Stage stage, Book book) {
		GridPane grid = createGrid();
				
		Text sceneTitle = new Text("Add a review");
		sceneTitle.setId("welcome-text");
		HBox hBox = new HBox();
		hBox.getChildren().add(sceneTitle);
		hBox.setAlignment(Pos.CENTER);
		grid.add(hBox, 0, 0);
		
		TextArea reviewTextArea = new TextArea();
		reviewTextArea.setPrefSize(350, 150);
		grid.add(reviewTextArea, 0, 2);
		
		Button backButton = new Button("Back");
		backButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Scene scene = createBookDetailsPage(stage, book);
				showScene(stage, scene);
			}
		});
		
		Button sendButton = new Button("Send");
		sendButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String review = reviewTextArea.getText();
				if (review.equals("")) {
					System.err.println("empty review");
				} else {
					try {
						book.addReview(review, client.getUser());
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
				Scene scene = createBookDetailsPage(stage, book);
				showScene(stage, scene);
			}
		});
		
		hBox = new HBox();
		hBox.setSpacing(40);
		hBox.setAlignment(Pos.CENTER);
		hBox.getChildren().addAll(sendButton, backButton);
		grid.add(hBox, 0, 3);
		
		Scene scene = new Scene(grid, WIDTH, HEIGHT);
		scene.getStylesheets().add(GUI.class.getResource(client.getStyleSheetPath()).toExternalForm());

		return scene;
	}
	
	private Scene createAddBookPage(Stage stage) {
		GridPane grid = createGrid();

		Text sceneTitle = new Text("Add a book");
		sceneTitle.setId("welcome-text");
		grid.add(sceneTitle, 0, 0, 2, 1);

		TextField isbnField = addTextField(grid, "ISBN:", 0, 1);
		TextField titleField = addTextField(grid, "Title:", 0, 2);
		TextField authorField = addTextField(grid, "Author:", 0, 3);

		Button backButton = new Button("Back");
		grid.add(backButton, 0, 5);
		backButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Scene scene = createIndexPage(stage);
				showScene(stage, scene);
			}
		});
		
		Button btnSend = new Button("OK");
		HBox hbBtnSend = new HBox(10);
		hbBtnSend.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtnSend.getChildren().add(btnSend);
		grid.add(hbBtnSend, 1, 5);
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
		scene.getStylesheets().add(GUI.class.getResource(client.getStyleSheetPath()).toExternalForm());

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
