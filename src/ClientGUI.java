import java.io.File;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ClientGUI extends BorderPane {
	private final static int MIN_SCEONDERY_STAGE_HEIGTH = 530;
	private final static int MIN_SCEONDERY_STAGE_WIDTH = 650;
	private final static int SMALL_TEXT_FIELD_SIZE = 50;
	private final static int NUM_OF_DAYS_A_MONTH = 31;
	private final static int EMAIL_TEXT_FIELD_LENGTH = 325;
	private final static int MAX_PHONE_NUMBER_LENGTH = 10;

	private final static String[] GENDERS_LIST = { "Male", "Female" };
	private final static String[] MARITAL_STATUS_LIST = { "Single", "Married", "Separated", "Divorced", "Widowed" };

	private Client currentClient;
	private boolean disableEditTextField = true;
	private boolean newClient;
	private boolean dataHasChanged;

	private ProfilePicture profilePicture;

	private final Insets fieldsInsets = new Insets(5);
	private final Insets otherInsets = new Insets(10);

	private GridPane clientDetails = new GridPane();
	private TextField firstNameTF;
	private TextField lastNameTF;
	private TextField idTF;
	private TextField phoneTF;
	private TextField emailTF;
	private ComboBox<String> genderCB;

	private ComboBox<String> maritalStatusCB;

	private TextField cityTF;
	private TextField streetNameTF;
	private TextField houseNumberTF;
	private TextField apartmentTF;
	private TextField zipcodeTF;

	private TextField yearOfBirthTF;
	private TextField ageTF;
	private ComboBox<Integer> dayOfBirthCB;
	private ComboBox<Month> monthOfBirthCB;

	private EditSaveButton editSaveButton;

	public EventHandler<ActionEvent> ae = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent arg0) {
			((Command) arg0.getSource()).Execute();
		}
	};

	public ClientGUI(Client client, boolean newClient) {
		setNewClient(newClient);
		setCurrentClient(client);
		disableEditTextField(!isANewClient());
		initClientWindow();
		setDataHasChanged(newClient);
	}

	public ClientGUI(Client client) {
		this(client, true);
	}

	private void initClientWindow() {
		setClientWindowLayout();
		changeFieldsStatus();

		Stage seconderyStage = new Stage();
		seconderyStage.setTitle("Client Window");
		seconderyStage.setScene(new Scene(this));
		seconderyStage.setResizable(false);
		seconderyStage.setMinHeight(MIN_SCEONDERY_STAGE_HEIGTH);
		seconderyStage.setMinWidth(MIN_SCEONDERY_STAGE_WIDTH);
		seconderyStage.showAndWait();
	}

	protected boolean saveNewState() {
		try {
			if (firstNameTF.getText().isEmpty() || lastNameTF.getText().isEmpty() || phoneTF.getText().isEmpty()) {
				throw new InvalidDataException(
						"Error!\nOne of the following fields are empty: Firstname, Lastname, Phonenumber.");
			}
			if (!(firstNameTF.getText().equals(getCurrentClient().getFirstName()))) {
				getCurrentClient().setFirstName(firstNameTF.getText());
				setDataHasChanged(true);
			}
			if (!(lastNameTF.getText().equals(getCurrentClient().getLastName()))) {
				getCurrentClient().setLastName(lastNameTF.getText());
				setDataHasChanged(true);
			}
			String phone = Client.fixPhoneNumber(phoneTF.getText());
			checkPhoneNumber(phone);
			if (!(phone.equals(getCurrentClient().getPhoneNumber()))) {
				getCurrentClient().setPhoneNumber(phone);
				setDataHasChanged(true);
				this.phoneTF.setText(phone);
			}
			if (!genderCB.getValue().equals(getCurrentClient().getGender())) {
				getCurrentClient().setGender(genderCB.getValue());
				setDataHasChanged(true);
			}
			if (!emailTF.getText().isEmpty() && !(emailTF.getText().equals(getCurrentClient().getEmail()))) {
				getCurrentClient().setEmail(emailTF.getText());
				setDataHasChanged(true);

			}
			checkForValidDate();
			if (!(maritalStatusCB.getValue().equals(getCurrentClient().getMaritalStatus()))) {
				getCurrentClient().setMaritalStatus(maritalStatusCB.getValue());
				setDataHasChanged(true);
			}
			saveStreetState();
			return true;
		} catch (InvalidDataException ex) {
			showAlert(ex.getMessage());
			return false;
		}
	}

	private void checkPhoneNumber(String phone) throws InvalidDataException {
		try {
			if (phone.length() > MAX_PHONE_NUMBER_LENGTH || phone.charAt(0) != '0') {
				throw new Exception();
			}
			Integer.parseInt(phone);
		} catch (Exception e) {
			throw new InvalidDataException("Error!\nInvalid phone number.");
		}
	}

	private void showAlert(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	private void saveStreetState() throws InvalidDataException {
		Integer houseNumber = getCurrentClient().getHouseNumber();
		Integer zipcode = getCurrentClient().getZipcode();
		try {
			if (!cityTF.getText().isEmpty() && !(cityTF.getText().equals(getCurrentClient().getCity()))) {
				getCurrentClient().setCity(cityTF.getText());
				setDataHasChanged(true);
			}
			if (!streetNameTF.getText().isEmpty()
					&& !(streetNameTF.getText().equals(getCurrentClient().getStreetName()))) {
				getCurrentClient().setStreetName(streetNameTF.getText());
				setDataHasChanged(true);
			}
			if (!houseNumberTF.getText().isEmpty()) {
				houseNumber = Integer.parseInt(houseNumberTF.getText());
				if (!houseNumber.equals(getCurrentClient().getHouseNumber())) {
					getCurrentClient().setHouseNumber(houseNumber);
					setDataHasChanged(true);
				}
			}
			if (!apartmentTF.getText().isEmpty() && !apartmentTF.getText().equals(getCurrentClient().getApartment())) {
				getCurrentClient().setApartment(apartmentTF.getText());
				setDataHasChanged(true);
			}
			if (!zipcodeTF.getText().isEmpty()) {
				zipcode = Integer.parseInt(zipcodeTF.getText());
				if (!zipcode.equals(getCurrentClient().getZipcode())) {
					getCurrentClient().setZipcode(zipcode);
					setDataHasChanged(true);
				}
			}
		} catch (NumberFormatException ex) {
			throw new InvalidDataException(
					"Error!\nOne of the following fields has invalid data: Housenumber, Apartment, Zipcode.");
		}
	}

	private void checkForValidDate() throws InvalidDataException {
		int day = dayOfBirthCB.getValue();
		Month month = monthOfBirthCB.getValue();
		int year = getCurrentClient().getBirthDay().getYear();
		LocalDate tempDate;
		try {
			if (!yearOfBirthTF.getText().isEmpty()) {
				year = Integer.parseInt(yearOfBirthTF.getText());
			}
		} catch (NumberFormatException ex) {
			throw new InvalidDataException("Error!\nYear of birth is not valid");
		}
		try {
			tempDate = LocalDate.of(year, month, day);
		} catch (DateTimeException ex) {
			throw new InvalidDataException("Error!\nDate of birth is not valid");
		}
		if (tempDate != null) {
			if (!tempDate.equals(getCurrentClient().getBirthDay())) {
				getCurrentClient().setBirthDay(tempDate);
				setDataHasChanged(true);
				ageTF.setText(getCurrentClient().getAge().toString());
			}
		}
	}

	public void changeFieldsStatus() {
		firstNameTF.setDisable(disabledEditTextField());
		lastNameTF.setDisable(disabledEditTextField());
		genderCB.setDisable(disabledEditTextField());
		phoneTF.setDisable(disabledEditTextField());
		emailTF.setDisable(disabledEditTextField());
		yearOfBirthTF.setDisable(disabledEditTextField());
		dayOfBirthCB.setDisable(disabledEditTextField());
		monthOfBirthCB.setDisable(disabledEditTextField());
		cityTF.setDisable(disabledEditTextField());
		streetNameTF.setDisable(disabledEditTextField());
		houseNumberTF.setDisable(disabledEditTextField());
		apartmentTF.setDisable(disabledEditTextField());
		zipcodeTF.setDisable(disabledEditTextField());
		maritalStatusCB.setDisable(disabledEditTextField());
	}

	/* Setters And Getters */
	public void setClientWindowLayout() {
		profilePicture = new ProfilePicture(getCurrentClient(),new File(getCurrentClient().getProfilePicture()));
		setPictureListner();

		Label firstNameLabel = new Label("First Name:");
		firstNameTF = new TextField(getCurrentClient().getFirstName());
		firstNameTF.setPromptText("Ex: Israel");

		Label lastNameLabel = new Label("Last Name:");
		lastNameTF = new TextField(getCurrentClient().getLastName());
		lastNameTF.setPromptText("Ex: Israeli");

		Label idLabel = new Label("ID:");
		idTF = new TextField(getCurrentClient().getID().toString());
		idTF.setDisable(true);
		HBox firstRow = setRow(firstNameLabel, firstNameTF, lastNameLabel, lastNameTF, idLabel, idTF);
		clientDetails.add(firstRow, 0, 0);
		GridPane.setMargin(firstRow, fieldsInsets);

		Label genderLabel = new Label("Gender:");
		genderCB = new ComboBox<>(FXCollections.observableArrayList(GENDERS_LIST));
		genderCB.setValue(getCurrentClient().getGender());
		HBox secondRow = setRow(genderLabel, genderCB);
		clientDetails.add(secondRow, 0, 1);
		GridPane.setMargin(secondRow, fieldsInsets);

		Label phoneLabel = new Label("Phone Number:");
		phoneTF = new TextField(getCurrentClient().getPhoneNumber());
		phoneTF.setPromptText("Ex: 000-0000000");

		Label emailLabel = new Label("EMail:");
		emailTF = new TextField(getCurrentClient().getEmail());
		emailTF.setPromptText("Ex: A@a");
		emailTF.setMinWidth(EMAIL_TEXT_FIELD_LENGTH);
		HBox thirdRow = setRow(phoneLabel, phoneTF, emailLabel, emailTF);
		clientDetails.add(thirdRow, 0, 2);
		GridPane.setMargin(thirdRow, fieldsInsets);

		HBox fourthRow = setBirthDayLayout();
		clientDetails.add(fourthRow, 0, 3);

		Label maritalStatusLabel = new Label("Marital Status:");
		maritalStatusCB = new ComboBox<>(FXCollections.observableArrayList(MARITAL_STATUS_LIST));
		maritalStatusCB.setValue(getCurrentClient().getMaritalStatus());
		HBox fifthRow = setRow(maritalStatusLabel, maritalStatusCB);
		clientDetails.add(fifthRow, 0, 4);
		GridPane.setMargin(fifthRow, fieldsInsets);

		VBox sixthRow = setAddressLayout();
		clientDetails.add(sixthRow, 0, 5);
		GridPane.setMargin(sixthRow, fieldsInsets);

		setUpdateButton();
		this.setCenter(clientDetails);
		BorderPane.setAlignment(clientDetails, Pos.CENTER);
		BorderPane.setMargin(profilePicture, otherInsets);
		this.setLeft(profilePicture);
		BorderPane.setAlignment(profilePicture, Pos.TOP_LEFT);

	}

	private void setPictureListner() {
		profilePicture.setOnMouseClicked(e -> {
			if (!disabledEditTextField()) {
				profilePicture.browsePicture();
			}
		});
	}

	public HBox setRow(Node... nodes) {
		HBox row = new HBox(nodes);
		row.setSpacing(5);
		return row;
	}

	public VBox setAddressLayout() {
		Label streetNameLabel = new Label("Street:");
		streetNameTF = new TextField(getCurrentClient().getStreetName());
		streetNameTF.setPromptText("Ex: Israel Ben Gurion");

		Label houseNumberLabel = new Label("House Number:");
		houseNumberTF = new TextField(getCurrentClient().getHouseNumber().toString());
		houseNumberTF.setMaxWidth(SMALL_TEXT_FIELD_SIZE);
		houseNumberTF.setPromptText("Ex: 1");

		Label apartmentLabel = new Label("Apartment:");
		apartmentTF = new TextField(getCurrentClient().getApartment().toString());
		apartmentTF.setMaxWidth(SMALL_TEXT_FIELD_SIZE);
		apartmentTF.setPromptText("Ex: 1");
		HBox streetDetails = setRow(streetNameLabel, streetNameTF, houseNumberLabel, houseNumberTF, apartmentLabel,
				apartmentTF);

		Label cityLabel = new Label("City:");
		cityTF = new TextField(getCurrentClient().getCity());
		cityTF.setPromptText("Ex: Tel-Aviv");
		Label zipcodeLabel = new Label("Zipcode:");
		zipcodeTF = new TextField(getCurrentClient().getZipcode().toString());
		zipcodeTF.setPromptText("Ex: 12345");
		HBox cityDetails = setRow(cityLabel, cityTF, zipcodeLabel, zipcodeTF);

		VBox address = new VBox(streetDetails, cityDetails);
		address.setSpacing(5);
		return address;
	}

	public HBox setBirthDayLayout() {
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
		yearOfBirthTF.setPromptText("Ex: 1900");
		yearOfBirthTF.setMaxWidth(SMALL_TEXT_FIELD_SIZE);

		Label ageLabel = new Label("Age:");
		ageTF = new TextField(getCurrentClient().getAge().toString());
		ageTF.setDisable(true);
		ageTF.setMaxWidth(SMALL_TEXT_FIELD_SIZE);
		HBox birthDayLayout = new HBox(dayLabel, dayOfBirthCB, monthLabel, monthOfBirthCB, yearLabel, yearOfBirthTF,
				ageLabel, ageTF);
		setMarginForBirthdayNodes(dayLabel, dayOfBirthCB, monthLabel, monthOfBirthCB, yearLabel, yearOfBirthTF,
				ageLabel, ageTF);
		return birthDayLayout;
	}

	private void setUpdateButton() {
		editSaveButton = new EditSaveButton(this, isANewClient());
		BorderPane.setMargin(editSaveButton, otherInsets);
		this.setBottom(editSaveButton);
		BorderPane.setAlignment(editSaveButton, Pos.BOTTOM_RIGHT);
		editSaveButton.setOnAction(ae);
	}

	public void setMarginForBirthdayNodes(Node... nodes) {
		for (Node node : nodes) {
			HBox.setMargin(node, fieldsInsets);
		}
	}

	public Integer[] getDayList() {
		Integer[] listOfDays = new Integer[NUM_OF_DAYS_A_MONTH];
		for (int i = 1; i <= NUM_OF_DAYS_A_MONTH; i++) {
			listOfDays[i - 1] = i;
		}
		return listOfDays;
	}

	protected Client getCurrentClient() {
		return currentClient;
	}

	protected void setCurrentClient(Client currentClient) {
		this.currentClient = currentClient;
	}

	protected boolean disabledEditTextField() {
		return disableEditTextField;
	}

	protected void disableEditTextField(boolean disableEditTextField) {
		this.disableEditTextField = disableEditTextField;
	}

	protected boolean isANewClient() {
		return newClient;
	}

	protected void setNewClient(boolean newClient) {
		this.newClient = newClient;
	}

	protected boolean isDataChanged() {
		return dataHasChanged;
	}

	protected void setDataHasChanged(boolean dataHasChanged) {
		this.dataHasChanged = dataHasChanged;
	}

}
