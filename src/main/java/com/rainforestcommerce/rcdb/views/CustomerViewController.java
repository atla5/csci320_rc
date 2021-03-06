package com.rainforestcommerce.rcdb.views;

import java.io.IOException;
import java.util.Date;

import com.rainforestcommerce.rcdb.controllers.CustomerProxy;
import com.rainforestcommerce.rcdb.models.Customer;
import com.rainforestcommerce.rcdb.views.ActivityManager.Activity;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
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
	
	// The table node which is injected by FXMLLoader
	@FXML
	private TableView<Customer> customer_table;
	
	/**
	 * Sets up the Customer table to properly display Customer objects.
	 * This method is called automatically by FXMLLoader.
	 */
	public void initialize() {
		
		// Get the table columns
		TableColumn<Customer, String> nameColumn = (TableColumn<Customer, String>) customer_table.getColumns().get(0);
		TableColumn<Customer, Integer> pointsColumn = (TableColumn<Customer, Integer>) customer_table.getColumns().get(1);
		
		// Configure the columns to accept the correct properties of Customer
		nameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("custName"));
		pointsColumn.setCellValueFactory(new PropertyValueFactory<Customer,Integer>("Points"));
		
		// Configure the click action for each row in the table
		customer_table.setRowFactory(rf -> {
			TableRow<Customer> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				SessionData.customer = row.getItem();
				ActivityManager.start(Activity.STORE_SELECTION);
			});
			return row;
			}
		);
		
		customer_table.setItems(FXCollections.observableArrayList(CustomerProxy.getCustomers()));
	}
	
	/**
	 * Returns the root node of the view.
	 * @return : The root node.
	 */
	public static VBox getView() {
		try {
			return FXMLLoader.load(CustomerViewController.class.getResource("/CustomerView.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
