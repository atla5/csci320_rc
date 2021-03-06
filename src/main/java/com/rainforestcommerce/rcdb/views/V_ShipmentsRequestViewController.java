package com.rainforestcommerce.rcdb.views;
import com.rainforestcommerce.rcdb.models.Shipment;
import com.rainforestcommerce.rcdb.views.ActivityManager.Activity;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import javax.xml.soap.Text;
import java.io.IOException;
import java.util.Date;

/**
 * The controller for the Vendor Shipment Requests View.
 * Responsible for loading the view's FXML, configuring the view's table
 * to display Shipment objects, and populating the table with the
 * Shipment objects retrieved from the database.
 *
 * @author Abdullah Al Hamoud <aaa1008@g.rit.edu>
 *
 */
public class V_ShipmentsRequestViewController {


    // The table node which is injected by FXMLLoader
    @FXML
    private TableView<Shipment> request_table;

    @FXML
    private javafx.scene.text.Text title;

    /**
     * Sets up the Customer table to properly display Customer objects.
     * This method is called automatically by FXMLLoader.
     */
    public void initialize() {
        title.setText(SessionData.vendor.getName());
        request_table.setItems(FXCollections.observableArrayList(SessionData.vendor.shipments));
        // Get the table columns
        TableColumn<Shipment, Integer> ID_Column = (TableColumn<Shipment, Integer>) request_table.getColumns().get(0);
        TableColumn<Shipment, String> Store_Column = (TableColumn<Shipment, String>) request_table.getColumns().get(1);
        
        // Configure the columns to accept the correct properties of Shipment
        ID_Column.setCellValueFactory(new PropertyValueFactory<Shipment, Integer>("ID"));
        Store_Column.setCellValueFactory(new PropertyValueFactory<Shipment, String>("Store"));
        

        // Configure the click action for each row in the table
        request_table.setRowFactory(rf -> {
                    TableRow<Shipment> row = new TableRow<>();
                    row.setOnMouseClicked(event -> {
                        Shipment shipment = row.getItem();
                        SessionData.shipment = shipment;
                        ActivityManager.start(Activity.VENDOR_SHIPMENT_DETAILS);
                    });
                    return row;
                }
        );

        // TEST CODE - remove when DB access is implemented

        // End test code
    }

    /**
     * Returns the root node of the view.
     * @return : The root node.
     */
    public static VBox getView() {
        try {
            return  FXMLLoader.load(StoreSelectionViewController.class.getResource("/VendorShipmentView.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @FXML
    protected void handleLogoutButtonClicked(MouseEvent event){ActivityManager.start(Activity.START_SCREEN);}

}
