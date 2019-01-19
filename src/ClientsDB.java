import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;

import javafx.collections.ObservableList;

abstract class ClientsDB {
	private final static String CONNECTION_PROPERTY = "jdbc:sqlite:Clients.db";

	public static void loadDB(ObservableList<Client> list) {
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
		}
	}

}
