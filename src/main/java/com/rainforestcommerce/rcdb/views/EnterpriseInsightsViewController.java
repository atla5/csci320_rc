package com.rainforestcommerce.rcdb.views;

import java.io.IOException;
import java.util.ArrayList;

import com.rainforestcommerce.rcdb.controllers.ProductProxy;
import com.rainforestcommerce.rcdb.controllers.PurchaseProxy;
import com.rainforestcommerce.rcdb.models.Product;
import com.rainforestcommerce.rcdb.controllers.StoreProxy;
import com.rainforestcommerce.rcdb.models.ProductQuantityPrice;
import com.rainforestcommerce.rcdb.models.Store;
import com.rainforestcommerce.rcdb.views.ActivityManager.Activity;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class EnterpriseInsightsViewController {
	
	private Product selectedProduct1;
	private Product selectedProduct2;
	
	@FXML
	private TextArea result;
	
	@FXML
	private TableView<Product> productTable1;
	
	@FXML
	private TableView<Product> productTable2;
	
	@FXML
	private TableView<ProductQuantityPrice> productTable3;
	
	@FXML
	private TableView<Store> storeTable;
	
	 @FXML
    protected void handleLogoutButtonPress(MouseEvent event) {
    	ActivityManager.start(Activity.START_SCREEN);
    }
	 
	public void initialize() {
		ObservableList<Product> allProducts = FXCollections.observableArrayList(ProductProxy.getAllProducts());
		productTable1.setItems(allProducts);
		productTable2.setItems(allProducts);
		
		((TableColumn<Product, String>)productTable1.getColumns().get(0)).setCellValueFactory(new PropertyValueFactory<Product, String>("productName"));
		((TableColumn<Product, String>)productTable2.getColumns().get(0)).setCellValueFactory(new PropertyValueFactory<Product, String>("productName"));
		
		productTable1.setRowFactory(rf -> {
			TableRow<Product> row = new TableRow();
			row.setOnMouseClicked(event -> {
				selectedProduct1 = row.getItem();
				showResults();
			});
			return row;
		});
		
		productTable2.setRowFactory(rf -> {
			TableRow<Product> row = new TableRow();
			row.setOnMouseClicked(event -> {
				selectedProduct2 = row.getItem();
				showResults();
			});
			return row;
		});

		TableColumn<Store, String> nameColumn = (TableColumn<Store, String>) storeTable.getColumns().get(0);
		// get the table column for the Store name

		nameColumn.setCellValueFactory(new PropertyValueFactory<Store,String>("Name"));
		// set the store names to fill the rows of the table

	 	storeTable.setItems(FXCollections.observableArrayList(StoreProxy.getStores()));
		
		storeTable.setRowFactory(rf -> {
					TableRow<Store> row = new TableRow<>();

					// set on click action to generate the products in a store and their purchase count
					row.setOnMouseClicked(event -> {
						Store store = row.getItem();

						productTable3.setItems(FXCollections.observableArrayList(store.inventory.values()));

						TableColumn<ProductQuantityPrice, String> product = (TableColumn<ProductQuantityPrice, String>) productTable3.getColumns().get(0);

						TableColumn<ProductQuantityPrice, Long> numberPurchased = (TableColumn<ProductQuantityPrice, Long>) productTable3.getColumns().get(1);


						product.setCellValueFactory(new PropertyValueFactory<ProductQuantityPrice, String>("ProductName"));
						numberPurchased.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ProductQuantityPrice, Long>, ObservableValue<Long>>() {
							@Override
							public ObservableValue<Long> call(TableColumn.CellDataFeatures<ProductQuantityPrice, Long> param) {
								return new SimpleLongProperty(PurchaseProxy.getNumPurchasesPerStore(store.getStoreId(),param.getValue().getUpcCode())).asObject();
							}
						});


					});
					return row;
				}
		);
	}
	
	private void showResults() {
		if ((selectedProduct1 != null) && (selectedProduct2 != null)) {
			long[] product1Stats = PurchaseProxy.getPurchaseStatsForProduct(selectedProduct1);
			long[] product2Stats = PurchaseProxy.getPurchaseStatsForProduct(selectedProduct2);
			result.setText("Comparison Results:\n" +
					selectedProduct1.getProductName() + 
					" has been purchased " + 
							(product1Stats[0] > product2Stats[0] ? "more times than " : product1Stats[0] < product2Stats[0] ? "fewer times than " : "the same number of times as ") + 
							selectedProduct2.getProductName() + 
							". " + selectedProduct1.getProductName() + 
							" has been purchased " + product1Stats[0] + 
							" times by " + product1Stats[1] + 
							" different customers, while " + 
							selectedProduct2.getProductName() +
							" has been purchased " + product2Stats[0] +
							" times by " + product2Stats[1] + " different customers.");
		}
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
