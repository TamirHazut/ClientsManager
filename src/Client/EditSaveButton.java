package Client;

import General.ClientsDB;
import General.CommandButton;
import General.InvalidDataException;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;

import java.io.File;

public class EditSaveButton extends CommandButton {
	private final static String EDIT_BUTTON_ICON = "images/Edit.png";
	private final static String SAVE_BUTTON_ICON = "images/Save.png";

	private boolean newClient;
	private ClientGUI gui;
	private boolean editDisplay;

	public EditSaveButton(ClientGUI gui, boolean newClient) {
		super();
		this.gui = gui;
		this.newClient = newClient;
		setEditDisplay(newClient ? false : true);
		setBackGround();
	}

	@Override
	public void Execute() {
		if (this.newClient) {
			if (saveNewClient()) {
				changeDisplay();
			}
		} else {
			if (!isEditDisplay()) {
				updateClient();
			} else {
				changeDisplay();
			}
		}

	}

	private void setBackGround() {
		this.setBackGroundImage(new Image(this.getClass().getClassLoader()
				.getResource(isEditDisplay() ? EDIT_BUTTON_ICON : SAVE_BUTTON_ICON).toString()));
	}


	private boolean saveNewClient() {
		if (gui.getClientDetails().saveNewState()) {
			if (ClientsDB.writeNewClient(gui.getCurrentClient())) {
				this.newClient = false;
				gui.setNewClient(false);
				savedSuccessfully("New Client Added Successfully");
				gui.getClientDetails().setDataHasChanged(false);
				return true;
			}
		}
		return false;
	}

	private boolean updateClient() {
		if (!gui.getClientDetails().disabledEditTextField()) {
			if (gui.getClientDetails().saveNewState()) {
				if (gui.getClientDetails().isDataChanged() && ClientsDB.updateClient(gui.getCurrentClient())) {
					savedSuccessfully("Client Modified Successfully");
					gui.getClientDetails().setDataHasChanged(false);
				}
				changeDisplay();
				return true;
			}
		}
		return false;
	}

	private void changeDisplay() {
		setEditDisplay(!isEditDisplay());
		gui.getClientDetails().disableEditTextField(!gui.getClientDetails().disabledEditTextField());
		gui.getClientDetails().changeFieldsStatus();
		setBackGround();
	}

	private void savedSuccessfully(String message) {
		InvalidDataException.showAlert(AlertType.CONFIRMATION, message);
	}

	protected boolean isEditDisplay() {
		return editDisplay;
	}

	protected void setEditDisplay(boolean editDisplay) {
		this.editDisplay = editDisplay;
	}

}