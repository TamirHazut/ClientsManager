import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

public class ProfilePicture extends HBox {
	public final static String DEFAULT_IMAGE_FOLDER = System.getProperty("user.home") + "\\Clients\\";
	public final static String DEFAULT_IMAGE = "res\\images\\Default.png";
	private final static int PICTURE_FRAME_WIDTH = 200;
	private final static int PICTURE_FRAME_HEIGTH= 500;
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
			image = new Image(new FileInputStream(new File(DEFAULT_IMAGE)));
		}
		this.setPrefWidth(PICTURE_FRAME_WIDTH);
		profilePicture.fitWidthProperty().bind(this.prefWidthProperty());
		profilePicture.setImage(image);
		profilePicture.setPreserveRatio(true);
		profilePicture.setSmooth(true);
		profilePicture.setCache(true);
		this.setPrefHeight(PICTURE_FRAME_HEIGTH);
	}

	private void copyFile(String originFile, String dest) throws IOException {
		Path from = Paths.get(originFile);
		Path to = Paths.get(dest);
		CopyOption[] options = new CopyOption[] { StandardCopyOption.REPLACE_EXISTING,
				StandardCopyOption.COPY_ATTRIBUTES };
		Files.copy(from, to, options);
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
				String dest = DEFAULT_IMAGE_FOLDER + fileName;
				copyFile(filePath, dest);
				this.client.setProfilePicture(fileName);
				ClientsDB.writeClientProfilePicture(this.client);
				setProfilePicture(file);
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
