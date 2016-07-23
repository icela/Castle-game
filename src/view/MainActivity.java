package view;

/**
 * @author ice1000
 * Created by ice1000 on 2016/7/23.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

public class MainActivity extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource(""));
		primaryStage.setTitle("城堡游戏");
		// TODO create a GUI Form
	}
}
