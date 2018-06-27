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
	private static final int stageX = 500;
	private static final int stageY = 400;
	private static VBox startScreen;
	private static VBox customersScreen;
	static {
		try {
			startScreen = FXMLLoader.load(ActivityManager.class.getResource("MainView.fxml"));
			customersScreen = FXMLLoader.load(ActivityManager.class.getResource("CustomerView.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void start(Activity activity) {
		stage.hide();
		switch(activity) {
		case START_SCREEN:
			stage.setScene(new Scene(startScreen, stageX, stageY));
			break;
		case CUSTOMERS:
			stage.setScene(new Scene(customersScreen, stageX, stageY));
			break;
		}
		stage.show();
	}

}
