import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

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
		if (this.newClient) {
			if (saveNewClient()) {
				changeDisplay();
			}
		} else {
			if (this.getText().equals("Save")) {
				updateClient();
			} else {
				changeDisplay();
			}
		}

	}

	private boolean saveNewClient() {
		if (gui.saveNewState()) {
			if (ClientsDB.writeNewClient(gui.getCurrentClient())) {
				this.newClient = false;
				gui.setNewClient(false);
				savedSuccessfully("New Client Added Successfully");
				gui.setDataHasChanged(false);
				return true;
			}
		}
		return false;
	}

	private boolean updateClient() {
		if (!gui.disabledEditTextField()) {
			if (gui.saveNewState()) {
				if (gui.isDataChanged() && ClientsDB.updateClient(gui.getCurrentClient())) {
					savedSuccessfully("Client Modified Successfully");
					gui.setDataHasChanged(false);
				}
				changeDisplay();
				return true;
			}
		}
		return false;
	}

	private void changeDisplay() {
		gui.disableEditTextField(!gui.disabledEditTextField());
		gui.changeFieldsStatus();
		this.setText(gui.disabledEditTextField() ? "Edit" : "Save");
	}

	private void savedSuccessfully(String message) {
		Alert alert = new Alert(AlertType.CONFIRMATION, message, ButtonType.OK);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

}