package com.rainforestcommerce.rcdb.views;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

public class OnlineStoreViewController {

    private static VBox view;

    static {
        try {
            view = FXMLLoader.load(CustomerViewController.class.getResource("/OnlineStoreView.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static VBox getView() {
        // TODO: Call method here to get customers from DB,
        // populate tableview with rows,
        // add click handler to each row
        return view;
    }
}