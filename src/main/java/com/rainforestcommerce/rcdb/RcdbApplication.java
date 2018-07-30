package com.rainforestcommerce.rcdb;

import com.rainforestcommerce.rcdb.controllers.DataLoader;
import com.rainforestcommerce.rcdb.controllers.TableCreator;
import com.rainforestcommerce.rcdb.views.View;
import com.rainforestcommerce.rcdb.controllers.ConnectionProxy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import static com.rainforestcommerce.rcdb.controllers.DataLoader.loadData;
import static com.rainforestcommerce.rcdb.controllers.TableCreator.createTables;
import static com.rainforestcommerce.rcdb.controllers.TableCreator.dropAllTables;


public class RcdbApplication extends View {
	private static final Logger logger = Logger.getLogger( RcdbApplication.class.getName() );
	private static final boolean LAUNCH_UI_ON_STARTUP = false;

	public static String RESOURCES_DIRECTORY = "/src/main/resources";

	private static final Boolean COMPLETELY_RESET_DB_EACH_RUN = true; //change to true when running for real

	private static boolean TEST_TABLE_CREATION_SUCCESSFUL = true;
	private static boolean RECREATE_TABLES_ON_STARTUP = true;
	private static boolean RUN_LOADERS_ON_STARTUP = false;
	private static boolean DROP_ALL_TABLES_ON_CLOSE = false;
	
	public static void main(String[] args) {
		optionallyOverrideSettings();

		ConnectionProxy.startConnection();
		logger.info("initial connection established");

		if(RECREATE_TABLES_ON_STARTUP){
			dropAllTables();
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

		if(TEST_TABLE_CREATION_SUCCESSFUL){
			try{
				testBasicQuery();
				logger.info("Table creation successful");
			}
			catch(SQLException sqle){
				logger.severe("Table creation failed");
				System.exit(1);
			}
		}
	    
	    // Start the view
		if(LAUNCH_UI_ON_STARTUP) {
			launch();
		}
	}

	private static void optionallyOverrideSettings(){
		if(COMPLETELY_RESET_DB_EACH_RUN != null && COMPLETELY_RESET_DB_EACH_RUN){
			RECREATE_TABLES_ON_STARTUP = true;
			RUN_LOADERS_ON_STARTUP = true;
			DROP_ALL_TABLES_ON_CLOSE = true;
			DataLoader.RUN_INSERTIONS_AGAINST_REAL_DB_CONNECTION = true;
			TableCreator.RUN_SQL_AGAINST_REAL_DB_CONNECTION = true;
		}
	}

	@Override
	public void stop(){
		logger.info("Exiting application...");
		if(DROP_ALL_TABLES_ON_CLOSE) {
			logger.info("Dropping all tables in the database");
			TableCreator.dropAllTables();
		}
		ConnectionProxy.endConnection();
	}

	private static final boolean testBasicQuery() throws SQLException{
		Connection conn = ConnectionProxy.connect();
		try {
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM products;");
			ResultSet rs = statement.executeQuery();
			logger.info(rs.toString());

		}catch(SQLException sqle){
			logger.severe("Basic SELECT didn't work on PRODUCTS table!");
			sqle.printStackTrace();
			return false;
		}finally{
			conn.close();
		}
		return true;
	}

}
