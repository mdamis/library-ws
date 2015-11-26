import static javafx.geometry.HPos.RIGHT;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUI extends Application {
	private final static int WIDTH = 600;
	private final static int HEIGHT = 400;

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("MLV Library");
		Scene scene = createSignInPage(primaryStage);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private Scene createSignInPage(Stage primaryStage) {
		GridPane grid = createGrid();

		Text scenetitle = new Text("Welcome to MLVLib");
		scenetitle.setId("welcome-text");
		grid.add(scenetitle, 0, 0, 2, 1);

		Label userName = new Label("User Name:");
		grid.add(userName, 0, 1);
		TextField userTextField = new TextField();
		grid.add(userTextField, 1, 1);

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
				String user = userTextField.getText();
				System.out.println("user: "+user);
				if (user.equals("")) {
					actiontarget.setFill(Color.FIREBRICK);
					actiontarget.setText("Enter a valid user name");
				} else {
					Scene scene = createIndexPage(user, primaryStage);
					primaryStage.setScene(scene);
					primaryStage.show();
				}
			}
		});

		Scene scene = new Scene(grid, WIDTH, HEIGHT);
		scene.getStylesheets().add(
				GUI.class.getResource("style.css").toExternalForm());
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

	private Scene createIndexPage(String user, Stage primaryStage) {
		GridPane grid = createGrid();

		Text sceneTitle = new Text("Connected as " + user);
		sceneTitle.setId("user-name");
		grid.add(sceneTitle, 0, 0);

		Button button = new Button("Quit");
		grid.add(button, 5, 5);
		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				primaryStage.close();
			}
		});
		
		Scene scene = new Scene(grid, WIDTH, HEIGHT);
		scene.getStylesheets().add(GUI.class.getResource("style.css").toExternalForm());
		return scene;
	}

}
