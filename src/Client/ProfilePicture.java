package Client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import General.ClientsDB;
import General.FilesCopier;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

public class ProfilePicture extends HBox {
	public final static String DEFAULT_IMAGE = "images/Default.png";
	private final static int PICTURE_FRAME_WIDTH = 200;
	private final static int PICTURE_FRAME_HEIGTH = 500;
	private final static int DROP_SHADOW_RADIUS = 10;
	private ImageView profilePicture = new ImageView();
	private Client client;

	public ProfilePicture(Client client, File filePath) {
		this.client = client;
		try {
			setProfilePicture(filePath);
			this.getChildren().add(profilePicture);
		} catch (FileNotFoundException e) {
		}
	}

	protected void setProfilePicture(File filePath) throws FileNotFoundException {
		Image image;
		try (FileInputStream imageIS = new FileInputStream(filePath)) {
			image = new Image(imageIS);
		} catch (IOException e) {
			image = new Image(this.getClass().getClassLoader().getResourceAsStream(DEFAULT_IMAGE));
		}
		this.setPrefWidth(PICTURE_FRAME_WIDTH);
		profilePicture.fitWidthProperty().bind(this.prefWidthProperty());
		profilePicture.setImage(image);
		profilePicture.setPreserveRatio(true);
		profilePicture.setSmooth(true);
		profilePicture.setCache(true);
		profilePicture.setEffect(new DropShadow( DROP_SHADOW_RADIUS, Color.BLACK ));
		this.setPrefHeight(PICTURE_FRAME_HEIGTH);
	}

	protected ImageView getProfilePicture() {
		return profilePicture;
	}

	protected String browsePicture() {
		final FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select Picture");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		getFilesExtensions(fileChooser);
		File file = fileChooser.showOpenDialog(null);
		if (file != null) {
			try {
				String filePath = file.getAbsolutePath();
				String[] filePathSplitted = filePath.split("[.]+");
				String fileName = this.client.idProperty().get() + "." + filePathSplitted[filePathSplitted.length - 1];
				String dest = this.client.getClientDir() + "\\" + fileName;
				FilesCopier.copyFile(dest, filePath);
				setProfilePicture(file);
				this.client.setProfilePicture(dest);
				ClientsDB.writeClientProfilePicture(this.client);
			} catch (IOException e) {
			}
			return file.getName();
		}
		return DEFAULT_IMAGE;
	}

	private void getFilesExtensions(FileChooser fileChooser) {
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG"));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG"));
	}

}
