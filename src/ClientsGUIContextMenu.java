import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ClientsGUIContextMenu extends ContextMenu {
	private final static int PREF_ICON_SIZE = 22;
	private final static String SHOW_CLIENT_ICON = "images/Info.png";
	private final static String DELETE_CLIENT_ICON = "images/Delete.png";
	private ImageView showIcon = new ImageView(
			new Image(this.getClass().getClassLoader().getResource(SHOW_CLIENT_ICON).toString()));
	private ImageView deleteIcon = new ImageView(
			new Image(this.getClass().getClassLoader().getResource(DELETE_CLIENT_ICON).toString()));
	private MenuItem showClientMI;
	private MenuItem deleteClientMI;
	private ClientsGUI gui;

	public ClientsGUIContextMenu(ClientsGUI gui) {
		this.gui = gui;
		init();
	}

	private void init() {
		showClientMI = initMenuItem(showIcon, "Show Client");		
		deleteClientMI = initMenuItem(deleteIcon, "Delete Client");	
	}
	private MenuItem initMenuItem(ImageView image,String text) {
		MenuItem item = new MenuItem();
		image.setFitHeight(PREF_ICON_SIZE);
		image.setFitWidth(PREF_ICON_SIZE);
		item.setGraphic(image);
		item.setText(text);
		return item;
	}

	protected void openMenu(MouseEvent mouseClick) {
		this.getItems().clear();
		this.getItems().addAll(showClientMI, deleteClientMI);
		this.show(gui.getClientsTable(), mouseClick.getScreenX(), mouseClick.getScreenY());
		showClientMI.setOnAction(e -> gui.editClient(gui.getClientsTable().getSelectionModel().getSelectedItem()));
		deleteClientMI.setOnAction(e -> gui.getButtonsPane().getDeleteButton().Execute());
	}

}
