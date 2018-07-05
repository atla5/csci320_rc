package com.rainforestcommerce.rcdb.views;

import com.rainforestcommerce.rcdb.views.ActivityManager.Activity;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The primary view class which starts the view component of the application.
 *
 */
public class View extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		ActivityManager.setStage(primaryStage);
		ActivityManager.start(Activity.START_SCREEN);
		primaryStage.show();
	}
    
	/**
	 * Method to launch the view independently of the rest of the application,
	 * i.e. for testing purposes
	 * @param args Not used
	 */
	public static void main(String[] args) {
		launch();
	}
}
