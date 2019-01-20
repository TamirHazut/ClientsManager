public class EditSaveButton extends CommandButton {
	private boolean newClient;
	private ClientGUI gui;

	public EditSaveButton(ClientGUI gui, boolean newClient) {
		super();
		this.gui = gui;
		this.newClient = newClient;
		this.setText(newClient ? "Save" : "Edit");
	}

	@Override
	public void Execute() {
		if (!gui.saveNewState()) {
			return;
		}
		if (this.newClient) {
			if (ClientsDB.writeNewClient(gui.getCurrentClient())) {
				this.newClient = false;
				gui.setNewClient(false);
			}
		}
		else {
			ClientsDB.updateClient(gui.getCurrentClient());
		}
		gui.disableEditTextField(!gui.disabledEditTextField());
		gui.changeFieldsStatus();
		this.setText(gui.disabledEditTextField() ? "Edit" : "Save");
	}
}