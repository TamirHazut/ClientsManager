import javafx.scene.image.Image;

public class DeleteButton extends ClientsGUIButtons {
	private final static String DELETE_BUTTON_ICON = "images/Delete.png";

	protected DeleteButton(ClientsGUI gui) {
		super(gui);
		this.setBackGroundImage(new Image(this.getClass().getClassLoader().getResource(DELETE_BUTTON_ICON).toString()));
	}

	@Override
	public void Execute() {
		Client temp = gui.getClientsTable().getSelectionModel().getSelectedItem();
		if (temp == null) {
			return;
		}
		setClientToDelete(temp);
		if (ClientsDB.deleteClient(client)) {
			gui.getClientList().remove(client);
		}
	}

	protected void setClientToDelete(Client client) {
		this.client = client;
	}

}