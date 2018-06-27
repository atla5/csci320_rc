package com.rainforestcommerce.rcdb.views;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AppController {
	
	public enum Activity {
		START_SCREEN,
		CUSTOMERS,
		PRODUCTS,
		CART,
		;
	}
	
	public static Stage stage;
	private static VBox startScreen;
	static {
		try {
			startScreen = FXMLLoader.load(AppController.class.getResource("MainView.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void start(Activity activity) {
		switch(activity) {
		case START_SCREEN:
			stage.getScene().setRoot(startScreen);
		}
	}

}
