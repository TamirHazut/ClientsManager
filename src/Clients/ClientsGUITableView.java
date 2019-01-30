package Clients;

import java.io.File;

import Client.Client;
import General.ClientsDB;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.input.MouseButton;
import javafx.util.Callback;

public class ClientsGUITableView extends TableView<Client> {
	private final static int NUM_OF_COLUMNS = 4;
	private final static int NUMBER_COLUMN_WIDTH = 20;
	private final static int NUM_OF_CLICKS_TO_OPEN_NEW_CLIENT_WINDOW = 2;
	private FilteredList<Client> filteredClientsList;
	private SortedList<Client> sortedClientsList;
	private ObservableList<Client> clientsList = FXCollections.observableArrayList();
	private ClientsGUI gui;

	public ClientsGUITableView(ClientsGUI gui) {
		this.gui = gui;
		init();
	}

	private void init() {
		setColumns();
		getDataBase();
		this.filteredClientsList = new FilteredList<Client>(this.clientsList, p -> true);
		this.sortedClientsList = new SortedList<>(this.filteredClientsList);
		this.sortedClientsList.comparatorProperty().bind(this.comparatorProperty());
		this.setItems(this.sortedClientsList);
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

	private void setTableViewListners() {
		this.setRowFactory(list -> {
			TableRow<Client> client = new TableRow<>();
			client.setOnMouseClicked(e -> {
				if (!client.isEmpty()) {
					if (e.getClickCount() == NUM_OF_CLICKS_TO_OPEN_NEW_CLIENT_WINDOW) {
						getGui().editClient(client.getItem());
					}
					if (e.getButton() == MouseButton.SECONDARY) {
						getGui().getClientCM().openMenu(e);
					}
				}
			});
			return client;

		});
	}

	@SuppressWarnings("unchecked")
	private void setColumns() {
		TableColumn<Client, Client> numberCol = new TableColumn<>("#");
		numberCol.setMinWidth(NUMBER_COLUMN_WIDTH);
		numberCol.setCellValueFactory(new Callback<>() {
			@Override
			public ObservableValue<Client> call(CellDataFeatures<Client, Client> p) {
				return new ReadOnlyObjectWrapper<Client>(p.getValue());
			}
		});
		numberCol.setCellFactory(new Callback<>() {
			@Override
			public TableCell<Client, Client> call(TableColumn<Client, Client> param) {
				return new TableCell<Client, Client>() {
					@Override
					protected void updateItem(Client item, boolean empty) {
						super.updateItem(item, empty);

						if (this.getTableRow() != null && item != null) {
							setText(this.getTableRow().getIndex() + 1 + "");
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
		this.getColumns().addAll(numberCol, idColumn, firstNameColumn, lastNameColumn, phoneColumn);
		this.getSortOrder().add(firstNameColumn);
	}

	private TableColumn<Client, String> setColumn(String header) {
		TableColumn<Client, String> column = new TableColumn<Client, String>(header);
		column.prefWidthProperty()
				.bind(this.widthProperty().subtract(NUMBER_COLUMN_WIDTH).divide(NUM_OF_COLUMNS));
		return column;
	}

	protected ClientsGUI getGui() {
		return gui;
	}

	protected FilteredList<Client> getFilteredClientsList() {
		return filteredClientsList;
	}

	protected void setFilteredClientsList(FilteredList<Client> filteredClientsList) {
		this.filteredClientsList = filteredClientsList;
	}

	protected SortedList<Client> getSortedClientsList() {
		return sortedClientsList;
	}

	protected ObservableList<Client> getClientsList() {
		return clientsList;
	}

}
