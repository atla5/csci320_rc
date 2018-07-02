package com.rainforestcommerce.rcdb.views;

import java.io.IOException;

import com.rainforestcommerce.rcdb.models.ProductPurchase;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class CartViewController {
	
	// The root node of the view
	private static VBox view;
	
	// Loads the view FXML
	static {
		try {
			view = FXMLLoader.load(CartViewController.class.getResource("/CartView.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// The table node which is injected by FXMLLoader
	@FXML
	private TableView item_table;
	
	/**
	 * Sets up the item table to properly display items.
	 * This method is called automatically by FXMLLoader.
	 */
	public void initialize() {
		// Get the table columns
		TableColumn<ProductPurchase, String> nameColumn = (TableColumn<ProductPurchase, String>) item_table.getColumns().get(0);
		TableColumn<ProductPurchase, String> sizeColumn = (TableColumn<ProductPurchase, String>) item_table.getColumns().get(1);
		TableColumn<ProductPurchase, String> brandColumn = (TableColumn<ProductPurchase, String>) item_table.getColumns().get(2);
		TableColumn<ProductPurchase, Float> priceColumn = (TableColumn<ProductPurchase, Float>) item_table.getColumns().get(3);
		TableColumn<ProductPurchase, Integer> quantityColumn = (TableColumn<ProductPurchase, Integer>) item_table.getColumns().get(4);
		
		// Configure the columns to accept the correct properties of ProductPurchase
		// TODO: ProductPurchase must provide access to the item's name, size, and brand before those columns can be set
		// nameColumn.setCellValueFactory(new PropertyValueFactory<ProductPurchase, String>("name"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<ProductPurchase, Float>("overallPrice"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<ProductPurchase, Integer>("quantity"));
	}
	
	/**
	 * Returns the root node of the view.
	 * @return : The root node.
	 */
	public static VBox getView() {
		return view;
	}

}
