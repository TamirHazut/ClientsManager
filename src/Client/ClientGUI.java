package Client;
import java.io.File;

import General.Command;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ClientGUI extends BorderPane {
	private final static int MIN_SCEONDERY_STAGE_HEIGTH = 530;
	private final static int MIN_SCEONDERY_STAGE_WIDTH = 650;

	private Client currentClient;

	private boolean newClient;

	private ProfilePicture profilePicture;

	private final Insets otherInsets = new Insets(10);

	private ClientGUIDetailsLayout clientDetails;

	private EditSaveButton editSaveButton;

	public EventHandler<ActionEvent> ae = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent arg0) {
			((Command) arg0.getSource()).Execute();
		}
	};

	public ClientGUI(Client client, boolean newClient) {
		setNewClient(newClient);
		setCurrentClient(client);
		if (newClient) {
			createFolder();
		}
		initClientWindow();
	}

	public ClientGUI(Client client) {
		this(client, true);
	}

	private void initClientWindow() {
		setClientWindow();
		Stage seconderyStage = new Stage();
		seconderyStage.setTitle("Client Window");
		seconderyStage.setScene(new Scene(this));
		seconderyStage.setResizable(false);
		seconderyStage.setMinHeight(MIN_SCEONDERY_STAGE_HEIGTH);
		seconderyStage.setMinWidth(MIN_SCEONDERY_STAGE_WIDTH);
		seconderyStage.showAndWait();
	}

	private void setClientWindow() {
		profilePicture = new ProfilePicture(getCurrentClient(),new File(getCurrentClient().getProfilePicture()));
		setPictureListner();

		setClientDetails(new ClientGUIDetailsLayout(getCurrentClient(), isANewClient()));
		getClientDetails().changeFieldsStatus();

		setUpdateButton();
		this.setCenter(clientDetails);
		BorderPane.setAlignment(clientDetails, Pos.CENTER);
		BorderPane.setMargin(profilePicture, otherInsets);
		this.setLeft(profilePicture);
		BorderPane.setAlignment(profilePicture, Pos.TOP_LEFT);
	}

	private void setPictureListner() {
		profilePicture.getProfilePicture().setOnMouseClicked(e -> {
			if (!getClientDetails().disabledEditTextField()) {
				profilePicture.browsePicture();
			}
		});
	}

	private void setUpdateButton() {
		editSaveButton = new EditSaveButton(this, isANewClient());
		BorderPane.setMargin(editSaveButton, otherInsets);
		this.setBottom(editSaveButton);
		BorderPane.setAlignment(editSaveButton, Pos.BOTTOM_RIGHT);
		editSaveButton.setOnAction(ae);
	}


	public boolean isANewClient() {
		return newClient;
	}

	public void setNewClient(boolean newClient) {
		this.newClient = newClient;
	}
	private void createFolder() {
		File file = new File(getCurrentClient().getClientDir());
		if (!file.exists()) {
			file.mkdirs();
		}
	}
	public Client getCurrentClient() {
		return currentClient;
	}

	public void setCurrentClient(Client currentClient) {
		this.currentClient = currentClient;
	}

	public ClientGUIDetailsLayout getClientDetails() {
		return clientDetails;
	}

	public void setClientDetails(ClientGUIDetailsLayout clientDetails) {
		this.clientDetails = clientDetails;
	}
}
