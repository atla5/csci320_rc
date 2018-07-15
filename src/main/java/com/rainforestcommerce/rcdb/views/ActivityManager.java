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
		STORE_SELECTION,
		VENDOR_SELECTION,
		;
	}
	
	public enum User {
		CUSTOMER,
		VENDOR,
		STORE_MANAGER,
		;
	}
	
	public static User user;
	
	public static Stage stage;
	
	public static void start(Activity activity) {
		switch(activity) {
			case START_SCREEN:
				stage.getScene().setRoot(MainViewController.getView());
				break;
			case CUSTOMERS:
				stage.getScene().setRoot(CustomerViewController.getView());
				break;
			case CART:
				stage.getScene().setRoot(CartViewController.getView());
				break;
			case PRODUCTS:
				stage.getScene().setRoot(StoreViewController.getView());
				break;
			case STORE_SELECTION:
				stage.getScene().setRoot(StoreSelectionViewController.getView());
				break;
			case VENDOR_SELECTION:
				stage.getScene().setRoot(SelectVendorViewController.getView());
				break;
		}
	}
	
	public static void setStage(Stage primaryStage) {
		stage = primaryStage;
		stage.setScene(new Scene(new VBox(), 625, 800));
	}

}
