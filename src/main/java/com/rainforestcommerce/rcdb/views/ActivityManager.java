package com.rainforestcommerce.rcdb.views;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ActivityManager {
	
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
			startScreen = FXMLLoader.load(ActivityManager.class.getResource("MainView.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void start(Activity activity) {
		stage.hide();
		switch(activity) {
		case START_SCREEN:
			stage.setScene(new Scene(startScreen, 500, 400));
			break;
		}
		stage.show();
	}

}
