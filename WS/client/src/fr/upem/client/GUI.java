package fr.upem.client;

import static javafx.geometry.HPos.RIGHT;

import java.awt.image.RasterFormatException;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import net.restfulwebservices.www.DataContracts._2008._01.CurrencyCode;

import javax.xml.rpc.ServiceException;

import fr.upem.book.Book;

public class GUI extends Application {
	private final static int WIDTH = 720;
	private final static int HEIGHT = 480;
	private List<Book> books = new ArrayList<>();
	private Client client;

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			client = new Client();
		} catch (ServiceException e) {
			System.err.println("Gui.java : start method failed");
		}

		primaryStage.setTitle("MLV Store");
		Scene scene = createSignInPage(primaryStage);
		showScene(primaryStage, scene);
	}

	@Override
	public void stop() {
		client.disconnect();
	}

	private TextField addTextField(GridPane grid, String label, int col, int row) {
		Label lab = new Label(label);
		grid.add(lab, col, row);
		TextField textField = new TextField();
		grid.add(textField, col + 1, row);
		return textField;
	}

	private Label addLabelField(GridPane grid, String label, int col, int row) {
		Label lab = new Label(label);
		grid.add(lab, col, row);
		Label labField = new Label("");
		grid.add(labField, col + 1, row);
		return labField;
	}

	private ComboBox<String> addComboBoxCurrency(GridPane grid, String label, int col, int row) {
		Label lab = new Label(label);
		grid.add(lab, col, row);
		ObservableList<String> options = 
			    FXCollections.observableArrayList(
			        "EUR","USD","JPY","GBP","INR","AUD","CAD","CHF","SGD","CNY"
			       );
		ComboBox<String> comboBox = new ComboBox<String>( options);
	    comboBox.setValue("EUR");
		grid.add(comboBox, col+1, row);
		return comboBox;
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
		grid.setAlignment(Pos.CENTER);

		Text scenetitle = new Text("Welcome to MLVLib Store");
		scenetitle.setId("welcome-text");
		grid.add(scenetitle, 0, 0, 3, 1);

		TextField userTextField = addTextField(grid, "Username:", 0, 1);

		Label pw = new Label("Password:");
		grid.add(pw, 0, 2);
		PasswordField pwBox = new PasswordField();
		grid.add(pwBox, 1, 2);

		Button btnSignIn = new Button("Sign in");
		Button btnSignUp = new Button("Sign up");
		// HBox hbBtn = new HBox(10);
		// hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		// hbBtn.getChildren().addAll(btnSignIn, btnSignUp);
		GridPane.setHalignment(btnSignIn, RIGHT);
		grid.add(btnSignIn, 1, 3);
		grid.add(btnSignUp, 0, 3);

		Button btnQuit = new Button("Quit");
		HBox hbBtnQuit = new HBox(10);
		hbBtnQuit.setAlignment(Pos.BOTTOM_LEFT);
		hbBtnQuit.getChildren().add(btnQuit);
		grid.add(hbBtnQuit, 0, 5);

		final Text message = new Text();
		message.setId("actiontarget");
		grid.add(message, 0, 4);
		GridPane.setColumnSpan(message, 2);
		GridPane.setHalignment(message, RIGHT);
		message.setId("actiontarget");

		btnSignIn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				signinHandler(primaryStage, userTextField, pwBox, message);
			}
		});

		btnSignUp.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				signupHandler(primaryStage, userTextField, pwBox, message);
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
					signinHandler(primaryStage, userTextField, pwBox, message);
				}
			}
		});
		pwBox.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {
					signinHandler(primaryStage, userTextField, pwBox, message);
				}
			}
		});

		Scene scene = new Scene(grid, WIDTH, HEIGHT);
		scene.getStylesheets().add(
				GUI.class.getResource("style.css").toExternalForm());
		return scene;
	}

	private void signinHandler(Stage primaryStage, TextField userTextField,
			PasswordField pb, final Text actiontarget) {
		String username = userTextField.getText();
		String passwd = pb.getText();
		System.out.println("signin user: " + username);
		System.out.println("signin passwd: " + passwd);
		try {
			if (username.equals("") || passwd.equals("")
					|| !client.userExist(username, passwd)) {
				System.out.println("invalid param to sign in");
				actiontarget.setFill(Color.FIREBRICK);
				actiontarget
						.setText("Enter a valid username and passwd or signup");
			} else {
				System.out.println("try to connect");
				if (client.signin(username, passwd)) {
					System.out.println("Connected as " + username);
					Scene scene = createIndexPage(primaryStage);
					showScene(primaryStage, scene);
				} else {
					System.err.println("Failed to connect");
					actiontarget.setFill(Color.FIREBRICK);
					actiontarget
							.setText("Enter a valid username and passwd or signup");
				}
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void signupHandler(Stage primaryStage, TextField userTextField,
			PasswordField pb, final Text actiontarget) {
		String username = userTextField.getText();
		String passwd = pb.getText();
		System.out.println("signup user: " + username);
		System.out.println("signup passwd: " + passwd);
		try {
			if (!client.userExist(username, passwd)) {
				System.out.println("try to register");
				System.out.println(client.signup(username, passwd));
				Scene scene = createIndexPage(primaryStage);
				showScene(primaryStage, scene);
			} else {
				System.out.println("user is already in");
				actiontarget.setFill(Color.FIREBRICK);
				actiontarget.setText("You already have an account");
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private Scene createIndexPage(Stage stage) {
		System.out.println("Creation en cours ...");

		try {
			books = client.getAllBooks();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		// Create an ObservableList from the ArrayList
		ObservableList<Book> observableBooks = FXCollections
				.observableArrayList(books);

		// Create table
		TableView<Book> tableView = new TableView<>();
		tableView.setEditable(false);
		// Create columns
		TableColumn<Book, String> isbnColumn = new TableColumn<>("ISBN");
		TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
		TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
		TableColumn<Book, Integer> exemplaryColumn = new TableColumn<>(
				"Exemplary");
		TableColumn<Book, Float> priceColumn = new TableColumn<>("Price");
		TableColumn<Book, String> currencyColumn = new TableColumn<>("Currency");

		// Link columns to the corresponding BookImpl properties
		isbnColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
		titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
		exemplaryColumn.setCellValueFactory(new PropertyValueFactory<>(
				"exemplary"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		currencyColumn.setCellValueFactory(new PropertyValueFactory<>(
				"currency"));

		tableView.setItems(observableBooks);
		tableView.getColumns().addAll(isbnColumn, titleColumn, authorColumn,
				exemplaryColumn, priceColumn, currencyColumn);
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
		} catch (Exception e) {
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

		grid.add(tableView, 0, 2, 3, 1);

		Button btnQuit = new Button("Quit");
		GridPane.setHalignment(btnQuit, RIGHT);
		grid.add(btnQuit, 2, 3);
		btnQuit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stage.close();
			}
		});

		Scene scene = new Scene(grid, WIDTH, HEIGHT);
		scene.getStylesheets().add(
				GUI.class.getResource("style.css").toExternalForm());
		return scene;
	}

	private Scene createBankSigninPage(Stage stage, Book book) {
		GridPane grid = createGrid();

		Text scenetitle = new Text("Welcome to MLVLib Bank");
		scenetitle.setId("welcome-text");
		grid.add(scenetitle, 0, 0, 3, 1);

		TextField userTextField = addTextField(grid, "Account ID:", 0, 1);
		int account = client.getCurrentAccount();
		if (account != 0) {
			userTextField.setText(Integer.toString(account));
		}

		Button btnSignIn = new Button("Sign in");
		Button btnSignUp = new Button("Sign up");

		GridPane.setHalignment(btnSignIn, RIGHT);
		grid.add(btnSignIn, 1, 3);
		grid.add(btnSignUp, 0, 3);

		Button btnBack = new Button("Back");
		HBox hbBtnBack = new HBox(10);
		hbBtnBack.setAlignment(Pos.BOTTOM_LEFT);
		hbBtnBack.getChildren().add(btnBack);
		grid.add(hbBtnBack, 0, 4);

		final Text message = new Text();
		message.setId("actiontarget");
		grid.add(message, 0, 2);
		GridPane.setColumnSpan(message, 2);
		GridPane.setHalignment(message, RIGHT);
		message.setId("actiontarget");

		btnSignIn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					signinBankHandler(stage, userTextField, message, book);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		btnSignUp.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Scene scene = createBankSignupPage(stage, book);
				showScene(stage, scene);
			}
		});
		btnBack.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Scene scene = createBookDetailsPage(stage, book);
				showScene(stage, scene);
			}
		});
		userTextField.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {
					try {
						signinBankHandler(stage, userTextField, message, book);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		Scene scene = new Scene(grid, WIDTH, HEIGHT);
		scene.getStylesheets().add(
				GUI.class.getResource("style.css").toExternalForm());
		return scene;
	}

	private void signinBankHandler(Stage primaryStage, TextField userTextField,
			final Text actiontarget, Book book) throws RemoteException {
		String accountIdStr = userTextField.getText();
		System.out.println("signin accountId: " + accountIdStr);
		try {
			int accountId = Integer.parseInt(accountIdStr);
			if (client.accountExist(accountId)) {
				client.setCurrentAccount(accountId);
				Scene scene = createCommandPage(primaryStage, book);
				showScene(primaryStage, scene);
			} else {
				actiontarget.setFill(Color.FIREBRICK);
				actiontarget.setText("This Account does not exist");
			}

		} catch (NumberFormatException e1) {
			actiontarget.setFill(Color.FIREBRICK);
			actiontarget.setText("Account Id is not a valid number");
		}
	}

	private Scene createBankSignupPage(Stage stage, Book book) {
		GridPane grid = createGrid();

		Text scenetitle = new Text("Signup Bank page");
		scenetitle.setId("welcome-text");
		grid.add(scenetitle, 0, 0, 2, 1);

		TextField nameLabel = addTextField(grid, "Name :", 0, 1);
		TextField firstnameLabel = addTextField(grid, "Firstname :", 0, 2);
		ComboBox<String> currencyCombobox = addComboBoxCurrency(grid, "Account currency :", 0, 3);
		Button btn = new Button("Create Account");
		grid.add(btn, 1, 4);

		Button btnBack = new Button("Back");
		HBox hbBtnBack = new HBox(10);
		hbBtnBack.setAlignment(Pos.BOTTOM_LEFT);
		hbBtnBack.getChildren().add(btnBack);
		grid.add(hbBtnBack, 0, 4);

		final Text message = new Text();
		grid.add(message, 0, 5);

		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				signupBankHandler(nameLabel,firstnameLabel,currencyCombobox,message,stage,book);
			}
		});

		btnBack.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Scene scene = createIndexPage(stage);
				showScene(stage, scene);
			}
		});

		Scene scene = new Scene(grid, WIDTH, HEIGHT);
		scene.getStylesheets().add(
				GUI.class.getResource("style.css").toExternalForm());
		return scene;
	}

	private Scene createBookDetailsPage(Stage stage, Book book) {
		GridPane grid = createGrid();

		final VBox vBox = new VBox();
		vBox.setSpacing(10);
		// vBox.setPrefSize(300, 300);

		grid.add(new Text(book.getTitle()), 0, 0);
		grid.add(new Text("by " + book.getAuthor()), 0, 1);
		vBox.getChildren().add(new Text("ISBN : " + book.getISBN()));
		vBox.getChildren().add(new Text("Summary :\n" + book.getSummary()));
		if (book.isAvailable()) {
			vBox.getChildren().add(
					new Text("Available (" + book.getExemplary()
							+ " exemplary left)"));
		} else {
			vBox.getChildren().add(new Text("Not available"));
		}
		vBox.getChildren().add(
				new Text("Price : " + book.getPrice() + " "
						+ book.getCurrency()));

		grid.add(vBox, 0, 3);

		Button backButton = new Button("Back");
		grid.add(backButton, 0, 6);
		backButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Scene scene = createIndexPage(stage);
				showScene(stage, scene);
			}
		});
		if (book.isAvailable()) {
			Button buyButton = new Button("Buy");
			grid.add(buyButton, 1, 6);
			buyButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					Scene scene = createBankSigninPage(stage, book);
					showScene(stage, scene);
				}
			});
		} else {
			grid.add(new Text("Book not available"), 1, 6);
		}

		Scene scene = new Scene(grid, WIDTH, HEIGHT);
		scene.getStylesheets().add(
				GUI.class.getResource("style.css").toExternalForm());
		return scene;
	}
	
	private  void signupBankHandler(TextField nameLabel,TextField firstnameLabel,ComboBox<String> currencyCombobox,Text message,Stage stage,Book book) {
		String name = nameLabel.getText();
		String firstname = firstnameLabel.getText();
		String currency = (String)currencyCombobox.getValue();
		
		if (name.equals("") && firstname.equals("") && currency.equals("")){
			message.setFill(Color.FIREBRICK);
			message.setText("Please enter valid informations");
		} else {
			try {
				if(currency.equals("")) {
					currency = "EUR";
				}
				int clientId = client.createAccount(name, firstname, currency);
				client.setCurrentAccount(clientId);
				Scene scene = createCommandPage(stage, book);
				showScene(stage, scene);

			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private Scene createCommandPage(Stage stage, Book book) {
		GridPane grid = createGrid();

		Text scenetitle = new Text("Commande du livre : " + book.getTitle());
		scenetitle.setId("welcome-text");
		grid.add(scenetitle, 0, 0, 2, 1);

		Label idLabel = addLabelField(grid, "Account id :", 0, 1);
		Label nameLabel = addLabelField(grid, "Name :", 0, 2);
		Label firstnamenameLabel = addLabelField(grid, "Firstname :", 0, 3);
		Label currencyLabel = addLabelField(grid, "Account currency :", 0, 4);
		Label balanceLabel = addLabelField(grid, "Account balance :", 0, 5);
		Label labelBook = new Label(book.getTitle()+" : ");
		grid.add( labelBook, 0, 6);
		Text textPrice = new Text();
		try {
			if(!client.getAccountCurrency().equals(book.getCurrency())){
				double rateToBook = Client.getConvertRate( book.getCurrency(),client.getAccountCurrency());
				double valueConverted = book.getPrice() * rateToBook;
				
				textPrice.setText("Price : " + book.getPrice() + " " + book.getCurrency()+" / "+valueConverted+" "+client.getAccountCurrency());
			} else {
				textPrice.setText("Price : " + book.getPrice() + " " + book.getCurrency());
			}
		} catch (RemoteException | IllegalArgumentException | ServiceException e) {
			System.err.println("Gui.java : client.getConvertRate error");
			textPrice.setText("Price : " + book.getPrice() + " " + book.getCurrency());
		}
		grid.add(textPrice, 1, 6);
		try {
			updateBankDetail(idLabel, nameLabel, firstnamenameLabel,
					currencyLabel, balanceLabel);
		} catch (RemoteException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		TextField moneyTextField = addTextField(grid, "Deposit money :", 0, 7);
		ComboBox<String> currencyCombobox = addComboBoxCurrency(grid, "", 2, 7);
		Button btnDeposit = new Button("Deposit");
		grid.add(btnDeposit, 4, 7);
		Button btnConfirm = new Button("Confirm");
		Button btnCancel = new Button("Cancel");

		HBox hbBtn = new HBox(10);
		
		hbBtn.setAlignment(Pos.BOTTOM_LEFT);
		hbBtn.getChildren().addAll(btnCancel);
		grid.add(hbBtn, 0, 9);
		hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_LEFT);
		hbBtn.getChildren().addAll(btnConfirm);
		grid.add(hbBtn, 1, 9);

		final Text message = new Text();
		grid.add(message, 0, 8);

		btnConfirm.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					if (client.isAbleToBuyBook(book)) {
						try {
							client.buyBook(book);
						} catch (RemoteException | IllegalArgumentException | ServiceException e1) {
							message.setFill(Color.FIREBRICK);
							message.setText("You can't buy this book it seems");
						}

						Scene scene = createIndexPage(stage);
						showScene(stage, scene);
					} else {
						message.setFill(Color.FIREBRICK);
						message.setText("Deposit more money");
					}
				} catch (RemoteException | IllegalArgumentException
						| ServiceException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		btnCancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Scene scene = createIndexPage(stage);
				showScene(stage, scene);
			}
		});

		btnDeposit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					Double ammount = Double.parseDouble(moneyTextField
							.getText());
					try {
						client.deposit(ammount,(String)currencyCombobox.getValue());
						updateBankDetail(idLabel,nameLabel,firstnamenameLabel,currencyLabel,balanceLabel);

					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IllegalArgumentException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ServiceException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} catch (NumberFormatException e1) {
					message.setFill(Color.FIREBRICK);
					message.setText("value not a number");
				}
			}
		});

		Scene scene = new Scene(grid, WIDTH, HEIGHT);
		scene.getStylesheets().add(
				GUI.class.getResource("style.css").toExternalForm());
		return scene;
	}

	private void updateBankDetail(Label idLabel, Label nameLabel,
			Label firstnameLabel, Label currencyLabel, Label balanceLabel)
			throws RemoteException {
		idLabel.setText(client.getAccountId());
		nameLabel.setText(client.getAccountName());
		firstnameLabel.setText(client.getAccountFirstname());
		currencyLabel.setText(client.getAccountCurrency());
		balanceLabel.setText(client.getAccountBalance());
	}
}
