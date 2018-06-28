package com.rainforestcommerce.rcdb.views;

import com.rainforestcommerce.rcdb.views.ActivityManager.Activity;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class View extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		ActivityManager.stage = primaryStage;
		ActivityManager.start(Activity.START_SCREEN);
		
	}
    
	public static void main(String[] args) {
		launch();
	}
}
