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
		gui.saveNewState();
		if (this.newClient) {
			if (ClientsDB.writeNewClient(gui.getCurrentClient())) {
				this.newClient = false;
				gui.setNewClient(false);
			}
		}
		gui.disableEditTextField(!gui.isDisabledEditTextField());
		gui.changeFieldsStatus();
		this.setText(gui.isDisabledEditTextField() ? "Edit" : "Save");
	}
}