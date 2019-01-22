import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

public class CommandButton extends Button implements Command {

	public CommandButton() {
		super();
		this.setPrefWidth(ClientsGUI.PREF_ICON_SIZE);
		this.setPrefHeight(ClientsGUI.PREF_ICON_SIZE);
	}

	public void Execute() {
	}

	protected void setBackGroundImage(Image image) {
		BackgroundSize backgroundSize = new BackgroundSize(ClientsGUI.PREF_ICON_SIZE, ClientsGUI.PREF_ICON_SIZE, true, true, true, false);
		BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
		Background background = new Background(backgroundImage);
		this.setBackground(background);
	}

}
