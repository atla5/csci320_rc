package com.rainforestcommerce.rcdb.views;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import com.rainforestcommerce.rcdb.models.Customer;
import com.rainforestcommerce.rcdb.models.Product;
import com.rainforestcommerce.rcdb.models.ProductQuantityPrice;
import com.rainforestcommerce.rcdb.models.StorePurchase;
import com.rainforestcommerce.rcdb.views.ActivityManager.Activity;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class StoreViewController {

    private static VBox view;

    static {
        try {
            view = FXMLLoader.load(CustomerViewController.class.getResource("/StoreView.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private TableView<ProductQuantityPrice> product_table;
    
    @FXML
	private Button logout_btn;
    
    @FXML
    protected void handleCartButtonPress(MouseEvent event) {
    	ActivityManager.start(Activity.CART);
    }
    
    @FXML
    protected void handleLogoutButtonPress(MouseEvent event) {
    	ActivityManager.start(Activity.START_SCREEN);
    }

    public void initialize() {
    	// Populate the product table with the items in the store's inventory
    	product_table.setItems(FXCollections.observableArrayList(SessionData.store.inventory.values()));
    	// Create the shopping cart if needed
    	if (SessionData.shoppingCart == null) {
			SessionData.shoppingCart = new StorePurchase(SessionData.store.getStoreId());
		}
        TableColumn<ProductQuantityPrice, String> product = (TableColumn<ProductQuantityPrice, String>) product_table.getColumns().get(0);
        TableColumn<ProductQuantityPrice, String> size_column = (TableColumn<ProductQuantityPrice,String>) product_table.getColumns().get(1);
        TableColumn<ProductQuantityPrice, String>  brand_column = (TableColumn<ProductQuantityPrice,String>) product_table.getColumns().get(2);
        TableColumn<ProductQuantityPrice, String> price_column = (TableColumn<ProductQuantityPrice,String>) product_table.getColumns().get(3);

        product.setCellValueFactory(new PropertyValueFactory<ProductQuantityPrice, String>("ProductName"));
        size_column.setCellValueFactory(new PropertyValueFactory<ProductQuantityPrice, String>("Size"));
        brand_column.setCellValueFactory(new PropertyValueFactory<ProductQuantityPrice, String>("Brand"));
        price_column.setCellValueFactory(new PropertyValueFactory<ProductQuantityPrice, String>("UnitPrice"));
        
        // Configure the click action for each row in the table
 		product_table.setRowFactory(rf -> {
 			TableRow<ProductQuantityPrice> row = new TableRow<>();
 			row.setOnMouseClicked(event -> {
 				ProductQuantityPrice selectedProduct = row.getItem();
 				Integer quantity = getQuantity();
 				if (quantity != null) {
 					SessionData.shoppingCart.products.put(selectedProduct.getUpcCode(), new ProductQuantityPrice(SessionData.store.inventory.get(selectedProduct.getUpcCode()).getUnitPrice(), quantity, selectedProduct));
 				}
 			});
 			return row;
 			}
 		);
    }
    
    private Integer getQuantity() {
    	TextInputDialog dialog = new TextInputDialog();
    	dialog.setTitle("Quantity Selection");
    	dialog.setHeaderText("Please enter the item quantity to purchase");
    	dialog.setContentText("Quantity");
    	Optional<String> result = dialog.showAndWait();
    	try {
    		return Integer.parseInt(result.orElse(null));
    	} catch (NumberFormatException e) {
    		return null;
    	}
    }


    public static VBox getView() {
        return view;
    }
}