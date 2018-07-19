package com.rainforestcommerce.rcdb;
import java.util.ArrayList;

import com.rainforestcommerce.rcdb.views.View;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.rainforestcommerce.rcdb.controllers.ConnectionProxy;
import com.rainforestcommerce.rcdb.controllers.StoreProxy;
import com.rainforestcommerce.rcdb.models.Store;

//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;

import com.rainforestcommerce.rcdb.views.View;

import javafx.stage.Stage;

public class RcdbApplication extends View {
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		super.start(primaryStage);
		
	}

	//private Connection connection;

	public static void main(String[] args) {

		RcdbApplication rcdb = new RcdbApplication();

		//try {
		//Class.forName("org.h2.Driver");
		ConnectionProxy.startConnection();
		System.out.println("connection established");
		//StoreProxy.createStores();
	    ArrayList<Store> stores = StoreProxy.getStores();
	    for (Store store : stores) {
	    	System.out.println(store.getName());
	    }
	    launch();
		/*} catch(ClassNotFoundException cnf){
			System.err.println("Error with h2 configuration. 'org.h2.Driver' not found.");
			cnf.printStackTrace();
			System.exit(1);
		} catch(SQLException sql){
			System.err.println("Error with sql connection.");
			sql.printStackTrace();
			System.exit(1);
		}*/
	}

	/*public Connection getConnection() {
		return connection;
	}

	public void closeConnection() throws SQLException{
		this.connection.close();
	}*/

}
