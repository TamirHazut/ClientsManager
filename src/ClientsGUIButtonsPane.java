import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

public class ClientsGUIButtonsPane extends HBox {
	private AddButton addButton;
	private DeleteButton deleteButton;
	private ClientsGUI gui;
	private final Insets buttonsInsets = new Insets(0, 0, 0, 5);
	
	public EventHandler<ActionEvent> ae = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent arg0) {
			((Command) arg0.getSource()).Execute();
		}
	};
	
	public ClientsGUIButtonsPane(ClientsGUI gui) {
		this.gui = gui;
		init();
	}
	
	private void init() {
		addButton = new AddButton(getGui());
		deleteButton = new DeleteButton(getGui());
		this.getChildren().addAll(addButton, deleteButton);
		HBox.setMargin(deleteButton, buttonsInsets);
		this.setAlignment(Pos.BOTTOM_RIGHT);
		addButton.setOnAction(ae);
		deleteButton.setOnAction(ae);
	}

	protected AddButton getAddButton() {
		return addButton;
	}

	protected DeleteButton getDeleteButton() {
		return deleteButton;
	}

	protected ClientsGUI getGui() {
		return gui;
	}
	
	
}
