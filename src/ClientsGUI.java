import java.time.LocalDate;
import java.time.Month;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ClientsGUI extends BorderPane {
	private final static int NUM_OF_COLUMNS = 4;
	private final static int NUM_OF_CLICKS_TO_OPEN_NEW_CLIENT_WINDOW = 2;
	private final static int SEARCH_BAR_WIDTH = 500;
	private final static int MAX_ID_LENGTH = 9;
	private static ClientsGUI singletonInstance;
	private final Insets panesInsets = new Insets(10);

	private TableView<Client> clientsTable = new TableView<>();
	private ObservableList<Client> clientList = FXCollections.observableArrayList();

	private ClientsGUI() {
		initSearchBar();
		initTableView();
		initButtons();
	}

	public static ClientsGUI getInstance() {
		if (singletonInstance == null) {
			singletonInstance = new ClientsGUI();
		}
		return singletonInstance;
	}

	private void initSearchBar() {
		VBox radioButtonsLayout = setRadioButtons();
		HBox radioButtonsBox = new HBox(radioButtonsLayout);
		HBox.setHgrow(radioButtonsBox, Priority.ALWAYS);

		TextField searchTF = new TextField();
		searchTF.setPromptText("Search...");
		searchTF.setMinWidth(SEARCH_BAR_WIDTH);
		HBox searchBarPane = new HBox(searchTF);
		searchBarPane.setAlignment(Pos.CENTER_RIGHT);
		HBox.setHgrow(searchBarPane, Priority.ALWAYS);

		HBox topPane = new HBox(radioButtonsBox, searchBarPane);
		topPane.setPadding(panesInsets);
		this.setTop(topPane);
	}

	private void initTableView() {
		setColumns();
		addClientsToList();
		this.clientsTable.setItems(this.clientList);
		this.setCenter(clientsTable);
		BorderPane.setAlignment(clientsTable, Pos.CENTER);
		setTableViewListner();
	}

	private void initButtons() {
		Button addButton = new Button("Add");
		Button deleteButton = new Button("Delete");
		HBox bottomPane = new HBox(addButton, deleteButton);
		HBox.setMargin(deleteButton, new Insets(0, 0, 0, 5));
		bottomPane.setAlignment(Pos.BOTTOM_RIGHT);
		this.setBottom(bottomPane);
		BorderPane.setMargin(bottomPane, panesInsets);
		addButton.setOnAction(e -> {
			Integer id = requestID();
			new ClientGUI(new Client(id));
		});
	}

	private Integer requestID() {
		boolean validID = false;
		TextInputDialog dialog = new TextInputDialog("Ex: 123456789");
		dialog.setTitle("Client ID");
		dialog.setHeaderText(null);
		dialog.setContentText("Please enter client id:");
		Integer id = 0;
		while (!validID) {
			dialog.showAndWait();
			try {
				String tempID = dialog.getEditor().getText();
				if (tempID.length() > MAX_ID_LENGTH) {
					throw new IllegalArgumentException();
				}
				id = Integer.parseInt(tempID);
				validID = true;
			} catch (IllegalArgumentException ex) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText(null);
				alert.setContentText("Invalid ID!");
				alert.showAndWait();
			}
		}
		return id;
	}

	@SuppressWarnings("unchecked")
	public void setColumns() {
		TableColumn<Client, String> firstNameColumn = setColumn("First Name");
		firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
		TableColumn<Client, String> lastNameColumn = setColumn("Last Name");
		lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
		TableColumn<Client, String> idColumn = setColumn("ID");
		idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
		TableColumn<Client, String> phoneColumn = setColumn("Phone Number");
		phoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
		this.clientsTable.getColumns().addAll(idColumn, firstNameColumn, lastNameColumn, phoneColumn);
	}

	public TableColumn<Client, String> setColumn(String header) {
		TableColumn<Client, String> column = new TableColumn<Client, String>(header);
		column.prefWidthProperty().bind(clientsTable.widthProperty().divide(NUM_OF_COLUMNS));
		return column;
	}

	public void setTableViewListner() {
		clientsTable.setRowFactory(list -> {
			TableRow<Client> client = new TableRow<>();
			client.setOnMouseClicked(e -> {
				if ((e.getClickCount() == NUM_OF_CLICKS_TO_OPEN_NEW_CLIENT_WINDOW) && (!client.isEmpty())) {
					Client currentClient = client.getItem();
					new ClientGUI(currentClient);
				}
			});
			return client;
		});
	}

	private VBox setRadioButtons() {
		final ToggleGroup searchOptions = new ToggleGroup();
		RadioButton searchByName = new RadioButton("Name");
		searchByName.setToggleGroup(searchOptions);
		VBox.setMargin(searchByName, new Insets(0, 0, 5, 0));
		RadioButton searchByID = new RadioButton("ID");
		searchByID.setToggleGroup(searchOptions);
		VBox.setMargin(searchByID, new Insets(0, 0, 5, 0));
		RadioButton searchByPhone = new RadioButton("Phone number");
		searchByPhone.setToggleGroup(searchOptions);
		VBox radioButtonsLayout = new VBox(searchByName, searchByID, searchByPhone);
		return radioButtonsLayout;
	}

	public void addClientsToList() {
		clientList.add(new Client("à", "a", 1, "050", "A@a", LocalDate.of(2000, Month.AUGUST, 1), "Male", "A", "a", 1,
				1, 111, "Single"));
		clientList.add(new Client("B", "b", 2, "051", "B@a", LocalDate.of(2001, Month.SEPTEMBER, 2), "Female", "A", "a",
				1, 1, 111, "Married"));
		clientList.add(new Client("C", "c", 3, "052", "C@a", LocalDate.of(2002, Month.NOVEMBER, 3), "Male", "A", "a", 1,
				1, 111, "Divorced"));
		clientList.add(new Client("æ", "d", 4, "053", "D@a", LocalDate.of(2002, Month.DECEMBER, 4), "Female", "A", "a",
				1, 1, 111, "Separated"));
	}

}
