package com.rainforestcommerce.rcdb.controllers;

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
    private static final boolean RUN_CREATIONS_AGAINST_REAL_DB_CONNECTION = false;
    private static final boolean DROP_AFTER_CREATING_IN_MAIN = false;

    public static void main(String[] args){
        createTables();
        if(DROP_AFTER_CREATING_IN_MAIN) {
            dropAllTables(); // to check whether tables were created to start with, run in debug mode and add a breakpoint to this line
        }
    }

    public static boolean createTables(){
        String createTablesSqlPath = Paths.get("").toAbsolutePath().toString()+ RESOURCES_DIRECTORY + "create_tables.sql";
        logger.info(createTablesSqlPath);
        try{
            String statement = "" +
                "CREATE TABLE product ( " +
                    "upc_code BIGINT PRIMARY KEY, " +
                    "product_name VARCHAR(255) NOT NULL, " +
                    "weight INT, brand_name VARCHAR(255)" +
                "); " +
                "CREATE TABLE stores ( " +
                    "store_id BIGINT PRIMARY KEY," +
                    "store_name VARCHAR(255) NOT NULL, " +
                    "opening_time TIME, " +
                    "closing_time TIME, " +
                    "addr_num INT, " +
                    "addr_street VARCHAR(255), " +
                    "addr_city VARCHAR(255), " +
                    "addr_state VARCHAR(255), " +
                    "addr_zipcode INT, " +
                    "check(closing_time > opening_time)" +
                ");" +
                "CREATE TABLE store_inventory(" +
                    "store_id BIGINT, " +
                    "product_id BIGINT, " +
                    "unit_price DECIMAL(15,2), " +
                    "quantity INT, " +
                    "PRIMARY KEY(store_id, product_id), " +
                    "FOREIGN KEY (store_id) REFERENCES stores(store_id), " +
                    "FOREIGN KEY (product_id) REFERENCES products(upc_code), " +
                    "check(unit_price >= 0), check(quantity >= 0)" +
                "); " +
                "CREATE TABLE customers (" +
                    "account_number BIGINT PRIMARY KEY, " +
                    "cust_name VARCHAR(100), " +
                    "birth_date DATETIME, " +
                    "male BOOLEAN, " +
                    "ethnicity VARCHAR(20), " +
                    "phone_number INT, " +
                    "accumulated_points INT, " +
                    "check(birth_date > 1900), " +
                    "check(birth_date < 2016), " +
                    "check(accumulated_points >= 0) " +
                "); " +
                "CREATE TABLE store_purchases (" +
                    "purchase_id BIGINT PRIMARY KEY, " +
                    "store_id BIGINT, " +
                    "account_number BIGINT, " +
                    "date DATE NOT NULL, " +
                    "total_price DECIMAL(15,2) NOT NULL, " +
                    "online BOOLEAN NOT NULL, " +
                    "FOREIGN KEY (store_id) REFERENCES stores(store_id), " +
                    "FOREIGN KEY (account_number) REFERENCES customers(account_number), " +
                    "check(date <= CURDATE()), " +
                    "check(total_price > 0)" +
                "); " +
                "CREATE TABLE product_purchases (" +
                    "purchase_id IDENTITY, " +
                    "product_id IDENTITY, " +
                    "quantity INT(255) NOT NULL, " +
                    "PRIMARY KEY (purchase_id, product_id), " +
                    "FOREIGN KEY (purchase_id) REFERENCES store_purchases(purchase_id), " +
                    "FOREIGN KEY (product_id) REFERENCES (product_id), " +
                    "check(quantity > 0)" +
                "); " +
                "CREATE TABLE shipments (" +
                    "shipment_id BIGINT PRIMARY KEY, " +
                    "store_id BIGINT NOT NULL, " +
                    "vendor_id BIGINT NOT NULL, " +
                    "order_date DATE, " +
                    "arrival_date DATE, " +
                    "FOREIGN KEY (store_id) REFERENCES store(store_id), " +
                    "FOREIGN KEY (vendor_id) REFERENCES vendor(vendor_id), " +
                    "check(arrival_date >= order_date)" +
                "); " +
                "CREATE TABLE shipment_contents(" +
                    "shipment_id BIGINT, " +
                    "product_id BIGINT, " +
                    "quantity INT, " +
                    "PRIMARY KEY (shipment_id, product_id)," +
                    "FOREIGN KEY (shipment_id) REFERENCES shipments(shipment_id), " +
                    "FOREIGN KEY (product_id) REFERENCES products(upc_code), " +
                    "check(quantity >=0)" +
                "); " +
                "CREATE TABLE vendor(" +
                    "vendor_id BIGINT PRIMARY KEY, " +
                    "vendor_name VARCHAR(255) NOT NULL, " +
                    "addr_num INT, " +
                    "addr_street VARCHAR(255), " +
                    "addr_city VARCHAR(255), " +
                    "addr_state VARCHAR(255), " +
                    "addr_zipcode INT" +
                "); " +
                "CREATE TABLE vendor_distribution(" +
                    "vendor_id BIGINT, " +
                    "product_id BIGINT, " +
                    "unit_price DECIMAL(15,2), " +
                    "PRIMARY KEY(vendor_id, product_id), " +
                    "FOREIGN KEY (vendor_id) REFERENCES vendors(vendor_id), " +
                    "FOREIGN KEY (product_id) REFERENCES products(upc_code), " +
                    "check(unit_price >= 0)" +
                ");";
            System.out.println(statement);
            if(RUN_CREATIONS_AGAINST_REAL_DB_CONNECTION) {
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
            try{
                PreparedStatement statement = conn.prepareStatement(dropTableCommand);
                statement.executeUpdate();
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
