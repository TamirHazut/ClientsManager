package Client;
import java.io.File;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Client {
	public final static String DEFAULT_CLIENTS_FOLDER = System.getProperty("user.home") + "\\Clients\\";
	private final static Integer DEFAULT_ID = 123456789;
	private final static String DEFAULT_STRING = "";
	private final static String DEFAULT_GENDER = "Male";
	private final static Integer DEFAULT_INTEGER = 0;
	private final static String DEFAULT_MARITAL_STATUS = "Single";
	private SimpleStringProperty firstName;
	private SimpleStringProperty lastName;
	private SimpleStringProperty id;
	private SimpleStringProperty phoneNumber;
	private String email;
	private String gender;
	private LocalDate birthDay;
	private String city;
	private String streetName;
	private Integer houseNumber;
	private String apartment;
	private Integer zipcode;
	private String maritalStatus;
	private String profilePicture;
	private String textFile;

	public Client(Integer id) {
		this(DEFAULT_STRING, DEFAULT_STRING, id, DEFAULT_STRING);
	}

	public Client(String firstName, String lastName, Integer id, String phoneNumber) {
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
		this.id = new SimpleStringProperty(id.toString());
		this.phoneNumber = new SimpleStringProperty(phoneNumber);
		this.email = DEFAULT_STRING;
		this.birthDay = LocalDate.now();
		this.gender = DEFAULT_GENDER;
		this.city = DEFAULT_STRING;
		this.streetName = DEFAULT_STRING;
		this.houseNumber = DEFAULT_INTEGER;
		this.apartment = DEFAULT_STRING;
		this.zipcode = DEFAULT_INTEGER;
		this.maritalStatus = DEFAULT_MARITAL_STATUS;
		this.profilePicture = ProfilePicture.DEFAULT_IMAGE;
		this.textFile = getClientDir() + "\\" + getID() + ".txt";
	}

	/* Setters And Getters */
	public String getFirstName() {
		return firstName.get();
	}

	public void setFirstName(String firstName) {
		this.firstName.set(firstName);
	}

	public String getLastName() {
		return lastName.get();
	}

	public void setLastName(String lastName) {
		this.lastName.set(lastName);
	}

	public Integer getID() {
		Integer tempID = DEFAULT_ID;
		try {
			tempID = Integer.parseInt(id.get());
		} catch (NumberFormatException ex) {
		}
		return tempID;
	}

	public void setId(Long id) {
		this.id.set(id.toString());
	}

	public String getPhoneNumber() {
		return phoneNumber.get();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public Integer getHouseNumber() { return houseNumber; }

	public void setHouseNumber(Integer houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getApartment() {
		return apartment;
	}

	public void setApartment(String appartment) {
		this.apartment = appartment;
	}

	public Integer getZipcode() {
		return zipcode;
	}

	public void setZipcode(Integer zipcode) {
		this.zipcode = zipcode;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber.set(phoneNumber);
	}

	public static final String fixPhoneNumber(String phoneNumber) {
		String[] phone = phoneNumber.split("[-]+");
		StringBuilder newPhoneNumber = new StringBuilder();
		for (int i = 0; i < phone.length; i++) {
			newPhoneNumber.append(phone[i]);
		}
		return newPhoneNumber.toString();
	}

	public LocalDate getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(LocalDate birthDay) {
		this.birthDay = birthDay;
	}

	public Long getAge() {
		return ChronoUnit.YEARS.between(getBirthDay(), LocalDate.now());
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String pictureFile) {
		this.profilePicture = pictureFile;
	}

	public String getTextFile() {
		return textFile;
	}
	public void setTextFile(String textFile) {
		this.textFile = textFile;
	}
	public String getClientDir() {
		return DEFAULT_CLIENTS_FOLDER + getID();
	}

	/* TableView Properties */
	public StringProperty firstNameProperty() {
		return firstName;
	}

	public StringProperty lastNameProperty() {
		return lastName;
	}

	public StringProperty idProperty() {
		return id;
	}

	public StringProperty phoneProperty() {
		return phoneNumber;
	}

}
