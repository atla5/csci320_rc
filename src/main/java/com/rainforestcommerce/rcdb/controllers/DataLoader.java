package com.rainforestcommerce.rcdb.controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.*;

public class DataLoader {

    private static final Logger LOGGER = Logger.getLogger( StoreProxy.class.getName() );

    public static String GENERIC_INSERT_STATEMENT = "INSERT into %s VALUES %s;";

    private static String dataDirectory = "./src/main/resources/sample_data";
    private static boolean RUN_INSERTIONS_AGAINST_REAL_DB_CONNECTION = false;

    public static void main(String[] args){
        createTables();
        loadProducts();
        loadCustomers();
        loadStores();
        loadInventory();
        loadPurchases(); // store and product purchases
        //System.exit(0);
    }

    public static boolean insertValuesIntoTable(String values, String tableName){
        String insertCommand = String.format(GENERIC_INSERT_STATEMENT, tableName, values);
        System.out.println(insertCommand);
        try{
            if(!RUN_INSERTIONS_AGAINST_REAL_DB_CONNECTION){ return false; }
            Connection conn = ConnectionProxy.connect();
            PreparedStatement statement = conn.prepareStatement(insertCommand);
            statement.executeUpdate();
            return true;
        }catch(SQLException ex){
            LOGGER.log( Level.SEVERE, ex.toString(), ex );
            //String firstValue = values.substring(1, values.indexOf(','));
            //System.err.println(String.format("Exception loading new item '%s' into table '%s'", firstValue, tableName));
            //sqle.printStackTrace();
            return false;
        }
    }

    private static void loadProducts(){
        List<String[]> csvData = readCsvIntoListOfStringArrays("Product.csv");
        String values;
        for(String[] data : csvData){
            String upcCode = data[0];
            String productName = data[1];
            String weight = data[2];
            String brand = data[3];

            values = String.format("(%s, '%s', %s, '%s')", upcCode, productName, weight, brand);
            insertValuesIntoTable(values, "products");
        }
    }

    private static void loadCustomers(){
        List<String[]> csvData = readCsvIntoListOfStringArrays("Customer.csv");
        String values;
        for(String[] data : csvData){
            String customerId = data[0];
            String customerName = data[1];
            String birthDate = data[2]; //TODO update to real Date
            boolean isMale = data[3].equalsIgnoreCase("male");
            String ethnicity = data[4]; //TODO  set ethnicity values
            String phone = data[5]; //TODO use 'like' in phone number check with digits-only
            int purchasePoints = Integer.parseInt(data[6]);

            values = String.format("(%s, '%s', '%s', %b, '%s', '%s', %d)",
                    customerId, customerName, birthDate, isMale, ethnicity, phone, purchasePoints);
            insertValuesIntoTable(values, "customers");
        }
    }

    private static void loadStores(){
        List<String[]> csvData = readCsvIntoListOfStringArrays("Store.csv");
        String values;
        for(String[] data : csvData){
            String storeId = data[0];
            String storeName = data[1];
            String openingTime = data[2]; //TODO: update to real Date
            String closingTime = data[3]; //TODO: update to real Date and add time comparison check
            String addrCity = data[4];
            String addrState = data[5];
            String addrZip = data[6];
            String addrStreet = data[7];
            String addrNum = data[8];

            values = String.format("(%s, '%s', '%s', '%s', %s, '%s', '%s', '%s', '%s')",
                    storeId, storeName, openingTime, closingTime, addrNum, addrStreet, addrCity, addrState, addrZip
            );
            insertValuesIntoTable(values, "stores");
        }
    }

    private static void loadInventory(){
        List<String[]> csvData = readCsvIntoListOfStringArrays("Store_Inventory.csv");
        String values;
        Random random = new Random();
        for(String[] data : csvData){
            String storeId = "" + random.nextInt(1000); //TODO: resolve errors in sample data
            String productId = "" + random.nextInt(150); //TODO: resolve errors in sample data
            float unitPrice = Integer.parseInt(data[2])/(100*1.0f); //TODO: determine fate of this variable
            int quantity = Integer.parseInt(data[3]);

            values = String.format("(%s, %s, %s, %d)", storeId, productId, unitPrice, quantity);
            insertValuesIntoTable(values, "store_inventory");
        }
    }

    private static void loadPurchases(){
        List<String[]> csvData = readCsvIntoListOfStringArrays("Purchase.csv");
        String values;
        Random random = new Random();
        for(String[] data : csvData){
            String purchaseId = data[0];
            String datePurchased = data[1];
            String storeId = data[3];
            int customerId = random.nextInt(150); //TODO: resolve errors in sample data
            String productId = "" + random.nextInt(150); //TODO: resolve errors in sample data
            boolean online = data[5].equalsIgnoreCase("true");
            int quantity = random.nextInt(8); //TODO: resolve errors in sample data

            values = String.format("(%s, %s, %s, '%s', %b)", purchaseId, storeId, customerId, datePurchased, online);
            insertValuesIntoTable(values, "store_purchases");
            values = String.format("(%s, %s, %d)", purchaseId, productId, quantity);
            insertValuesIntoTable(values, "product_purchases");
        }
    }

    private static List<String[]> readCsvIntoListOfStringArrays(String filename){
        List<String[]> toReturn = new ArrayList<>();
        BufferedReader br = null;

        try{
            br = new BufferedReader(new FileReader(dataDirectory+"/"+filename));
            String line; boolean isHeaderLine = true;
            while((line = br.readLine()) != null){
                if(isHeaderLine){ isHeaderLine = false; continue; }

                toReturn.add(sanitizeStringForSql(line).split(","));
            }
        }catch(Exception exception){
            LOGGER.log( Level.SEVERE, exception.toString(), exception );
            System.err.println(String.format("Error reading filename %s", filename));
            exception.printStackTrace();
        }finally{
            try {
                if (br != null) { br.close(); }
            }catch(IOException ioe){
                LOGGER.log( Level.SEVERE, ioe.toString(), ioe );
                ioe.printStackTrace();
            }
        }
        return toReturn;
    }

    private static String sanitizeStringForSql(String inputString){
        return inputString.replaceAll("'", "").replaceAll("-"," ").replaceAll("\"","");
    }

    private static String stringArrayToPrintString(String[] toPrint){
        String printString = "";
        if(toPrint != null && toPrint.length >= 0){
            for (String token : toPrint) { printString += token + "\t"; }
        }
        return printString;
    }

    private static void createTables(){
        try{
            Connection conn = ConnectionProxy.connect();
            String statement = "CREATE TABLE product ( upc_code LONG PRIMARY KEY, product_name VARCHAR(255) NOT NULL, weight INT, brand_name VARCHAR(255)); CREATE TABLE stores ( store_id LONG PRIMARY KEY, store_name VARCHAR(255) NOT NULL, opening_time TIME, closing_time TIME, addr_num INT, addr_street VARCHAR(255), addr_city VARCHAR(255), addr_state VARCHAR(255), addr_zipcode INT, check(closing_time > opening_time));CREATE TABLE store_inventory(store_id LONG, product_id LONG, unit_price DECIMAL(15,2), quantity INT PRIMARY KEY (store_id, product_id), FOREIGN KEY (store_id) REFERENCES stores(store_id), FOREIGN KEY (product_id) REFERENCES products(upc_code), check(unit_price >= 0), check(quantity >= 0)); CREATE TABLE customers (account_number LONG PRIMARY KEY, cust_name VARCHAR(100), birth_date DATETIME, male BOOLEAN, ethnicity VARCHAR(20), phone_number INT, accumulated_points INT, check(birth_date > 1900), check(birth_date < 2016), check(accumulated_points >= 0), ); CREATE TABLE store_purchases (purchase_id LONG PRIMARY KEY, store_id LONG, account_number LONG, date DATE NOT NULL, total_price DECIMAL(15,2) NOT NULL, online BOOLEAN NOT NULL, FOREIGN KEY (store_id) REFERENCES stores(store_id), FOREIGN KEY (account_number) REFERENCES customers(account_number), check(date <= CURDATE()), check(total_price > 0)); CREATE TABLE product_purchases (purchase_id IDENTITY, product_id IDENTITY, quantity INT(255) NOT NULL, PRIMARY KEY (purchase_id, product_id), FOREIGN KEY (purchase_id) REFERENCES store_purchases(purchase_id), FOREIGN KEY (product_id) REFERENCES (product_id), check(quantity > 0)); CREATE TABLE shipments (shipment_id LONG PRIMARY KEY, store_id LONG NOT NULL, vendor_id LONG NOT NULL, order_date DATE, arrival_date DATE, FOREIGN KEY (store_id) REFERENCES store(store_id), FOREIGN KEY (vendor_id)  REFERENCES vendor(vendor_id), check(arrival_date >= order_date)); CREATE TABLE shipment_contents(shipment_id LONG, product_id LONG, quantity INT, PRIMARY KEY (shipment_id, product_id)FOREIGN KEY (shipment_id) REFERENCES shipments(shipment_id), FOREIGN KEY (product_id) REFERENCES products(upc_code), check(quantity >=0)); CREATE TABLE vendor(vendor_id LONG PRIMARY KEY, vendor_name VARCHAR(255) NOT NULL, addr_num INT, addr_street VARCHAR(255), addr_city VARCHAR(255), addr_state VARCHAR(255), addr_zipcode INT, ); CREATE TABLE vendor_distribution(vendor_id LONG, product_id LONG, unit_price DECIMAL(15,2), PRIMARY KEY( vendor_id, product_id), FOREIGN KEY (vendor_id) REFERENCES vendors(vendor_id), FOREIGN KEY (product_id) REFERENCES products(upc_code), check(unit_price >= 0));";
            conn.createStatement().execute(statement);
            conn.close();
        } catch(Exception ex){
            LOGGER.log( Level.SEVERE, ex.toString(), ex );
        }
    }
}
