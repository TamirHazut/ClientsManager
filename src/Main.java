import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	private final static int MIN_PRIMARY_STAGE_HEIGTH = 500;
	private final static int MIN_PRIMARY_STAGE_WIDTH = 700;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		ClientsGUI mainWindow = ClientsGUI.getInstance();
		mainWindow.prefHeightProperty().bind(primaryStage.heightProperty());
		mainWindow.prefWidthProperty().bind(primaryStage.widthProperty());
		Scene mainScene = new Scene(mainWindow);
		primaryStage.setTitle("Clients List");
		primaryStage.setScene(mainScene);
		primaryStage.setMinHeight(MIN_PRIMARY_STAGE_HEIGTH);
		primaryStage.setMinWidth(MIN_PRIMARY_STAGE_WIDTH);
		primaryStage.show();
	}

}
