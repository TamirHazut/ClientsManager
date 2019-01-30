package General;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class InvalidDataException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidDataException(String message) {
		super(message);
	}

	public static void showAlert(Alert.AlertType alertType, String message) {
		Alert alert = new Alert(alertType , message, ButtonType.OK);
		alert.setTitle(alertType.toString());
		alert.setHeaderText(null);
		alert.showAndWait();
	}

}
