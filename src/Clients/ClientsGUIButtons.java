package Clients;

import Client.Client;
import General.CommandButton;

abstract class ClientsGUIButtons extends CommandButton {
	protected ClientsGUI gui;
	protected Client client;
	
	protected ClientsGUIButtons(ClientsGUI gui) {
		this.gui = gui;
	}
	
}