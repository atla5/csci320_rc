package com.rainforestcommerce.rcdb.views;

import com.rainforestcommerce.rcdb.views.ActivityManager.Activity;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class MainViewController {
	@FXML
	protected void handleCustomerButtonPress(MouseEvent event) {
		ActivityManager.start(Activity.CUSTOMERS);
	}

}
