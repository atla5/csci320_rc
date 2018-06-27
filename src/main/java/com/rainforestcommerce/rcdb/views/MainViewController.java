package com.rainforestcommerce.rcdb.views;

import com.rainforestcommerce.rcdb.views.ActivityManager.Activity;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MainViewController {
	@FXML
	protected void handleCustomerButtonPress(ActionEvent event) {
		ActivityManager.start(Activity.CUSTOMERS);
	}

}
