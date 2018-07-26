package com.rainforestcommerce.rcdb.views;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import org.omg.CORBA.INITIALIZE;

import com.rainforestcommerce.rcdb.controllers.PurchaseProxy;
import com.rainforestcommerce.rcdb.models.Product;
import com.rainforestcommerce.rcdb.models.ProductQuantityPrice;
import com.rainforestcommerce.rcdb.models.StorePurchase;
import com.rainforestcommerce.rcdb.views.ActivityManager.Activity;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class CartViewController {
	
	// The table node which is injected by FXMLLoader
	@FXML
	private TableView<ProductQuantityPrice> item_table;
	
	@FXML
	private Text total;
	
	@FXML
	protected void handleStoreButtonPress(MouseEvent event) {
		ActivityManager.start(Activity.PRODUCTS);
	}
	
	@FXML
	protected void handleLogoutButtonPress(MouseEvent event) {
		ActivityManager.start(Activity.START_SCREEN);
	}
	
	@FXML
	protected void handleCheckoutButtonPress(MouseEvent event) {
		PurchaseProxy.insertNewStorePurchase(SessionData.shoppingCart);
		ActivityManager.start(Activity.STORE_SELECTION);
	}
	
	/**
	 * Sets up the item table to properly display items.
	 * This method is called automatically by FXMLLoader.
	 */
	public void initialize() {
		// Populate the list with the items in the shopping cart
		item_table.setItems(FXCollections.observableArrayList(SessionData.shoppingCart.products.values()));
        
		// Get the table columns
		TableColumn<ProductQuantityPrice, String> nameColumn = (TableColumn<ProductQuantityPrice, String>) item_table.getColumns().get(0);
		TableColumn<ProductQuantityPrice, String> sizeColumn = (TableColumn<ProductQuantityPrice, String>) item_table.getColumns().get(1);
		TableColumn<ProductQuantityPrice, String> brandColumn = (TableColumn<ProductQuantityPrice, String>) item_table.getColumns().get(2);
		TableColumn<ProductQuantityPrice, Float> priceColumn = (TableColumn<ProductQuantityPrice, Float>) item_table.getColumns().get(3);
		TableColumn<ProductQuantityPrice, Integer> quantityColumn = (TableColumn<ProductQuantityPrice, Integer>) item_table.getColumns().get(4);
		
		nameColumn.setCellValueFactory(new PropertyValueFactory<ProductQuantityPrice, String>("productName"));
		sizeColumn.setCellValueFactory(new PropertyValueFactory<ProductQuantityPrice, String>("size"));
		brandColumn.setCellValueFactory(new PropertyValueFactory<ProductQuantityPrice, String>("brand"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<ProductQuantityPrice, Float>("overallPrice"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<ProductQuantityPrice, Integer>("quantity"));
		
		 // Configure the click action for each row in the table
 		item_table.setRowFactory(rf -> {
 			TableRow<ProductQuantityPrice> row = new TableRow<>();
 			row.setOnMouseClicked(event -> {
 				ProductQuantityPrice selectedProduct = row.getItem();
 				Integer quantity = getQuantity(SessionData.store.inventory.get(selectedProduct.getUpcCode()));
 				if (quantity != null) {
 					SessionData.shoppingCart.products.put(selectedProduct.getUpcCode(), new ProductQuantityPrice(SessionData.store.inventory.get(selectedProduct.getUpcCode()).getUnitPrice(), quantity, selectedProduct));
 					initialize();
 				}
 			});
 			return row;
 			}
 		);
 		
 	
 		total.setText("$" + String.valueOf(SessionData.shoppingCart.getCost()));
 	}
	
	 private Integer getQuantity(ProductQuantityPrice product) {
	    	TextInputDialog dialog = new TextInputDialog();
	    	dialog.setTitle("Quantity Selection");
	    	dialog.setHeaderText("Please enter the item quantity to purchase");
	    	dialog.setContentText("Quantity");
	    	Optional<String> result = dialog.showAndWait();
	    	try {
	    		Integer quantity = Integer.parseInt(result.orElse(null));
	    		if ((quantity != null) && quantity > product.getQuantity()) {
	    			Alert warning = new Alert(AlertType.ERROR);
	    			warning.setHeaderText("Woah there!");
	    			warning.setContentText("The maximum number of this product which can be purchased from this store is " + product.getQuantity() + ".");
	    			warning.showAndWait();
	    			return null;
	    		} else {
	    			return quantity;
	    		}
	    	} catch (NumberFormatException e) {
	    		return null;
	    	}
	    }
	
	/**
	 * Returns the root node of the view.
	 * @return : The root node.
	 */
	public static VBox getView() {
		try {
			return FXMLLoader.load(CartViewController.class.getResource("/CartView.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
