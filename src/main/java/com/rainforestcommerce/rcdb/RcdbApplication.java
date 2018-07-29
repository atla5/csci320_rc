package com.rainforestcommerce.rcdb;

import com.rainforestcommerce.rcdb.controllers.TableCreator;
import com.rainforestcommerce.rcdb.views.View;
import com.rainforestcommerce.rcdb.controllers.ConnectionProxy;

import java.util.logging.Logger;

import static com.rainforestcommerce.rcdb.controllers.DataLoader.loadData;
import static com.rainforestcommerce.rcdb.controllers.TableCreator.createTables;


public class RcdbApplication extends View {
	private static final Logger logger = Logger.getLogger( RcdbApplication.class.getName() );
	public static String RESOURCES_DIRECTORY = "/src/main/resources";

	private static final boolean CREATE_TABLES_ON_STARTUP = false;
	private static final boolean RUN_LOADERS_ON_STARTUP = false;
	private static final boolean LAUNCH_UI_ON_STARTUP = false;
	private static final boolean DROP_ALL_TABLES_ON_CLOSE = false;
	
	public static void main(String[] args) {

		ConnectionProxy.startConnection();
		logger.info("initial connection established");

		if(CREATE_TABLES_ON_STARTUP){
			boolean tableCreationSuccessful = createTables();
			if(!tableCreationSuccessful){
				logger.severe("ERROR creating tables. Exiting with error status...");
				System.exit(1);
			}
		}
		if(RUN_LOADERS_ON_STARTUP) {
			boolean loadDataSuccessful = loadData();
			if(!loadDataSuccessful){
				logger.severe("ERROR loading data. Exiting with error status...");
			}
		}
	    
	    // Start the view
		if(LAUNCH_UI_ON_STARTUP) {
			launch();
		}
	}

	@Override
	public void stop(){
		logger.info("Exiting application...");
		if(DROP_ALL_TABLES_ON_CLOSE) {
			logger.info("Dropping all tables in the database");
			TableCreator.dropAllTables();
		}
	}

}
