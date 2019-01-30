package General;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;

import Client.Client;
import javafx.collections.ObservableList;

public abstract class ClientsDB {
	public final static String FILE_NAME = "Clients.db";
	public final static String FILE_PATH = System.getProperty("user.home") + "\\Clients";
	private final static String CONNECTION_PROPERTY = "jdbc:sqlite:" + FILE_PATH + "\\" + FILE_NAME;

	public static boolean createTable() {
		File file = new File(FILE_PATH);
		if (!file.exists()) {
			file.mkdirs();
		}
		if (!file.isHidden()) {
			try {
				Files.setAttribute(Paths.get(file.getAbsolutePath()), "dos:hidden", true, LinkOption.NOFOLLOW_LINKS);
			} catch (IOException e) {
			}
		}
		try (Connection connection = DriverManager.getConnection(CONNECTION_PROPERTY);) {
			String query = "CREATE TABLE Clients (`FirstName`	TEXT NOT NULL," + "`LastName`	TEXT NOT NULL,"
					+ "`ID`	NUMERIC NOT NULL UNIQUE," + "`PhoneNumber`	TEXT NOT NULL UNIQUE," + "`Email`	TEXT,"
					+ "`Gender`	TEXT NOT NULL," + "`MaritalStatus`	TEXT NOT NULL," + "`DayOfBirth`	INTEGER NOT NULL,"
					+ "`MonthOfBirth`	INTEGER NOT NULL," + "`YearOfBirth`	INTEGER NOT NULL," + "`City`	TEXT,"
					+ "`Street`	TEXT," + "`HouseNumber`	INTEGER," + "`Apartment`	TEXT," + "`Zipcode`	INTEGER,"
					+ "`ProfilePicture`	TEXT," + "`TextFile`	TEXT," + "PRIMARY KEY(`ID`));";
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
				Client client = new Client(firstName, lastName, id, phoneNumber);
				client.setEmail(result.getString("Email"));
				client.setGender(result.getString("Gender"));
				client.setMaritalStatus(result.getString("MaritalStatus"));
				client.setBirthDay(LocalDate.of(result.getInt("YearOfBirth"),Month.of(result.getInt("MonthOfBirth")),result.getInt("DayOfBirth")));
				client.setCity(result.getString("City"));
				client.setStreetName(result.getString("Street"));
				client.setHouseNumber(result.getInt("HouseNumber"));
				client.setApartment(result.getString("Apartment"));
				client.setZipcode(result.getInt("Zipcode"));
				client.setProfilePicture(result.getString("ProfilePicture"));
				client.setTextFile(result.getString("TextFile"));
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
			prestate.setString(13, client.getApartment());
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
			String query = "INSERT INTO Clients(FirstName,LastName,ID,PhoneNumber,Email,Gender,MaritalStatus,DayOfBirth,MonthOfBirth,YearOfBirth,City,Street,HouseNumber,Apartment,Zipcode,ProfilePicture,TextFile) "
					+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
			prestate.setString(14, client.getApartment());
			prestate.setInt(15, client.getZipcode());
			prestate.setString(16, client.getProfilePicture());
			prestate.setString(17, client.getTextFile());
			prestate.executeUpdate();
			prestate.close();
		} catch (SQLException ex) {
			return false;
		}
		return true;
	}

	public static boolean writeClientProfilePicture(Client client) {
		try (Connection connection = DriverManager.getConnection(CONNECTION_PROPERTY);) {
			String query = "UPDATE Clients SET ProfilePicture = ? WHERE ID = ?";
			PreparedStatement prestate = connection.prepareStatement(query);
			prestate.setString(1, client.getProfilePicture());
			prestate.setInt(2, client.getID());
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
