package com.rainforestcommerce.rcdb.views;

import java.io.IOException;

import com.rainforestcommerce.rcdb.views.ActivityManager.Activity;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class MainViewController {
	
	private static VBox view;
	static {
		try {
			view = FXMLLoader.load(MainViewController.class.getResource("/MainView.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static VBox getView() {
		return view;
	}
	
	@FXML
	protected void handleCustomerButtonPress(MouseEvent event) {
		ActivityManager.start(Activity.CUSTOMERS);
	}

}
