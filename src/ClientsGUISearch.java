import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ClientsGUISearch extends HBox {
	private final static int SEARCH_BAR_WIDTH = 500;

	private static enum SearchOptions {
		NAME, ID, PHONE
	}

	private RadioButton searchByName = new RadioButton("Name");
	private RadioButton searchByID = new RadioButton("ID");
	private RadioButton searchByPhone = new RadioButton("Phone number");
	private final ToggleGroup searchOptions = new ToggleGroup();
	private TextField searchTF = new TextField();
	private Integer searchBy;
	private ClientsGUI gui;

	public ClientsGUISearch(ClientsGUI gui) {
		this.gui = gui;
		init();
	}

	private void init() {
		VBox radioButtonsLayout = setRadioButtons();
		HBox radioButtonsBox = new HBox(radioButtonsLayout);
		HBox.setHgrow(radioButtonsBox, Priority.ALWAYS);

		searchTF.setPromptText("Search...");
		searchTF.setPrefWidth(SEARCH_BAR_WIDTH);
		HBox searchBarPane = new HBox(searchTF);
		searchBarPane.setAlignment(Pos.CENTER);
		HBox.setHgrow(searchBarPane, Priority.ALWAYS);

		this.getChildren().addAll(radioButtonsBox, searchBarPane);
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
				getGui().getFilteredClientsList().setPredicate(client -> (client.getFirstName() + " " + client.getLastName())
						.toLowerCase().contains(searchTF.getText().toLowerCase().trim()));
				break;
			case ID:
				getGui().getFilteredClientsList().setPredicate(client -> client.getID().toString().toLowerCase()
						.contains(searchTF.getText().toLowerCase().trim()));
				break;
			case PHONE:
				getGui().getFilteredClientsList().setPredicate(
						client -> client.getPhoneNumber().contains(searchTF.getText().toLowerCase().trim()));
				break;
			}
		});
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
		getGui().getFilteredClientsList().setPredicate(null);
	}

	protected ClientsGUI getGui() {
		return gui;
	}

}
