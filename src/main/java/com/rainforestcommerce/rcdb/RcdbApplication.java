package com.rainforestcommerce.rcdb;
import java.sql.Connection;
import java.util.ArrayList;

import com.rainforestcommerce.rcdb.controllers.DataLoader;
import com.rainforestcommerce.rcdb.views.View;
import com.rainforestcommerce.rcdb.controllers.ConnectionProxy;
import com.rainforestcommerce.rcdb.controllers.StoreProxy;
import com.rainforestcommerce.rcdb.models.Store;


public class RcdbApplication extends View {
	
	public static void main(String[] args) {

		ConnectionProxy.startConnection();
		System.out.println("connection established");

		boolean tableCreationSuccessful = createTables();
		if(!tableCreationSuccessful){
			System.exit(1);
		}

		DataLoader.loadData();

		// Testing purposes - TODO remove
		//StoreProxy.createStores();
		ArrayList<Store> stores = StoreProxy.getStores();
	    for (Store store : stores) {
	    	System.out.println(store.getName());
	    }
	    
	    // Start the view
	    launch();
	}

	private static boolean createTables(){
		String createTablesSqlFile = DataLoader.getTableCreationScriptPath();
		//TODO complete implementation
		return true;
	}

}
