package com.rainforestcommerce.rcdb.views;

import java.io.IOException;

import com.rainforestcommerce.rcdb.views.ActivityManager.Activity;
import com.rainforestcommerce.rcdb.views.ActivityManager.User;

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
		ActivityManager.user = User.CUSTOMER;
		ActivityManager.start(Activity.CUSTOMERS);
	}
	
	@FXML
	protected void handleVendorButtonPress(MouseEvent event) {
		ActivityManager.user = User.VENDOR;
		ActivityManager.start(Activity.VENDOR_SELECTION);
	}
	
	@FXML
	protected void handleStoreMgmtButtonPress(MouseEvent event) {
		ActivityManager.user = User.STORE_MANAGER;
		ActivityManager.start(Activity.STORE_SELECTION);
	}

}
