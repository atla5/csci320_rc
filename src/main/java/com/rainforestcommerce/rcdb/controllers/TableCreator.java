package com.rainforestcommerce.rcdb.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

import static com.rainforestcommerce.rcdb.RcdbApplication.RESOURCES_DIRECTORY;

public class TableCreator {
    private static final Logger logger = Logger.getLogger( TableCreator.class.getName() );
    private static final boolean DROP_AFTER_CREATING_IN_MAIN = false;

    public static boolean RUN_SQL_AGAINST_REAL_DB_CONNECTION = true;

    public static void main(String[] args){
        if(RUN_SQL_AGAINST_REAL_DB_CONNECTION){
            ConnectionProxy.startConnection();
        }

        dropAllTables();
        createTables();
        createTriggers();

        if(DROP_AFTER_CREATING_IN_MAIN){
            dropAllTables();
        }
    }

    public static boolean createTables(){
        String createTablesSqlPathString = Paths.get("").toAbsolutePath().toString()+ RESOURCES_DIRECTORY + "/create_tables.sql";
        logger.info("creating tables using following script: " + createTablesSqlPathString + " ...");
        try{
            Path createTablesSqlPath = Paths.get(createTablesSqlPathString);
            String statement = new String(Files.readAllBytes(createTablesSqlPath));
            if(RUN_SQL_AGAINST_REAL_DB_CONNECTION) {
                Connection conn = ConnectionProxy.connect();
                conn.createStatement().execute(statement);
                conn.close();
            }
            logger.info("table creation successful.");
            return true;
        } catch(Exception ex){
            logger.severe("ERROR creating tables: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    public static void createTriggers(){
        String triggerStatement = UpdateStoreInventoryOnShipmentTrigger.update_store_inventory_on_shipment_trigger_sql;
        logger.info("adding trigger to database for 'UpdatingStoreInventoryOnShipments'");
        try {
            Connection conn = ConnectionProxy.connect();
            conn.createStatement().execute(triggerStatement);
            conn.close();
        }catch(SQLException sqle){
            logger.warning("ERROR adding triggers!");
            sqle.printStackTrace();
        }
    }

    public static void dropAllTables(){
        String[] tables = { "products", "customers",
                "stores", "store_inventory", "store_purchases","product_purchases",
                "vendors","vendor_distributions","shipments","shipment_contents"
        };
        Connection conn = ConnectionProxy.connect();
        logger.info("dropping tables...");
        for(String tableName : tables){
            String dropTableCommand = "DROP TABLE " + tableName + ";";
            if(!RUN_SQL_AGAINST_REAL_DB_CONNECTION){
                continue;
            }
            try{
                PreparedStatement statement = conn.prepareStatement(dropTableCommand);
                statement.execute();
            }catch(SQLException sqle){
                logger.warning("ERROR dropping table '" + tableName + "'!");
                sqle.printStackTrace();
            }
        }
        logger.info("tables dropped successfully.");
        try{ conn.close(); }
        catch(SQLException sqle){
            logger.severe("ERROR closing connection used to drop tables");
            sqle.printStackTrace();
        }
    }
}
