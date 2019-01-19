import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Client {
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
	private Integer apartment;
	private Integer zipcode;
	private String maritalStatus;

	public Client(Integer id) {
		this(DEFAULT_STRING, DEFAULT_STRING, id, DEFAULT_STRING, DEFAULT_STRING, LocalDate.now(),
				DEFAULT_GENDER, DEFAULT_STRING, DEFAULT_STRING, DEFAULT_INTEGER, DEFAULT_INTEGER, DEFAULT_INTEGER,
				DEFAULT_MARITAL_STATUS);
	}

	public Client(String firstName, String lastName, Integer id, String phoneNumber, String email, LocalDate birthDay,
			String gender, String city, String streetName, Integer houseNumber, Integer apartment, Integer zipcode,
			String maritalStatus) {
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
		this.id = new SimpleStringProperty(id.toString());
		this.phoneNumber = new SimpleStringProperty(phoneNumber);
		this.email = email;
		this.birthDay = birthDay;
		this.gender = gender;
		this.city = city;
		this.streetName = streetName;
		this.houseNumber = houseNumber;
		this.apartment = apartment;
		this.zipcode = zipcode;
		this.maritalStatus = maritalStatus;
	}

	/* Setters And Getters */
	protected String getFirstName() {
		return firstName.get();
	}

	protected void setFirstName(String firstName) {
		this.firstName.set(firstName);
	}

	protected String getLastName() {
		return lastName.get();
	}

	protected void setLastName(String lastName) {
		this.lastName.set(lastName);
	}

	protected Integer getID() {
		Integer tempID = DEFAULT_ID;
		try {
			tempID = Integer.parseInt(id.get());
		} catch (NumberFormatException ex) {
		}
		return tempID;
	}

	protected void setId(Long id) {
		this.id.set(id.toString());
	}

	protected String getPhoneNumber() {
		return phoneNumber.get();
	}

	protected String getEmail() {
		return email;
	}

	protected void setEmail(String email) {
		this.email = email;
	}

	protected String getGender() {
		return gender;
	}

	protected void setGender(String gender) {
		this.gender = gender;
	}

	protected String getCity() {
		return city;
	}

	protected void setCity(String city) {
		this.city = city;
	}

	protected String getStreetName() {
		return streetName;
	}

	protected void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	protected Integer getHouseNumber() {
		return houseNumber;
	}

	protected void setHouseNumber(Integer houseNumber) {
		this.houseNumber = houseNumber;
	}

	protected Integer getApartment() {
		return apartment;
	}

	protected void setApartment(Integer appartment) {
		this.apartment = appartment;
	}

	protected Integer getZipcode() {
		return zipcode;
	}

	protected void setZipcode(Integer zipcode) {
		this.zipcode = zipcode;
	}

	protected String getMaritalStatus() {
		return maritalStatus;
	}

	protected void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	protected void setPhoneNumber(String phoneNumber) {
		this.phoneNumber.set(phoneNumber);
	}

	protected LocalDate getBirthDay() {
		return birthDay;
	}

	protected void setBirthDay(LocalDate birthDay) {
		this.birthDay = birthDay;
	}

	protected Long getAge() {
		return ChronoUnit.YEARS.between(getBirthDay(), LocalDate.now());
	}

	/* TableView Properties */
	protected StringProperty firstNameProperty() {
		return firstName;
	}

	protected StringProperty lastNameProperty() {
		return lastName;
	}

	protected StringProperty idProperty() {
		return id;
	}

	protected StringProperty phoneProperty() {
		return phoneNumber;
	}

}
