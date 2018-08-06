package com.rainforestcommerce.rcdb.views;
import com.rainforestcommerce.rcdb.controllers.StoreProxy;
import com.rainforestcommerce.rcdb.models.*;
import com.rainforestcommerce.rcdb.views.ActivityManager.Activity;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

/**
 * The controller for the Store Selection View.
 * Responsible for loading the view's FXML, configuring the view's table
 * to display Store objects, and populating the table with the
 * Store objects retrieved from the database.
 *
 * @author Abdullah Al Hamoud <aaa1008@g.rit.edu>
 *
 */
public class StoreSelectionViewController {

    // The table node which is injected by FXMLLoader
    @FXML
    private TableView<Store> store_table;
    
    @FXML
	private Button logout_btn;
    
    @FXML
    protected void handleLogoutButtonPress(MouseEvent event) {
    	ActivityManager.start(Activity.START_SCREEN);
    }

    /**
     * Sets up the Customer table to properly display Customer objects.
     * This method is called automatically by FXMLLoader.
     */
    public void initialize() {

        // Get the table columns
        TableColumn<Store, String> nameColumn = (TableColumn<Store, String>) store_table.getColumns().get(0);

        // Configure the columns to accept the correct properties of Customer
        nameColumn.setCellValueFactory(new PropertyValueFactory<Store, String>("Name"));

        // Configure the click action for each row in the table
        store_table.setRowFactory(rf -> {
                    TableRow<Store> row = new TableRow<>();
                    row.setOnMouseClicked(event -> {
                        Store store = row.getItem();
                        SessionData.store = store;
                        switch (ActivityManager.user) {
                        case CUSTOMER:
                        	ActivityManager.start(Activity.PRODUCTS);
                        	break;
                        case STORE_MANAGER:
                        	ActivityManager.start(Activity.STORE_MANAGEMENT);
                        	break;
                        }
                        
                    });
                    return row;
                }
        );

        store_table.setItems(FXCollections.observableArrayList(StoreProxy.getStores()));
    }

    /**
     * Returns the root node of the view.
     * @return : The root node.
     */
    public static VBox getView() {
    	try {
            return FXMLLoader.load(StoreSelectionViewController.class.getResource("/StoreSelectionView.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
