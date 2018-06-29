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

/**
 * The controller for the Customer View.
 * Responsible for loading the view's FXML, configuring the view's table
 * to display Customer objects, and populating the table with the 
 * Customer objects retrieved from the database.
 * 
 * @author Graham Home <gmh5970@g.rit.edu>
 *
 */
public class CustomerViewController {
	
	// The root node of the view
	private static VBox view;
	
	// The table node which is injected by FXMLLoader
	@FXML
	private TableView<Customer> customer_table;
	
	// Loads the view FXML
	static {
		try {
			view = FXMLLoader.load(CustomerViewController.class.getResource("/CustomerView.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets up the Customer table to properly display Customer objects.
	 * This method is called automatically by FXMLLoader.
	 */
	public void initialize() {
		TableColumn<Customer, String> nameColumn = (TableColumn<Customer, String>) customer_table.getColumns().get(0);
		TableColumn<Customer, Date> dobColumn = (TableColumn<Customer, Date>) customer_table.getColumns().get(1);
		TableColumn<Customer, Integer> pointsColumn = (TableColumn<Customer, Integer>) customer_table.getColumns().get(2);
		
		nameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("custName"));
		dobColumn.setCellValueFactory(new PropertyValueFactory<Customer, Date>("birthDate"));
		
		// TEST CODE - remove when DB access is implemented
		try {
			ObservableList<Customer> customers = FXCollections.observableArrayList(
				new Customer(Arrays.asList(new String[] {"1", "Graham", "08/03/1993", "true", "whatever", "1234567890"} )),
				new Customer(Arrays.asList(new String[] {"2", "Abdul", "09/19/1996", "true", "whatever", "1234567890"} ))
			);
			customer_table.setItems(customers);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// End test code
	}
	
	/**
	 * Returns the root node of the view.
	 * @return : The root node.
	 */
	public static VBox getView() {
		return view;
	}

}
