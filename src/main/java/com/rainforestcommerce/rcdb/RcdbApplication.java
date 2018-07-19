package com.rainforestcommerce.rcdb;
import java.util.ArrayList;

import com.rainforestcommerce.rcdb.views.View;

import com.rainforestcommerce.rcdb.controllers.ConnectionProxy;
import com.rainforestcommerce.rcdb.controllers.StoreProxy;
import com.rainforestcommerce.rcdb.models.Store;


public class RcdbApplication extends View {
	
	public static void main(String[] args) {

		ConnectionProxy.startConnection();
		System.out.println("connection established");
		// TODO = table creation logic goes here
		// Dataloader.main(null);
		
		// Testing purposes - TODO remove
		//StoreProxy.createStores();
	    ArrayList<Store> stores = StoreProxy.getStores();
	    for (Store store : stores) {
	    	System.out.println(store.getName());
	    }
	    
	    // Start the view
	    launch();
	}
}
