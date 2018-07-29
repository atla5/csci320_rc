package com.rainforestcommerce.rcdb.views;

import java.io.IOException;

import com.rainforestcommerce.rcdb.models.ProductQuantityPrice;
import com.rainforestcommerce.rcdb.models.Store;
import com.rainforestcommerce.rcdb.views.ActivityManager.Activity;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class EnterpriseInsightsViewController {
	
	private static ProductQuantityPrice product1;
	private static ProductQuantityPrice product2;
	
	@FXML
	protected Text result;
	
	@FXML
	protected TableView<ProductQuantityPrice> productTable1;
	
	@FXML
	protected TableView<ProductQuantityPrice> productTable2;
	
	@FXML
	protected TableView<ProductQuantityPrice> productTable3;
	
	@FXML
	protected TableView<Store> storeTable;
	
	 @FXML
    protected void handleLogoutButtonPress(MouseEvent event) {
    	ActivityManager.start(Activity.START_SCREEN);
    }
	 
	public void initialize() {
		// TODO: populate tables, add click logic to each row 
	}

	
	public static VBox getView() {
		try {
            return FXMLLoader.load(CustomerViewController.class.getResource("/EnterpriseInsightsView.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
	}
}
