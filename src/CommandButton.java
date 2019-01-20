import javafx.scene.control.Button;

public class CommandButton extends Button implements Command {
	protected final static int PREF_WIDTH = 80;

	public CommandButton() {
		super();
		this.setPrefWidth(PREF_WIDTH);
	}

	public void Execute() {
	}

}
