package com.rainforestcommerce.rcdb.views;

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
	
	public static void start(Activity activity) {
		switch(activity) {
		case START_SCREEN:
			stage.getScene().setRoot(MainViewController.getView());
			break;
		case CUSTOMERS:
			stage.getScene().setRoot(CustomerViewController.getView());
			break;
		}
	}
	
	public static void setStage(Stage primaryStage) {
		stage = primaryStage;
		stage.setScene(new Scene(new VBox(), 500, 400));
	}

}
