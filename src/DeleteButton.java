public class DeleteButton extends ClientsGUIButtons {

	protected DeleteButton(ClientsGUI gui) {
		super(gui);
		this.setText("Delete");
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