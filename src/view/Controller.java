package view;


import castle.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * @author ice1000
 */

public class Controller extends Game {

	@FXML // fx:id="inputBox"
	private TextField inputBox; // Value injected by FXMLLoader

	@FXML // fx:id="sendButton"
	private Button sendButton; // Value injected by FXMLLoader

	@FXML // fx:id="textArea"
	private TextArea textArea; // Value injected by FXMLLoader

	@FXML
	void inputAction(ActionEvent event) {

	}

	@FXML
	void sendClicked(ActionEvent event) {
		handleMessage(inputBox.getText());
	}

	@Override
	public void echo(String words) {
		textArea.appendText(words);
	}

	@Override
	public void echoln(String words) {
		echo(words + "\n");
	}

	@Override
	public void closeScreen() {
		System.exit(0);
	}
}
