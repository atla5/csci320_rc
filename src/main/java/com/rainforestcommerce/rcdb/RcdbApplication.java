package com.rainforestcommerce.rcdb;

import com.rainforestcommerce.rcdb.views.View;
import com.rainforestcommerce.rcdb.controllers.ConnectionProxy;

import static com.rainforestcommerce.rcdb.controllers.DataLoader.loadData;
import static com.rainforestcommerce.rcdb.controllers.TableCreator.createTables;


public class RcdbApplication extends View {
	public static String RESOURCES_DIRECTORY = "/src/main/resources";

	private static final boolean CREATE_TABLES_ON_STARTUP = false;
	private static final boolean RUN_LOADERS_ON_STARTUP = false;
	private static final boolean LAUNCH_UI_ON_STARTUP = false;
	
	public static void main(String[] args) {

		ConnectionProxy.startConnection();
		System.out.println("initial connection established");

		if(CREATE_TABLES_ON_STARTUP){
			boolean tableCreationSuccessful = createTables();
			if(!tableCreationSuccessful){
				System.err.println("ERROR creating tables. Exiting with error status...");
				System.exit(1);
			}
		}
		if(RUN_LOADERS_ON_STARTUP) {
			boolean loadDataSuccessful = loadData();
			if(!loadDataSuccessful){
				System.err.println("ERROR loading data. Exiting with error status...");
			}
		}
	    
	    // Start the view
		if(LAUNCH_UI_ON_STARTUP) {
			launch();
		}
	}

}
