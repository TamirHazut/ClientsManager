import java.io.File;
import java.util.Optional;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class ClientsGUI extends BorderPane {
	private final static int NUM_OF_COLUMNS = 4;
	private final static int NUM_OF_CLICKS_TO_OPEN_NEW_CLIENT_WINDOW = 2;
	private final static int SEARCH_BAR_WIDTH = 500;
	private final static int NUMBER_COLUMN_WIDTH = 20;
	private final static double NUMBER_COLUMS_WIDTH_SUBSTRACTION = NUMBER_COLUMN_WIDTH*1.5;
	private final static int MAX_ID_LENGTH = 9;
	private final Insets panesInsets = new Insets(10);
	private final Insets buttonsInsets = new Insets(0, 0, 0, 5);

	private static enum SearchOptions {
		NAME, ID, PHONE
	}

	private Integer searchBy;

	private static ClientsGUI singletonInstance;
	private HBox searchPane;
	private RadioButton searchByName = new RadioButton("Name");
	private RadioButton searchByID = new RadioButton("ID");
	private RadioButton searchByPhone = new RadioButton("Phone number");
	private final ToggleGroup searchOptions = new ToggleGroup();
	private TextField searchTF = new TextField();

	private TableView<Client> clientsTable = new TableView<>();
	private FilteredList<Client> filteredClientsList;
	private SortedList<Client> sortedClientsList;
	private ObservableList<Client> clientsList = FXCollections.observableArrayList();

	private AddButton addButton;
	private DeleteButton deleteButton;

	private ContextMenu clientCM = new ContextMenu();

	private MenuBar clientsMenuBar = new MenuBar();

	public EventHandler<ActionEvent> ae = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent arg0) {
			((Command) arg0.getSource()).Execute();
		}
	};

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
		initMenuBar();
		initSearchBar();
		topPane.getChildren().addAll(clientsMenuBar, searchPane);
		this.setTop(topPane);
	}

	private void initMenuBar() {
		Menu fileMenu = new Menu("File");
		MenuItem newClientMenu = new MenuItem("New Client");
		MenuItem exitWindow = new MenuItem("Exit");
		fileMenu.getItems().addAll(newClientMenu, exitWindow);
		clientsMenuBar.getMenus().add(fileMenu);
		newClientMenu.setOnAction(e -> addButton.Execute());
		exitWindow.setOnAction(e -> Platform.exit());
	}

	private void initSearchBar() {
		VBox radioButtonsLayout = setRadioButtons();
		HBox radioButtonsBox = new HBox(radioButtonsLayout);
		HBox.setHgrow(radioButtonsBox, Priority.ALWAYS);

		searchTF.setPromptText("Search...");
		searchTF.setPrefWidth(SEARCH_BAR_WIDTH);
		HBox searchBarPane = new HBox(searchTF);
		searchBarPane.setAlignment(Pos.CENTER);
		HBox.setHgrow(searchBarPane, Priority.ALWAYS);

		searchPane = new HBox(radioButtonsBox, searchBarPane);
		searchPane.setPadding(panesInsets);
		setSearchListner();
	}

	private void setSearchListner() {
		searchTF.setOnKeyReleased(keyEvent -> {
			if (searchOptions.getSelectedToggle() == null) {
				return;
			}
			SearchOptions selectedSearchOption = SearchOptions.values()[searchBy];
			switch (selectedSearchOption) {
			case NAME:
				filteredClientsList.setPredicate(client -> (client.getFirstName() + " " + client.getLastName())
						.toLowerCase().contains(searchTF.getText().toLowerCase().trim()));
				break;
			case ID:
				filteredClientsList.setPredicate(client -> client.getID().toString().toLowerCase()
						.contains(searchTF.getText().toLowerCase().trim()));
				break;
			case PHONE:
				filteredClientsList.setPredicate(
						client -> client.getPhoneNumber().contains(searchTF.getText().toLowerCase().trim()));
				break;
			}
		});
	}

	private void initTableView() {
		setColumns();
		getDataBase();
		this.filteredClientsList = new FilteredList<Client>(this.clientsList, p -> true);
		this.sortedClientsList = new SortedList<>(this.filteredClientsList);
		this.sortedClientsList.comparatorProperty().bind(this.clientsTable.comparatorProperty());
		this.clientsTable.setItems(this.sortedClientsList);
		this.setCenter(clientsTable);
		BorderPane.setAlignment(clientsTable, Pos.CENTER);
		setTableViewListners();
	}

	private void getDataBase() {
		File file = new File(ClientsDB.FILE_PATH);
		if (file.exists()) {
			ClientsDB.loadDB(this.clientsList);
		} else {
			ClientsDB.createTable();
		}
	}

	private void initButtons() {
		addButton = new AddButton(this);
		deleteButton = new DeleteButton(this);
		HBox bottomPane = new HBox(addButton, deleteButton);
		HBox.setMargin(deleteButton, buttonsInsets);
		bottomPane.setAlignment(Pos.BOTTOM_RIGHT);
		this.setBottom(bottomPane);
		BorderPane.setMargin(bottomPane, panesInsets);
		addButton.setOnAction(ae);
		deleteButton.setOnAction(ae);
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
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText(null);
				alert.setContentText("Invalid ID!");
				alert.showAndWait();
				return null;
			}
			return id;
        }
        return null;
	}

	@SuppressWarnings("unchecked")
	private void setColumns() {
		TableColumn<Client, Client> numberCol = new TableColumn<>("#");
        numberCol.setMinWidth(NUMBER_COLUMN_WIDTH);
        numberCol.setCellValueFactory(new Callback<CellDataFeatures<Client, Client>, ObservableValue<Client>>() {
            @Override public ObservableValue<Client> call(CellDataFeatures<Client, Client> p) {
                return new ReadOnlyObjectWrapper<Client>(p.getValue());
            }
        });

        numberCol.setCellFactory(new Callback<TableColumn<Client, Client>, TableCell<Client, Client>>() {
            @Override public TableCell<Client, Client> call(TableColumn<Client, Client> param) {
                return new TableCell<Client, Client>() {
                    @Override protected void updateItem(Client item, boolean empty) {
                        super.updateItem(item, empty);

                        if (this.getTableRow() != null && item != null) {
                            setText(this.getTableRow().getIndex()+1+"");
                        } else {
                            setText("");
                        }
                    }
                };
            }
        });
        numberCol.setSortable(false);
		TableColumn<Client, String> firstNameColumn = setColumn("First Name");
		firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
		TableColumn<Client, String> lastNameColumn = setColumn("Last Name");
		lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
		TableColumn<Client, String> idColumn = setColumn("ID");
		idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
		TableColumn<Client, String> phoneColumn = setColumn("Phone Number");
		phoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
		this.clientsTable.getColumns().addAll(numberCol,idColumn, firstNameColumn, lastNameColumn, phoneColumn);
		this.clientsTable.getSortOrder().add(firstNameColumn);
	}

	private TableColumn<Client, String> setColumn(String header) {
		TableColumn<Client, String> column = new TableColumn<Client, String>(header);
		column.prefWidthProperty().bind(clientsTable.widthProperty().subtract(NUMBER_COLUMS_WIDTH_SUBSTRACTION).divide(NUM_OF_COLUMNS));
		return column;
	}

	private void setTableViewListners() {
		clientsTable.setRowFactory(list -> {
			TableRow<Client> client = new TableRow<>();
			client.setOnMouseClicked(e -> {
				if (!client.isEmpty()) {
					if (e.getClickCount() == NUM_OF_CLICKS_TO_OPEN_NEW_CLIENT_WINDOW) {
						editClient(client.getItem());
					}
					if (e.getButton() == MouseButton.SECONDARY) {
						setContextMenu(e);
					}
				}
			});
			return client;

		});
	}

	private void editClient(Client client) {
		new ClientGUI(client, false);
	}

	private void setContextMenu(MouseEvent mouseClick) {
		clientCM.getItems().clear();
		MenuItem showClientMI = new MenuItem("Show");
		MenuItem deleteClientMI = new MenuItem("Delete");
		clientCM.getItems().addAll(showClientMI, deleteClientMI);
		clientCM.show(clientsTable, mouseClick.getScreenX(), mouseClick.getScreenY());
		showClientMI.setOnAction(e -> editClient(clientsTable.getSelectionModel().getSelectedItem()));
		deleteClientMI.setOnAction(e -> deleteButton.Execute());
	}

	private VBox setRadioButtons() {
		searchByName.setToggleGroup(searchOptions);
		searchByName.setUserData(SearchOptions.NAME.ordinal());
		VBox.setMargin(searchByName, new Insets(0, 0, 5, 0));
		searchByID.setToggleGroup(searchOptions);
		searchByID.setUserData(SearchOptions.ID.ordinal());
		VBox.setMargin(searchByID, new Insets(0, 0, 5, 0));
		searchByPhone.setToggleGroup(searchOptions);
		searchByPhone.setUserData(SearchOptions.PHONE.ordinal());
		searchOptions.selectToggle(searchByName);
		searchBy = SearchOptions.NAME.ordinal();
		searchOptions.selectedToggleProperty().addListener(e -> {
			if (searchOptions.getSelectedToggle() != null) {
				this.searchBy = (Integer) searchOptions.getSelectedToggle().getUserData();
				resetSearch();
			}
		});
		VBox radioButtonsLayout = new VBox(searchByName, searchByID, searchByPhone);
		return radioButtonsLayout;
	}

	protected void resetSearch() {
		searchTF.setText(null);
		filteredClientsList.setPredicate(null);
	}

	protected TableView<Client> getClientsTable() {
		return clientsTable;
	}

	protected ObservableList<Client> getClientList() {
		return clientsList;
	}

}
