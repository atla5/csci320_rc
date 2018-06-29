package com.rainforestcommerce.rcdb.views;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;


import com.rainforestcommerce.rcdb.models.Customer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class CustomerViewController {
	
	private static VBox view;
	
	@FXML
	private TableView<Customer> customer_table;
	
	static {
		try {
			view = FXMLLoader.load(CustomerViewController.class.getResource("/CustomerView.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void initialize() {
		TableColumn<Customer, String> nameColumn = (TableColumn<Customer, String>) customer_table.getColumns().get(0);
		TableColumn<Customer, Date> dobColumn = (TableColumn<Customer, Date>) customer_table.getColumns().get(1);
		TableColumn<Customer, Integer> pointsColumn = (TableColumn<Customer, Integer>) customer_table.getColumns().get(2);
		
		nameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerName"));
		dobColumn.setCellValueFactory(new PropertyValueFactory<Customer, Date>("birthDate"));
		
		try {
			ObservableList<Customer> customers = FXCollections.observableArrayList(
				new Customer(Arrays.asList(new String[] {"1", "Graham", "08/03/1993", "true", "whatever", "1234567890"} )),
				new Customer(Arrays.asList(new String[] {"2", "Abdul", "09/19/1996", "true", "whatever", "1234567890"} ))
			);
			customer_table.setItems(customers);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public static VBox getView() {
		// TODO: Call method here to get customers from DB,
		// populate tableview with rows,
		// add click handler to each row
		
		// TEST CODE
		return view;
	}

}
