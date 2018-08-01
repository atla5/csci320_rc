package com.rainforestcommerce.rcdb.views;

import java.io.IOException;
import java.net.URL;
import java.util.Date;

import com.rainforestcommerce.rcdb.models.ProductQuantityPrice;
import com.rainforestcommerce.rcdb.models.Shipment;
import com.rainforestcommerce.rcdb.models.StorePurchase;
import com.rainforestcommerce.rcdb.views.ActivityManager.Activity;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * The controller for the Store Management view.
 */
public class
StoreManagementController {

	private static VBox view;




	@FXML
	private Text title;
	
	@FXML
	private TableView<ProductQuantityPrice> inventory_table;

	@FXML
	private TableView<StorePurchase> purchase_table;
	
	@FXML
	protected void handleLogoutButtonPress(MouseEvent event) {
		ActivityManager.start(Activity.START_SCREEN);
	}
	
	public void initialize() {
		// Populate the inventory table with the items in the store's inventory
    	inventory_table.setItems(FXCollections.observableArrayList(SessionData.store.inventory));
    	
        TableColumn<ProductQuantityPrice, String> product = (TableColumn<ProductQuantityPrice, String>) inventory_table.getColumns().get(0);
        TableColumn<ProductQuantityPrice, String> size_column = (TableColumn<ProductQuantityPrice,String>) inventory_table.getColumns().get(1);
        TableColumn<ProductQuantityPrice, String>  quantity_column = (TableColumn<ProductQuantityPrice,String>) inventory_table.getColumns().get(2);
        TableColumn<ProductQuantityPrice, String> price_column = (TableColumn<ProductQuantityPrice,String>) inventory_table.getColumns().get(3);


        product.setCellValueFactory(new PropertyValueFactory<ProductQuantityPrice, String>("ProductName"));
        size_column.setCellValueFactory(new PropertyValueFactory<ProductQuantityPrice, String>("Size"));
        quantity_column.setCellValueFactory(new PropertyValueFactory<ProductQuantityPrice, String>("Quantity"));
        price_column.setCellValueFactory(new PropertyValueFactory<ProductQuantityPrice, String>("UnitPrice"));

		//Populate the purchases table with the purchase data
		purchase_table.setItems(FXCollections.observableArrayList(SessionData.store.purchase));

		TableColumn<StorePurchase,Long> customer_name = (TableColumn<StorePurchase, Long>) purchase_table.getColumns().get(0);
		TableColumn<StorePurchase,Long> number_items = (TableColumn<StorePurchase, Long>) purchase_table.getColumns().get(1);
		
		customer_name.setCellValueFactory(new PropertyValueFactory<StorePurchase, Long>("CustomerName"));
		number_items.setCellValueFactory(new PropertyValueFactory<StorePurchase, Long>("NumberOfItems"));
		
		title.setText(SessionData.store.getName());
	}
	
	/**
	 * Returns the root node of the view.
	 */
	public static VBox getView() {
		try {
			return FXMLLoader.load(StoreManagementController.class.getResource("/StoreManagementView.fxml"));
		}
		catch (IOException e){
			e.printStackTrace();
			return null;
		}
	}
}
