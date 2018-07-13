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

    @FXML
    private TableView<Product> product_table;
    
    @FXML
    protected void handleCartButtonPress(MouseEvent event) {
    	ActivityManager.start(Activity.CART);
    }
    
    @FXML
    protected void handleLogoutButtonPress(MouseEvent event) {
    	ActivityManager.start(Activity.START_SCREEN);
    }

    static {
        try {
            view = FXMLLoader.load(CustomerViewController.class.getResource("/StoreView.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize(){
        TableColumn<Product, String> product = (TableColumn<Product,String>) product_table.getColumns().get(0);
        TableColumn<Product, Integer>  weight = (TableColumn<Product,Integer>) product_table.getColumns().get(1);
        TableColumn<Product, Integer> size_column = (TableColumn<Product,Integer>) product_table.getColumns().get(2);
        TableColumn<Product, String>  brand_column = (TableColumn<Product,String>) product_table.getColumns().get(3);
        TableColumn<Product, String> price_column = (TableColumn<Product,String>) product_table.getColumns().get(4);
        TableColumn<Product, Integer> quantity_column = (TableColumn<Product,Integer>) product_table.getColumns().get(5);

        product.setCellValueFactory(new PropertyValueFactory<Product, String>("ProductName"));
        weight.setCellValueFactory(new PropertyValueFactory<Product, Integer>("Weight"));
        
        // Configure the click action for each row in the table
 		product_table.setRowFactory(rf -> {
 			TableRow<Product> row = new TableRow<>();
 			row.setOnMouseClicked(event -> {
 				Product selectedProduct = row.getItem();
 				if (SessionData.shoppingCart == null) {
 					SessionData.shoppingCart = new StorePurchase(SessionData.store.getStoreId());
 				}
 				Integer quantity = getQuantity();
 				if (quantity != null) {
 					SessionData.shoppingCart.products.put(selectedProduct.getUpcCode(), new ProductQuantityPrice(selectedProduct.getUpcCode(), SessionData.store.inventory.get(selectedProduct.getUpcCode()).getUnitPrice(), quantity));
 				}
 			});
 			return row;
 			}
 		);

 		// TEST CODE - remove
 		
        ObservableList<Product> products = FXCollections.observableArrayList(
                new Product(Arrays.asList(new String[] {"1", "Product1", "400", "true"} )),
                new Product(Arrays.asList(new String[] {"2", "Product2", "345534", "true"} ))
        );
        product_table.setItems(products);

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