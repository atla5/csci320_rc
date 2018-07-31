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
import static com.rainforestcommerce.rcdb.controllers.TableCreator.*;


public class RcdbApplication extends View {
	private static final Logger logger = Logger.getLogger( RcdbApplication.class.getName() );

	public static String RESOURCES_DIRECTORY = "/src/main/resources";
	public static boolean USE_TRANSIENT_PRODUCTION_DB = false;

	private static final Boolean RUN_AS_FINAL_SUBMISSION = true; //change to true when running for real
	private static final boolean LAUNCH_UI_ON_STARTUP = true;

	private static boolean RESET_DB_THEN_EXIT = false;
	private static boolean RECREATE_TABLES_ON_STARTUP = false;
	private static boolean RUN_LOADERS_ON_STARTUP = false;
	private static boolean DROP_ALL_TABLES_ON_CLOSE = false;
	
	public static void main(String[] args) {
		optionallyOverrideSettings();
		ConnectionProxy.startConnection();

		if(RESET_DB_THEN_EXIT){
			logger.info("resetting the db then exiting with it loaded...");
			int status = resetDatabase() ? 0 : 1;
			System.exit(status);
		}

		if(RECREATE_TABLES_ON_STARTUP){
			dropAllTables();
			boolean tableCreationSuccessful = createTables();
			if(!tableCreationSuccessful){
				logger.severe("ERROR creating tables. Exiting with error status...");
				System.exit(1);
			}
			createTriggers();
		}
		if(RUN_LOADERS_ON_STARTUP) {
			boolean loadDataSuccessful = loadData();
			if(!loadDataSuccessful){
				logger.severe("ERROR loading data. Exiting with error status...");
				System.exit(1);
			}
		}

		if(testBasicQuerySuccessful()){
			logger.info("Table creation and population successful");
		}
		else{
			logger.severe("Table creation failed! Exiting...");
			System.exit(1);
		}

		if(LAUNCH_UI_ON_STARTUP) {
			logger.info("Launching UI...");
			launch();
		}
	}

	private static void optionallyOverrideSettings(){
		if(RUN_AS_FINAL_SUBMISSION != null && RUN_AS_FINAL_SUBMISSION){
			USE_TRANSIENT_PRODUCTION_DB = true;
			RESET_DB_THEN_EXIT = false;
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
			logger.info("Dropping all tables in the database...");
			TableCreator.dropAllTables();
		}
		ConnectionProxy.endConnection();
	}

	private static boolean testBasicQuerySuccessful(){
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
			try {
				if (conn != null) {conn.close(); }
			}catch(SQLException sqle){
				return false;
			}
		}
		return true;
	}

	private static boolean resetDatabase(){
		dropAllTables();
		createTables();
		loadData();
		return testBasicQuerySuccessful();
	}

}
