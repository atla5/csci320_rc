package com.rainforestcommerce.rcdb.views;

import java.io.IOException;

import com.rainforestcommerce.rcdb.models.ProductQuantityPrice;
import com.rainforestcommerce.rcdb.views.ActivityManager.Activity;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
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
	protected void handleVendorButtonClicked(MouseEvent event) {
		ActivityManager.start(Activity.VENDOR_SHIPMENT);
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
