import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;

import javafx.collections.ObservableList;

abstract class ClientsDB {
	public final static String FILE_NAME = "Clients.db";
	private final static String CONNECTION_PROPERTY = "jdbc:sqlite:" + FILE_NAME;

	public static boolean createTable() {
		try (Connection connection = DriverManager.getConnection(CONNECTION_PROPERTY);) {
			String query = "CREATE TABLE Clients (`FirstName`	TEXT NOT NULL," + "`LastName`	TEXT NOT NULL,"
					+ "`ID`	NUMERIC NOT NULL UNIQUE," + "`PhoneNumber`	TEXT NOT NULL UNIQUE," + "`Email`	TEXT,"
					+ "`Gender`	TEXT NOT NULL," + "`MaritalStatus`	TEXT NOT NULL," + "`DayOfBirth`	INTEGER NOT NULL,"
					+ "`MonthOfBirth`	INTEGER NOT NULL," + "`YearOfBirth`	INTEGER NOT NULL," + "`City`	TEXT,"
					+ "`Street`	TEXT," + "`HouseNumber`	INTEGER," + "`Apartment`	INTEGER," + "`Zipcode`	INTEGER,"
					+ "PRIMARY KEY(`ID`));";
			PreparedStatement prestate = connection.prepareStatement(query);
			prestate.executeUpdate();
			prestate.close();
		} catch (SQLException ex) {
			return false;
		}
		return true;
	}

	public static boolean loadDB(ObservableList<Client> list) {
		try (Connection connection = DriverManager.getConnection(CONNECTION_PROPERTY);) {
			String query = "SELECT * FROM Clients";
			PreparedStatement prestate = connection.prepareStatement(query);
			ResultSet result = prestate.executeQuery();
			while (result.next()) {
				String firstName = result.getString("FirstName");
				String lastName = result.getString("LastName");
				Integer id = result.getInt("ID");
				String phoneNumber = result.getString("PhoneNumber");
				String email = result.getString("Email");
				String gender = result.getString("Gender");
				String maritalStatus = result.getString("MaritalStatus");
				Integer dayOfBirth = result.getInt("DayOfBirth");
				Month monthOfBirth = Month.of(result.getInt("MonthOfBirth"));
				Integer yearOfBirth = result.getInt("YearOfBirth");
				String city = result.getString("City");
				String street = result.getString("Street");
				Integer houseNumber = result.getInt("HouseNumber");
				Integer apartment = result.getInt("Apartment");
				Integer zipcode = result.getInt("Zipcode");
				Client client = new Client(firstName, lastName, id, phoneNumber, email,
						LocalDate.of(yearOfBirth, monthOfBirth, dayOfBirth), gender, city, street, houseNumber,
						apartment, zipcode, maritalStatus);
				list.add(client);
			}
			prestate.close();
			result.close();
		} catch (SQLException ex) {
			return false;
		}
		return true;
	}

	public static boolean updateClient(Client client) {
		try (Connection connection = DriverManager.getConnection(CONNECTION_PROPERTY);) {
			String query = "UPDATE Clients SET " + "FirstName = ?," + "LastName = ?," + "PhoneNumber = ?,"
					+ "Email = ?," + "Gender = ?," + "MaritalStatus = ?," + "DayOfBirth = ?," + "MonthOfBirth = ?,"
					+ "YearOfBirth = ?," + "City = ?," + "Street = ?," + "HouseNumber = ?," + "Apartment = ?,"
					+ "Zipcode = ?" + "WHERE ID = ?";
			PreparedStatement prestate = connection.prepareStatement(query);
			prestate.setString(1, client.getFirstName());
			prestate.setString(2, client.getLastName());
			prestate.setString(3, client.getPhoneNumber());
			prestate.setString(4, client.getEmail());
			prestate.setString(5, client.getGender());
			prestate.setString(6, client.getMaritalStatus());
			prestate.setInt(7, client.getBirthDay().getDayOfMonth());
			prestate.setInt(8, client.getBirthDay().getMonthValue());
			prestate.setInt(9, client.getBirthDay().getYear());
			prestate.setString(10, client.getCity());
			prestate.setString(11, client.getStreetName());
			prestate.setInt(12, client.getHouseNumber());
			prestate.setInt(13, client.getApartment());
			prestate.setInt(14, client.getZipcode());
			prestate.setInt(15, client.getID());
			prestate.executeUpdate();
			prestate.close();
		} catch (SQLException ex) {
			return false;
		}
		return true;
	}

	public static boolean writeNewClient(Client client) {
		try (Connection connection = DriverManager.getConnection(CONNECTION_PROPERTY);) {
			String query = "INSERT INTO Clients(FirstName,LastName,ID,PhoneNumber,Email,Gender,MaritalStatus,DayOfBirth,MonthOfBirth,YearOfBirth,City,Street,HouseNumber,Apartment,Zipcode) "
					+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement prestate = connection.prepareStatement(query);
			prestate.setString(1, client.getFirstName());
			prestate.setString(2, client.getLastName());
			prestate.setInt(3, client.getID());
			prestate.setString(4, client.getPhoneNumber());
			prestate.setString(5, client.getEmail());
			prestate.setString(6, client.getGender());
			prestate.setString(7, client.getMaritalStatus());
			prestate.setInt(8, client.getBirthDay().getDayOfMonth());
			prestate.setInt(9, client.getBirthDay().getMonthValue());
			prestate.setInt(10, client.getBirthDay().getYear());
			prestate.setString(11, client.getCity());
			prestate.setString(12, client.getStreetName());
			prestate.setInt(13, client.getHouseNumber());
			prestate.setInt(14, client.getApartment());
			prestate.setInt(15, client.getZipcode());
			prestate.executeUpdate();
			prestate.close();
		} catch (SQLException ex) {
			return false;
		}
		return true;
	}

	public static boolean deleteClient(Client client) {
		try (Connection connection = DriverManager.getConnection(CONNECTION_PROPERTY);) {
			String query = "DELETE FROM CLIENTS WHERE ID = ?";
			PreparedStatement prestate = connection.prepareStatement(query);
			prestate.setInt(1, client.getID());
			prestate.executeUpdate();
			prestate.close();
		} catch (SQLException ex) {
			return false;
		}
		return true;
	}

}
