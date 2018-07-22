package com.rainforestcommerce.rcdb;

import com.rainforestcommerce.rcdb.views.View;
import com.rainforestcommerce.rcdb.controllers.ConnectionProxy;
import com.rainforestcommerce.rcdb.controllers.DataLoader;


public class RcdbApplication extends View {
	
	public static void main(String[] args) {

		ConnectionProxy.startConnection();
		System.out.println("connection established");
		DataLoader.main(null);

	    
	    // Start the view
	    launch();
	}

}
