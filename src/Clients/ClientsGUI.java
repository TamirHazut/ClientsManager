package Clients;

import java.util.Optional;

import Client.Client;
import Client.ClientGUI;
import General.InvalidDataException;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ClientsGUI extends BorderPane {
	public final static int PREF_ICON_SIZE = 40;
	private final static int MAX_ID_LENGTH = 9;
	private final Insets panesInsets = new Insets(10);

	private static ClientsGUI singletonInstance;

	private ClientsGUIMenuBar clientsMenuBar;

	private ClientsGUISearch searchPane;

	private ClientsGUITableView clientsTable;

	private ClientsGUIContextMenu clientCM = new ClientsGUIContextMenu(this);

	private ClientsGUIButtonsPane buttonsPane;

	private ClientsGUI() {
		initTopPane();
		initTableView();
		initButtons();
	}

	public static ClientsGUI getInstance() {
		if (singletonInstance == null) {
			singletonInstance = new ClientsGUI();
		}
		return singletonInstance;
	}

	private void initTopPane() {
		VBox topPane = new VBox();
		clientsMenuBar = new ClientsGUIMenuBar(this);
		initSearchBar();
		topPane.getChildren().addAll(clientsMenuBar, searchPane);
		this.setTop(topPane);
	}

	private void initSearchBar() {
		searchPane = new ClientsGUISearch(this);
		searchPane.setPadding(panesInsets);
	}

	private void initTableView() {
		this.clientsTable = new ClientsGUITableView(this);
		this.setCenter(clientsTable);
		BorderPane.setAlignment(clientsTable, Pos.CENTER);
	}

	private void initButtons() {
		buttonsPane = new ClientsGUIButtonsPane(this);
		buttonsPane.setAlignment(Pos.BOTTOM_RIGHT);
		this.setBottom(buttonsPane);
		BorderPane.setMargin(buttonsPane, panesInsets);
	}

	protected Integer requestID() {
		TextInputDialog dialog = new TextInputDialog("Ex: 123456789");
		dialog.setTitle("Client ID");
		dialog.setHeaderText(null);
		dialog.setContentText("Please enter client id:");
		Integer id = 0;
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			try {
				String tempID = dialog.getEditor().getText();
				if (tempID.length() > MAX_ID_LENGTH || tempID.isEmpty()) {
					throw new IllegalArgumentException();
				}
				id = Integer.parseInt(tempID);
			} catch (IllegalArgumentException ex) {
				InvalidDataException.showAlert(AlertType.ERROR, "Invalid ID!");
				return null;
			}
			return id;
		}
		return null;
	}

	protected void editClient(Client client) {
		new ClientGUI(client, false);
	}

	protected ClientsGUITableView getClientsTable() {
		return clientsTable;
	}

	protected ObservableList<Client> getClientList() {
		return getClientsTable().getClientsList();
	}

	protected FilteredList<Client> getFilteredClientsList() {
		return getClientsTable().getFilteredClientsList();
	}

	protected void setFilteredClientsList(FilteredList<Client> filteredClientsList) {
		getClientsTable().setFilteredClientsList(filteredClientsList);
		;
	}

	protected ClientsGUISearch getSearchPane() {
		return searchPane;
	}

	protected ClientsGUIButtonsPane getButtonsPane() {
		return buttonsPane;
	}

	protected ClientsGUIContextMenu getClientCM() {
		return clientCM;
	}
}
