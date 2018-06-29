package com.rainforestcommerce.rcdb.views;
import java.io.IOException;
import java.util.Arrays;

import com.rainforestcommerce.rcdb.models.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;


public class OnlineStoreViewController {

    private static VBox view;

    @FXML
    private TableView<Product> product_table;

    static {
        try {
            view = FXMLLoader.load(CustomerViewController.class.getResource("/OnlineStoreView.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize(){
        TableColumn<Product, String> product = (TableColumn<Product,String>) product_table.getColumns().get(0);
        TableColumn<Product, Integer>  weight = (TableColumn<Product,Integer>) product_table.getColumns().get(1);
        TableColumn<Product, Integer> size_column = (TableColumn<Product,Integer>) product_table.getColumns().get(2);
        TableColumn<Product, String>  brand_column = (TableColumn<Product,String>) product_table.getColumns().get(3);

        product.setCellValueFactory(new PropertyValueFactory<Product, String>("ProductName"));
        weight.setCellValueFactory(new PropertyValueFactory<Product, Integer>("Weight"));

        ObservableList<Product> products = FXCollections.observableArrayList(
                new Product(Arrays.asList(new String[] {"1", "Product1", "400", "true"} )),
                new Product(Arrays.asList(new String[] {"2", "Product2", "345534", "true"} ))
        );
        product_table.setItems(products);

    }


    public static VBox getView() {
        // TODO: Call method here to get customers from DB,
        // populate tableview with rows,
        // add click handler to each row
        return view;
    }
}