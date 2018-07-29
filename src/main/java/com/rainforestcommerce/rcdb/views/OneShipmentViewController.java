package com.rainforestcommerce.rcdb.views;
import com.rainforestcommerce.rcdb.models.ProductQuantityPrice;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import java.io.IOException;

/**
 * The controller for the Shipment View.
 * Responsible for loading the view's FXML, configuring the view's table
 * to display Store objects, and populating the table with the
 * Shipment object information retrieved from the database.
 *
 * @author Abdullah Al Hamoud <aaa1008@g.rit.edu>
 *
 */
public class OneShipmentViewController {

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
    private TableView<ProductQuantityPrice> product_table;

    /**
     * Sets up the Customer table to properly display Customer objects.
     * This method is called automatically by FXMLLoader.
     */
    public void initialize() {

        // Get the table columns
        TableColumn<ProductQuantityPrice, String> nameColumn = (TableColumn<ProductQuantityPrice, String>) product_table.getColumns().get(0);
        TableColumn<ProductQuantityPrice, String> sizeColumn = (TableColumn<ProductQuantityPrice, String>) product_table.getColumns().get(1);
        TableColumn<ProductQuantityPrice, String> brandColumn = (TableColumn<ProductQuantityPrice, String>) product_table.getColumns().get(2);
        TableColumn<ProductQuantityPrice, Float> priceColumn = (TableColumn<ProductQuantityPrice, Float>) product_table.getColumns().get(3);
        TableColumn<ProductQuantityPrice, Integer> quantityColumn = (TableColumn<ProductQuantityPrice, Integer>) product_table.getColumns().get(4);


        // Configure the columns to accept the correct properties of Customer
        nameColumn.setCellValueFactory(new PropertyValueFactory<ProductQuantityPrice, String>("Name"));

        // Configure the click action for each row in the table
        product_table.setRowFactory(rf -> {
                    TableRow<ProductQuantityPrice> row = new TableRow<>();
                    row.setOnMouseClicked(event -> {
                        ProductQuantityPrice product = row.getItem();
                        SessionData.productQuantityPrice = product;
                    });
                    return row;
                }
        );

        // TEST CODE - remove when DB access is implemented
        ObservableList<ProductQuantityPrice> products = FXCollections.observableArrayList(
                //TODO: Test using product objects
                //new ProductQuantityPrice("Abdul"),
                //new ProductQuantityPrice("Graham")
        );
        product_table.setItems(products);

        // End test code
    }

    /**
     * Returns the root node of the view.
     * @return : The root node.
     */
    public static VBox getView() {
        return view;
    }

}
