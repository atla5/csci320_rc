package com.rainforestcommerce.rcdb.views;

import com.rainforestcommerce.rcdb.views.AppController.Activity;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class View extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setScene(
				new Scene(
						new StackPane(), 
						500, 400));
		AppController.stage = primaryStage;
		AppController.start(Activity.START_SCREEN);
		
	}
    
	public static void main(String[] args) {
		launch();
	}
}
