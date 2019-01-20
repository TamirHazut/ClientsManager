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
	private final static int SMALL_TEXT_FIELD_SIZE = 50;
	private final static int NUM_OF_DAYS_A_MONTH = 31;
	private final static int EMAIL_TEXT_FIELD_LENGTH = 325;

	private final static String[] GENDERS_LIST = { "Male", "Female" };
	private final static String[] MARITAL_STATUS_LIST = { "Single", "Married", "Separated", "Divorced", "Widowed" };

	private Client currentClient;
	private boolean disableEditTextField = true;
	private boolean newClient;

	private Shape pictureFrame = new Rectangle(PICTURE_FRAME_WIDTH, PICTURE_FRAME_HIEGHT);

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

	protected void saveNewState() {
		if (!firstNameTF.getText().isEmpty() && !(firstNameTF.getText().equals(getCurrentClient().getFirstName()))) {
			getCurrentClient().setFirstName(firstNameTF.getText());
		}
		if (!lastNameTF.getText().isEmpty() && !(lastNameTF.getText().equals(getCurrentClient().getLastName()))) {
			getCurrentClient().setLastName(lastNameTF.getText());
		}
		if (!phoneTF.getText().isEmpty() && !(phoneTF.getText().equals(getCurrentClient().getPhoneNumber()))) {
			getCurrentClient().setPhoneNumber(phoneTF.getText());
		}
		if (!emailTF.getText().isEmpty() && !(emailTF.getText().equals(getCurrentClient().getEmail()))) {
			getCurrentClient().setEmail(emailTF.getText());
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
		firstNameTF.setDisable(isDisabledEditTextField());
		lastNameTF.setDisable(isDisabledEditTextField());
		genderCB.setDisable(isDisabledEditTextField());
		phoneTF.setDisable(isDisabledEditTextField());
		emailTF.setDisable(isDisabledEditTextField());
		yearOfBirthTF.setDisable(isDisabledEditTextField());
		dayOfBirthCB.setDisable(isDisabledEditTextField());
		monthOfBirthCB.setDisable(isDisabledEditTextField());
		cityTF.setDisable(isDisabledEditTextField());
		streetNameTF.setDisable(isDisabledEditTextField());
		houseNumberTF.setDisable(isDisabledEditTextField());
		apartmentTF.setDisable(isDisabledEditTextField());
		zipcodeTF.setDisable(isDisabledEditTextField());
		maritalStatusCB.setDisable(isDisabledEditTextField());
	}
	
//	public void actionHandled(ActionEvent e) {
//		((Command) e.getSource()).Execute();
//	}

	/* Setters And Getters */
	public void setClientWindowLayout() {
		HBox picturePane = new HBox(pictureFrame);
		Label firstNameLabel = new Label("First Name:");
		firstNameTF = new TextField(getCurrentClient().getFirstName());

		Label lastNameLabel = new Label("Last Name:");
		lastNameTF = new TextField(getCurrentClient().getLastName());

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
		Label emailLabel = new Label("EMail:");
		emailTF = new TextField(getCurrentClient().getEmail());
		emailTF.setMinWidth(EMAIL_TEXT_FIELD_LENGTH);
		HBox thirdRow = setRow(phoneLabel, phoneTF,emailLabel,emailTF);
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
		BorderPane.setMargin(picturePane, otherInsets);
		this.setLeft(picturePane);
		BorderPane.setAlignment(picturePane, Pos.TOP_LEFT);
	}

	public HBox setRow(Node... nodes) {
		HBox row = new HBox(nodes);
		row.setSpacing(5);
		return row;
	}

	public VBox setAddressLayout() {
		Label streetNameLabel = new Label("Street:");
		streetNameTF = new TextField(getCurrentClient().getStreetName());
		Label houseNumberLabel = new Label("House Number:");
		houseNumberTF = new TextField(getCurrentClient().getHouseNumber().toString());
		houseNumberTF.setMaxWidth(SMALL_TEXT_FIELD_SIZE);
		Label apartmentLabel = new Label("Apartment:");
		apartmentTF = new TextField(getCurrentClient().getApartment().toString());
		apartmentTF.setMaxWidth(SMALL_TEXT_FIELD_SIZE);
		HBox streetDetails = setRow(streetNameLabel, streetNameTF, houseNumberLabel, houseNumberTF, apartmentLabel,
				apartmentTF);

		Label cityLabel = new Label("City:");
		cityTF = new TextField(getCurrentClient().getCity());
		Label zipcodeLabel = new Label("Zipcode:");
		zipcodeTF = new TextField(getCurrentClient().getZipcode().toString());
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
		editSaveButton = new EditSaveButton(this,isANewClient());
//		updateButton.setPrefWidth(80);
		BorderPane.setMargin(editSaveButton, otherInsets);
		this.setBottom(editSaveButton);
		BorderPane.setAlignment(editSaveButton, Pos.BOTTOM_RIGHT);
		editSaveButton.setOnAction(ae);
//		updateButton.setOnAction(e -> {
//			if (isDisableEditTextField()) {
//				setDisableEditTextField(false);
//				changeFieldsStatus();
//				updateButton.setText("Save");
//			} else {
//				setDisableEditTextField(true);
//				saveNewState();
//				if (!isANewClient()) {
//				} else {
//					ClientsDB.writeNewClient(getCurrentClient());
//				}
//				changeFieldsStatus();
//				updateButton.setText("Update");
//			}
//		});
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

	protected boolean isDisabledEditTextField() {
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
	
	

}
