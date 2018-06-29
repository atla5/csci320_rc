package com.rainforestcommerce.rcdb.views;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.rainforestcommerce.rcdb.models.Customer;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

public class CustomerViewController {
	
	private static VBox view;
	static {
		try {
			view = FXMLLoader.load(CustomerViewController.class.getResource("/CustomerView.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static VBox getView() {
		// TODO: Call method here to get customers from DB,
		// populate tableview with rows,
		// add click handler to each row
		
		// TEST CODE
		ArrayList<Customer> customers = new ArrayList<>();
		return view;
	}

}
