import java.time.LocalDate;
import java.time.Month;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

public class ClientsGUI extends VBox {
	private final static int NUM_OF_COLUMNS = 4;
	private final static int NUM_OF_CLICKS_TO_OPEN_NEW_CLIENT_WINDOW = 2;
	private TableView<Client> clientsTable = new TableView<>();
	private ObservableList<Client> clientList = FXCollections.observableArrayList();
	private static ClientsGUI singletonInstance;

	private ClientsGUI() {
		initTableView();
	}

	public static ClientsGUI getInstance() {
		if (singletonInstance == null) {
			singletonInstance = new ClientsGUI();
		}
		return singletonInstance;
	}

	private void initTableView() {
		setColumns();
		addClientsToList();
		this.clientsTable.setItems(this.clientList);
		this.getChildren().add(clientsTable);
		clientsTable.prefHeightProperty().bind(this.heightProperty());
		clientsTable.prefWidthProperty().bind(this.widthProperty());
		setTableViewListner();
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

	public void addClientsToList() {
//		Client a = new Client(firstName, lastName, id, phoneNumber, birthDay, gender, city, streetName, houseNumber, apartment, zipcode, maritalStatus)
		clientList.add(new Client("à", "a", 1, "050", LocalDate.of(2000, Month.AUGUST, 1), "Male", "A", "a", 1, 1, 111, "Single"));
		clientList.add(new Client("B", "b", 2, "051", LocalDate.of(2001, Month.SEPTEMBER, 2), "Female", "A", "a", 1, 1, 111, "Married"));
		clientList.add(new Client("C", "c", 3, "052", LocalDate.of(2002, Month.NOVEMBER, 3), "Male", "A", "a", 1, 1, 111, "Divorced"));
		clientList.add(new Client("æ", "d", 4, "053", LocalDate.of(2002, Month.DECEMBER, 4), "Female", "A", "a", 1, 1, 111, "Separated"));
	}

}
