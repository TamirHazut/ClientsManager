package Client;


import javafx.scene.control.TextArea;

import java.io.*;

public class ClientGUIFreeText extends TextArea {
    private final int HEIGHT = 200;
    private final int WIDTH = 300;
    private String clientFile;

    public ClientGUIFreeText(String clientFile) {
        this.clientFile = clientFile;
        this.setPrefSize(WIDTH,HEIGHT);
    }

    public boolean saveFreeText() {
        if (!this.getText().isEmpty()) {
            File file = new File(getClientFile());
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
                try (ObjectOutputStream o = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(getClientFile())))) {
                    o.writeObject(this.getText());
                    return true;
                }
            }catch (IOException e) {
            }
        }
        return false;
    }

    public void readFreeText() {
        File file = new File(getClientFile());
        if (file.exists() && file.isFile()) {
            try (ObjectInputStream i = new ObjectInputStream(new BufferedInputStream(new FileInputStream(getClientFile())))) {
                    this.setText((String) (i.readObject()));
            }
            catch (IOException | ClassNotFoundException e) {
            }
        }
    }

    public String getClientFile() {
        return clientFile;
    }
}
