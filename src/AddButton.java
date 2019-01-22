import javafx.scene.image.Image;

public class AddButton extends ClientsGUIButtons {
	private final static String ADD_BUTTON_ICON = "images/Add.png";


	public AddButton(ClientsGUI gui) {
		super(gui);
		this.setBackGroundImage(new Image(this.getClass().getClassLoader().getResource(ADD_BUTTON_ICON).toString()));
	}

	@Override
	public void Execute() {
		Integer id = gui.requestID();
		if (id != null) {
			this.client = new Client(id);
			if (!newClient()) {
				gui.getClientList().add(this.client);
				gui.getSearchPane().resetSearch();
			}
		}
	}

	private boolean newClient() {
		ClientGUI gui = new ClientGUI(this.client, true);
		return gui.isANewClient();
	}


}