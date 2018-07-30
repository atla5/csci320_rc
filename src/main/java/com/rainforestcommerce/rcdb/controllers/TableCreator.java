package com.rainforestcommerce.rcdb.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
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

        if(DROP_AFTER_CREATING_IN_MAIN){
            dropAllTables();
        }
    }

    public static boolean createTables(){
        String createTablesSqlPathString = Paths.get("").toAbsolutePath().toString()+ RESOURCES_DIRECTORY + "/create_tables.sql";
        logger.info(createTablesSqlPathString);
        try{
            Path createTablesSqlPath = Paths.get(createTablesSqlPathString);
            String statement = new String(Files.readAllBytes(createTablesSqlPath));
            System.out.println(statement);
            if(RUN_SQL_AGAINST_REAL_DB_CONNECTION) {
                Connection conn = ConnectionProxy.connect();
                conn.createStatement().execute(statement);
                conn.close();
            }
            return true;
        } catch(Exception ex){
            logger.severe("ERROR creating tables: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    public static void dropAllTables(){
        String[] tables = { "products", "customers",
                "stores", "store_inventory", "store_purchases","product_purchases",
                "vendors","vendor_distributions","shipments","shipment_contents"
        };
        Connection conn = ConnectionProxy.connect();
        for(String tableName : tables){
            String dropTableCommand = "DROP TABLE " + tableName + ";";
            logger.info(dropTableCommand);
            if(!RUN_SQL_AGAINST_REAL_DB_CONNECTION){
                continue;
            }
            try{
                PreparedStatement statement = conn.prepareStatement(dropTableCommand);
                statement.execute();
            }catch(SQLException sqle){
                logger.warning("ERROR dropping table '" + tableName);
                sqle.printStackTrace();
            }
        }
        try{ conn.close(); }
        catch(SQLException sqle){
            logger.severe("ERROR closing connection used to drop tables");
            sqle.printStackTrace();
        }
    }

    private static boolean notExisting(){
        boolean result = true;
        try{
            Connection conn = ConnectionProxy.connect();
            ResultSet rs = conn.getMetaData().getTables(null, null, "Products", null);
            result = !rs.next();
            conn.close();
        } catch(Exception ex){
            logger.log( Level.SEVERE, ex.toString(), ex );
        }
        return result;
    }
}
