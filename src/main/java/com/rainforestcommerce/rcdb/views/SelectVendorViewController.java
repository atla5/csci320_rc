package com.rainforestcommerce.rcdb.views;
import com.rainforestcommerce.rcdb.controllers.VendorProxy;
import com.rainforestcommerce.rcdb.models.Shipment;
import com.rainforestcommerce.rcdb.models.Vendor;
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
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

/**
 * The controller for the Vendor Selection View.
 * Responsible for loading the view's FXML, configuring the view's table
 * to display Store objects, and populating the table with the
 * Vendor objects retrieved from the database.
 *
 * @author Abdullah Al Hamoud <aaa1008@g.rit.edu>
 *
 */
public class SelectVendorViewController {

    // The root node of the view
    private static VBox view;

    // Loads the view FXML
    static {
        try {
            view = FXMLLoader.load(StoreSelectionViewController.class.getResource("/VendorSelectionView.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // The table node which is injected by FXMLLoader
    @FXML
    private TableView<Vendor> vendor_table;

    /**
     * Sets up the Customer table to properly display Customer objects.
     * This method is called automatically by FXMLLoader.
     */
    public void initialize() {

        // Get the table columns
        TableColumn<Vendor, String> nameColumn = (TableColumn<Vendor, String>) vendor_table.getColumns().get(0);

        // Configure the columns to accept the correct properties of Customer
        nameColumn.setCellValueFactory(new PropertyValueFactory<Vendor, String>("Name"));

        // Configure the click action for each row in the table
        vendor_table.setRowFactory(rf -> {
                    TableRow<Vendor> row = new TableRow<>();
                    row.setOnMouseClicked(event -> {
                        Vendor vendor = row.getItem();
                        SessionData.vendor = vendor;
                        ActivityManager.start(ActivityManager.Activity.VENDOR_SHIPMENT);
                    });
                    return row;
                }
        );

        // TEST CODE - remove when DB access is implemented
            ObservableList<Vendor> vendors = FXCollections.observableArrayList(VendorProxy.getVendors());

            /*for (Vendor vendor: vendors){
                vendor.shipments= new HashMap<>();
                vendor.shipments.put(1,new Shipment(1,"store 1",new Date(2017,9,19),"Graham",3000,new Date(2017,10,1)));
                vendor.shipments.put(2,new Shipment(2,"store 2",new Date(2018,4,1),"Abdul",1500,new Date(2018,4,5)));

            }*/
            vendor_table.setItems(vendors);

        // End test code
    }

    /**
     * Returns the root node of the view.
     * @return : The root node.
     */
    public static VBox getView() {
        return view;
    }


    @FXML
    protected void handleLogoutButtonClicked(MouseEvent event){ActivityManager.start(ActivityManager.Activity.START_SCREEN);}

}
