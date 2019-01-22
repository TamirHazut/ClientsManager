import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ClientsGUIMenuBar extends MenuBar {
	private final static int PREF_ICON_SIZE = 22;
	private final static String NEW_CLIENT_ICON = "images/Add.png";
	private final static String EXIT_ICON = "images/Exit.png";
	private ImageView addIcon = new ImageView(
			new Image(this.getClass().getClassLoader().getResource(NEW_CLIENT_ICON).toString()));
	private ImageView exitIcon = new ImageView(
			new Image(this.getClass().getClassLoader().getResource(EXIT_ICON).toString()));
	private ClientsGUI gui;

	public ClientsGUIMenuBar(ClientsGUI gui) {
		this.gui = gui;
		init();
	}

	private void init() {
		Menu fileMenu = new Menu("File");
		MenuItem newClientMenu = setMenu(addIcon, "New Client");
		MenuItem exitWindow = setMenu(exitIcon, "Exit");
		fileMenu.getItems().addAll(newClientMenu, exitWindow);
		this.getMenus().add(fileMenu);
		newClientMenu.setOnAction(e -> gui.getButtonsPane().getAddButton().Execute());
		exitWindow.setOnAction(e -> Platform.exit());
	}

	private MenuItem setMenu(ImageView icon, String text) {
		MenuItem item = new MenuItem();
		icon.setFitHeight(PREF_ICON_SIZE);
		icon.setFitWidth(PREF_ICON_SIZE);
		item.setGraphic(icon);
		item.setText(text);
		return item;
	}

}
