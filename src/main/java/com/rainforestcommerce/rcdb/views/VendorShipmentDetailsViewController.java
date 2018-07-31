package com.rainforestcommerce.rcdb.views;

import java.io.IOException;

import com.rainforestcommerce.rcdb.controllers.ShipmentRequestProxy;
import com.rainforestcommerce.rcdb.controllers.StoreProxy;
import com.rainforestcommerce.rcdb.controllers.VendorProxy;
import com.rainforestcommerce.rcdb.models.ProductQuantityPrice;
import com.rainforestcommerce.rcdb.views.ActivityManager.Activity;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * The controller for the Vendor Shipment Details View.
 * Allows the items in a shipment to be viewed and for
 * the shipment to be shipped.
 */
public class VendorShipmentDetailsViewController {
	
	@FXML
	protected TableView<ProductQuantityPrice> item_table;
	
	@FXML
	protected void handleShipItButtonClicked(MouseEvent event) {
		ShipmentRequestProxy.recieveShipment(SessionData.shipment,SessionData.store);
	}
	
	@FXML
	protected void handleVendorButtonClicked(MouseEvent event) {
		ActivityManager.start(Activity.VENDOR_SHIPMENT);
	}
	

	@FXML
	protected void handleLogoutButtonClicked(MouseEvent event){ActivityManager.start(Activity.START_SCREEN);}


	public void initialize() {


		// Populate the item table with the items in the shipment's contents
		item_table.setItems(FXCollections.observableArrayList(SessionData.shipment.contents));

		TableColumn<ProductQuantityPrice, String> product = (TableColumn<ProductQuantityPrice, String>) item_table.getColumns().get(0);
		TableColumn<ProductQuantityPrice, String> size_column = (TableColumn<ProductQuantityPrice, String>) item_table.getColumns().get(1);
		TableColumn<ProductQuantityPrice, String> brand_column = (TableColumn<ProductQuantityPrice, String>) item_table.getColumns().get(2);
		TableColumn<ProductQuantityPrice, String> price_column = (TableColumn<ProductQuantityPrice, String>) item_table.getColumns().get(3);
		TableColumn<ProductQuantityPrice, String> quantity_column = (TableColumn<ProductQuantityPrice, String>) item_table.getColumns().get(4);

		product.setCellValueFactory(new PropertyValueFactory<ProductQuantityPrice, String>("ProductName"));
		size_column.setCellValueFactory(new PropertyValueFactory<ProductQuantityPrice, String>("Size"));
		brand_column.setCellValueFactory(new PropertyValueFactory<ProductQuantityPrice, String>("Brand"));
		price_column.setCellValueFactory(new PropertyValueFactory<ProductQuantityPrice, String>("UnitPrice"));
		quantity_column.setCellValueFactory(new PropertyValueFactory<ProductQuantityPrice, String>("Quantity"));
		System.out.println(SessionData.shipment.contents);

	}

	public static VBox getView() {
		try {
			return FXMLLoader.load(CartViewController.class.getResource("/VendorShipmentDetailsView.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
