package com.rainforestcommerce.rcdb.views;

import javafx.scene.Scene;
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
	
	public static void start(Activity activity) {
		stage.hide();
		switch(activity) {
		case START_SCREEN:
			stage.setScene(new Scene(MainViewController.getView(), stageX, stageY));
			break;
		case CUSTOMERS:
			stage.setScene(new Scene(CustomerViewController.getView(), stageX, stageY));
			break;
		}
		stage.show();
	}

}
