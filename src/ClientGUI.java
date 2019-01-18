import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class ClientGUI extends BorderPane {
	private final static int MIN_SCEONDERY_STAGE_HEIGTH = 530;
	private final static int MIN_SCEONDERY_STAGE_WIDTH = 650;
	private final static int PICTURE_FRAME_WIDTH = 200;
	private final static int PICTURE_FRAME_HIEGHT = 400;
	private final static int YEAR_TEXT_FIELD_SIZE = 50;
	private final static int AGE_TEXT_FIELD_SIZE = 50;
	private final static int NUM_OF_DAYS_A_MONTH = 31;

	private final static String[] GENDERS_LIST = { "Male", "Female" };
	private final static String[] MARITAL_STATUS_LIST = { "Single", "Married", "Separated", "Divorced", "Widowed" };

	private Client currentClient;
	private boolean disableEditTextField = true;

	private Shape pictureFrame = new Rectangle(PICTURE_FRAME_WIDTH, PICTURE_FRAME_HIEGHT);

	private VBox centerPane = new VBox();

	private GridPane basicClientDetails = new GridPane();
	private TextField firstNameTF;
	private TextField lastNameTF;
	private TextField idTF;
	private TextField phoneTF;
	private ComboBox<String> genderCB;

	private GridPane extendedClientDetails = new GridPane();
	private ComboBox<String> maritalStatusCB;

	private TextField cityTF;
	private TextField streetNameTF;
	private TextField houseNumberTF;
	private TextField apartmentTF;
	private TextField zipcodeTF;

	private HBox birthDayLayout = new HBox();
	private TextField yearOfBirthTF;
	private TextField ageTF;
	private ComboBox<Integer> dayOfBirthCB;
	private ComboBox<Month> monthOfBirthCB;

	public ClientGUI(Client client) {
		setCurrentClient(client);
		initClientWindow();
	}

	private void initClientWindow() {
		setBasicClientWindowLayout();
		setExtendedClientWindowLayout();
		changeFieldsStatus();

		Stage seconderyStage = new Stage();
		seconderyStage.setTitle("Client Window");
		seconderyStage.setScene(new Scene(this));
		seconderyStage.setResizable(false);
		seconderyStage.setMinHeight(MIN_SCEONDERY_STAGE_HEIGTH);
		seconderyStage.setMinWidth(MIN_SCEONDERY_STAGE_WIDTH);
		seconderyStage.show();
	}

	private void saveNewState() {
		if (!firstNameTF.getText().isEmpty() && !(firstNameTF.getText().equals(getCurrentClient().getFirstName()))) {
			getCurrentClient().setFirstName(firstNameTF.getText());
		}
		if (!lastNameTF.getText().isEmpty() && !(lastNameTF.getText().equals(getCurrentClient().getLastName()))) {
			getCurrentClient().setLastName(lastNameTF.getText());
		}
		if (!phoneTF.getText().isEmpty() && !(phoneTF.getText().equals(getCurrentClient().getPhoneNumber()))) {
			getCurrentClient().setPhoneNumber(phoneTF.getText());
		}
		checkForValidDate();
		if (!(maritalStatusCB.getValue().equals(getCurrentClient().getMaritalStatus()))) {
			getCurrentClient().setMaritalStatus(maritalStatusCB.getValue());
		}
		saveStreetState();
	}

	private void saveStreetState() {
		Integer houseNumber = getCurrentClient().getHouseNumber();
		Integer apartment = getCurrentClient().getApartment();
		Integer zipcode = getCurrentClient().getZipcode();
		try {
			if (!cityTF.getText().isEmpty() && !(cityTF.getText().equals(getCurrentClient().getCity()))) {
				getCurrentClient().setCity(cityTF.getText());
			}
			if (!streetNameTF.getText().isEmpty()
					&& !(streetNameTF.getText().equals(getCurrentClient().getStreetName()))) {
				getCurrentClient().setStreetName(streetNameTF.getText());
			}
			if (!houseNumberTF.getText().isEmpty()) {
				houseNumber = Integer.parseInt(houseNumberTF.getText());
				if (houseNumber != getCurrentClient().getHouseNumber()) {
					getCurrentClient().setHouseNumber(houseNumber);

				}
			}
			if (!apartmentTF.getText().isEmpty()) {
				apartment = Integer.parseInt(apartmentTF.getText());
				if (apartment != getCurrentClient().getApartment()) {
					getCurrentClient().setApartment(apartment);

				}
			}
			if (!zipcodeTF.getText().isEmpty()) {
				zipcode = Integer.parseInt(zipcodeTF.getText());
				if (zipcode != getCurrentClient().getZipcode()) {
					getCurrentClient().setZipcode(zipcode);

				}
			}
		} catch (NumberFormatException ex) {
		}
	}

	private void checkForValidDate() {
		int day = dayOfBirthCB.getValue();
		Month month = monthOfBirthCB.getValue();
		int year = getCurrentClient().getBirthDay().getYear();
		LocalDate tempDate;
		try {
			if (!yearOfBirthTF.getText().isEmpty()) {
				year = Integer.parseInt(yearOfBirthTF.getText());
			}
		} catch (NumberFormatException ex) {
		}
		try {
			tempDate = LocalDate.of(year, month, day);
		} catch (DateTimeException ex) {
			tempDate = null;
		}
		if ((tempDate != null) && (tempDate != getCurrentClient().getBirthDay())) {
			getCurrentClient().setBirthDay(tempDate);
			ageTF.setText(getCurrentClient().getAge().toString());
		}
	}

	public void changeFieldsStatus() {
		firstNameTF.setDisable(isDisableEditTextField());
		lastNameTF.setDisable(isDisableEditTextField());
		genderCB.setDisable(isDisableEditTextField());
		phoneTF.setDisable(isDisableEditTextField());
		yearOfBirthTF.setDisable(isDisableEditTextField());
		dayOfBirthCB.setDisable(isDisableEditTextField());
		monthOfBirthCB.setDisable(isDisableEditTextField());
		cityTF.setDisable(isDisableEditTextField());
		streetNameTF.setDisable(isDisableEditTextField());
		houseNumberTF.setDisable(isDisableEditTextField());
		apartmentTF.setDisable(isDisableEditTextField());
		zipcodeTF.setDisable(isDisableEditTextField());
		maritalStatusCB.setDisable(isDisableEditTextField());
	}

	/* Setters And Getters */
	public void setBasicClientWindowLayout() {
		HBox picturePane = new HBox(pictureFrame);
		firstNameTF = setFieldLayout("First Name:", getCurrentClient().getFirstName(), basicClientDetails, 0, 0);
		lastNameTF = setFieldLayout("Last Name:", getCurrentClient().getLastName(), basicClientDetails, 0, 2);
		idTF = setFieldLayout("ID:", getCurrentClient().getID().toString(), basicClientDetails, 0, 4);
		idTF.setDisable(isDisableEditTextField());

		genderCB = setStringComboBoxLayout("Gender:", GENDERS_LIST, basicClientDetails, 1, 0);
		genderCB.setValue(getCurrentClient().getGender());

		phoneTF = setFieldLayout("Phone Number:", getCurrentClient().getPhoneNumber(), basicClientDetails, 2, 0);
		setBirthDayLayout();
		setUpdateButton();
		centerPane.getChildren().addAll(basicClientDetails, birthDayLayout);
		BorderPane.setMargin(centerPane, new Insets(10));
		this.setCenter(centerPane);
		BorderPane.setAlignment(centerPane, Pos.CENTER);
		BorderPane.setMargin(picturePane, new Insets(10));
		this.setLeft(picturePane);
		BorderPane.setAlignment(picturePane, Pos.TOP_LEFT);
	}

	public void setExtendedClientWindowLayout() {
		maritalStatusCB = setStringComboBoxLayout("Marital Status:", MARITAL_STATUS_LIST, extendedClientDetails, 0, 0);
		maritalStatusCB.setValue(getCurrentClient().getMaritalStatus());

		streetNameTF = setFieldLayout("Street:", getCurrentClient().getStreetName(), extendedClientDetails, 1, 0);
		houseNumberTF = setFieldLayout("House Number:", getCurrentClient().getHouseNumber().toString(),
				extendedClientDetails, 1, 2);
		apartmentTF = setFieldLayout("Apartment:", getCurrentClient().getApartment().toString(), extendedClientDetails,
				1, 4);
		cityTF = setFieldLayout("City:", getCurrentClient().getCity(), extendedClientDetails, 2, 0);
		zipcodeTF = setFieldLayout("Zipcode:", getCurrentClient().getZipcode().toString(), extendedClientDetails, 2, 2);
		centerPane.getChildren().add(extendedClientDetails);
	}

	public ComboBox<String> setStringComboBoxLayout(String labelValue, String[] value, GridPane clientDetails,
			int startingRowIndex, int startingColIndex) {
		Label label = new Label(labelValue);
		ObservableList<String> tempList = FXCollections.observableArrayList(value);
		ComboBox<String> cb = new ComboBox<>(tempList);
		clientDetails.add(label, startingColIndex, startingRowIndex);
		clientDetails.add(cb, startingColIndex + 1, startingRowIndex);
		GridPane.setMargin(cb, new Insets(5, 0, 5, 5));
		return cb;
	}

	public void setBirthDayLayout() {
		Label dayLabel = new Label("Day:");
		ObservableList<Integer> listOfDays = FXCollections.observableArrayList(getDayList());
		dayOfBirthCB = new ComboBox<>(listOfDays);
		dayOfBirthCB.setValue(getCurrentClient().getBirthDay().getDayOfMonth());

		Label monthLabel = new Label("Month:");
		ObservableList<Month> listOfMonths = FXCollections.observableArrayList(Month.values());
		monthOfBirthCB = new ComboBox<>(listOfMonths);
		monthOfBirthCB.setValue(getCurrentClient().getBirthDay().getMonth());

		Integer year = getCurrentClient().getBirthDay().getYear();
		Label yearLabel = new Label("Year:");
		yearOfBirthTF = new TextField(year.toString());
		yearOfBirthTF.setMaxWidth(YEAR_TEXT_FIELD_SIZE);

		Label ageLabel = new Label("Age:");
		ageTF = new TextField(getCurrentClient().getAge().toString());
		ageTF.setDisable(true);
		ageTF.setMaxWidth(AGE_TEXT_FIELD_SIZE);
		birthDayLayout.getChildren().addAll(dayLabel, dayOfBirthCB, monthLabel, monthOfBirthCB, yearLabel,
				yearOfBirthTF, ageLabel, ageTF);
		setMarginForBirthdayNodes(dayLabel, dayOfBirthCB, monthLabel, monthOfBirthCB, yearLabel, yearOfBirthTF,
				ageLabel, ageTF);
	}

	private void setUpdateButton() {
		Button updateButton = new Button("Update");
		updateButton.setPrefWidth(80);
		BorderPane.setMargin(updateButton, new Insets(10));
		this.setBottom(updateButton);
		BorderPane.setAlignment(updateButton, Pos.BOTTOM_RIGHT);
		updateButton.setOnAction(e -> {
			if (isDisableEditTextField()) {
				setDisableEditTextField(false);
				changeFieldsStatus();
				updateButton.setText("Save");
			} else {
				setDisableEditTextField(true);
				saveNewState();
				changeFieldsStatus();
				updateButton.setText("Update");
			}
		});
	}

	public void setMarginForBirthdayNodes(Node... nodes) {
		for (Node node : nodes) {
			HBox.setMargin(node, new Insets(5, 5, 5, 0));
		}
	}

	public Integer[] getDayList() {
		Integer[] listOfDays = new Integer[NUM_OF_DAYS_A_MONTH];
		for (int i = 1; i <= NUM_OF_DAYS_A_MONTH; i++) {
			listOfDays[i - 1] = i;
		}
		return listOfDays;
	}

	public TextField setFieldLayout(String label, String value, GridPane clientDetails, int startingRowIndex,
			int startingColIndex) {
		Label clientLabel = new Label(label);
		TextField clientTF = new TextField(value);
		clientDetails.add(clientLabel, startingColIndex, startingRowIndex);
		clientDetails.add(clientTF, startingColIndex + 1, startingRowIndex);
		GridPane.setMargin(clientTF, new Insets(5, 5, 5, 5));
		return clientTF;
	}

	protected Client getCurrentClient() {
		return currentClient;
	}

	protected void setCurrentClient(Client currentClient) {
		this.currentClient = currentClient;
	}

	protected boolean isDisableEditTextField() {
		return disableEditTextField;
	}

	protected void setDisableEditTextField(boolean disableEditTextField) {
		this.disableEditTextField = disableEditTextField;
	}

}
