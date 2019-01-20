public class AddButton extends ClientsGUIButtons {
	public AddButton(ClientsGUI gui) {
		super(gui);
		this.setText("Add");
	}

	@Override
	public void Execute() {
		Integer id = gui.requestID();
		if (id != null) {
			this.client = new Client(id);
			if (!newClient()) {
				gui.getClientList().add(this.client);
				gui.resetSearch();
			}
		}
	}

	private boolean newClient() {
		ClientGUI gui = new ClientGUI(this.client, true);
		return gui.isANewClient();
	}

}