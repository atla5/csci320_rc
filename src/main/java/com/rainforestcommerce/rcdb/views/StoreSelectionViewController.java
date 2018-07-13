package com.rainforestcommerce.rcdb.views;
import com.rainforestcommerce.rcdb.models.Store;
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
import java.text.ParseException;
import java.util.Arrays;

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

    // The root node of the view
    private static VBox view;

    // Loads the view FXML
    static {
        try {
            view = FXMLLoader.load(StoreSelectionViewController.class.getResource("/StoreSelectionView.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // The table node which is injected by FXMLLoader
    @FXML
    private TableView<Store> store_table;

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
                    });
                    return row;
                }
        );

        // TEST CODE - remove when DB access is implemented
        try {
            ObservableList<Store> stores = FXCollections.observableArrayList(
                    new Store(Arrays.asList(new String[] {"1", "name", "8:00", "23:59", null, null, null, null, null} )),
                    new Store(Arrays.asList(new String[] {"2", "name 2", "8:00", "23:59", null, null, null, null, null} ))
            );
            store_table.setItems(stores);
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
