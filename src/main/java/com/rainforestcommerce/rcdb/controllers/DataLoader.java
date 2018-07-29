package com.rainforestcommerce.rcdb.controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.rainforestcommerce.rcdb.RcdbApplication.RESOURCES_DIRECTORY;

public class DataLoader {
    private static final Logger logger = Logger.getLogger( DataLoader.class.getName() );

    private static final boolean RUN_INSERTIONS_AGAINST_REAL_DB_CONNECTION = false;

    public static void main(String[] args){
        loadData();
    }

    public static boolean loadData(){
        //reset the data directory to update `dataDirectory` to its absolute path
        loadProducts();
        loadCustomers();
        loadStores();
        loadInventory();
        loadPurchases();
        loadVendors();
        loadShipments();
        return true;
    }

    public static boolean insertValuesIntoTable(String values, String tableName){
        String insertCommand = String.format("INSERT into %s VALUES %s;", tableName, values);
        System.out.println(insertCommand);
        try{
            if(!RUN_INSERTIONS_AGAINST_REAL_DB_CONNECTION){ return false; }
            Connection conn = ConnectionProxy.connect();
            PreparedStatement statement = conn.prepareStatement(insertCommand);
            statement.executeUpdate();
            return true;
        }catch(SQLException sqle){
            String firstValue = values.substring(1, values.indexOf(','));
            logger.warning(String.format("Exception loading new item '%s' into table '%s'", firstValue, tableName));
            sqle.printStackTrace();
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
            boolean online_store = data[5].equalsIgnoreCase("true");
            int quantity = random.nextInt(8); //TODO: resolve errors in sample data

            values = String.format("(%s, %s, %s, '%s', %b)", purchaseId, storeId, customerId, datePurchased, online_store);
            insertValuesIntoTable(values, "store_purchases");
            values = String.format("(%s, %s, %d)", purchaseId, productId, quantity);
            insertValuesIntoTable(values, "product_purchases");
        }
    }

    private static void loadVendors(){
        List<String[]> csvData = readCsvIntoListOfStringArrays("Vendor.csv");
        String values;
        Random random = new Random();
        for(String[] data : csvData) {
            String vendor_id = data[0];
            String vendor_name = data[1];
            String addr_number = data[2];
            String addr_street = data[3];
            String addr_city = data[4];
            String addr_state = data[5];
            String addr_zipcode = data[6];

            values = String.format("(%s, '%s', %s, '%s', '%s', '%s', %s)", vendor_id, vendor_name, addr_number, addr_street, addr_city, addr_state, addr_zipcode);
            insertValuesIntoTable(values, "vendors");

            // each vendor distributes 10 items
            int product_id; String unit_price;
            for(int i=0; i<10; i++){
                product_id = random.nextInt(151);
                unit_price = random.nextInt(5) + "." + random.nextInt(99);
                values = String.format("(%s, %s, %s)", vendor_id, product_id, unit_price);
                insertValuesIntoTable(values, "vendor_distributions");
            }
        }
    }

    private static void loadShipments(){
        List<String[]> csvData = readCsvIntoListOfStringArrays("Shipment.csv");
        String values;
        Random random = new Random();

        for(String[] data : csvData){
            String shipment_id = data[0];
            String store_id = data[1];
            String vendor_id = data[2];
            String order_date = data[3]; //TODO change to real date
            String arrival_date = data[4]; //TODO change to real data
            values = (String.format("(%s, %s, %s, '%s', '%s')", shipment_id, store_id, vendor_id, order_date, arrival_date));
            insertValuesIntoTable(values, "shipments");

            // each shipment contains 5 products
            int product_id, quantity;
            for(int i=0; i<5; i++) {
                product_id = random.nextInt(151);
                quantity = random.nextInt(350);
                values = (String.format("(%s, %d, %d)", shipment_id, product_id, quantity));
                insertValuesIntoTable(values, "shipment_contents");
            }
        }
    }


    private static List<String[]> readCsvIntoListOfStringArrays(String filename){
        List<String[]> toReturn = new ArrayList<>();
        BufferedReader br = null;
        String sampleDataDirectory = Paths.get("").toAbsolutePath().toString()+ RESOURCES_DIRECTORY + "/sample_data";

        try{
            br = new BufferedReader(new FileReader(sampleDataDirectory +"/"+filename));
            String line; boolean isHeaderLine = true;
            while((line = br.readLine()) != null){
                if(isHeaderLine){ isHeaderLine = false; continue; }

                toReturn.add(sanitizeStringForSql(line).split(","));
            }
        }catch(Exception exception){
            logger.severe("Error reading filename " + filename);
            exception.printStackTrace();
        }finally{
            try {
                if (br != null) { br.close(); }
            }catch(IOException ioe){
                logger.severe("IOException reached while closing buffer on filename " + filename);
                ioe.printStackTrace();
            }
        }
        return toReturn;
    }

    private static String sanitizeStringForSql(String inputString){
        return inputString.replaceAll("'", "").replaceAll("-"," ").replaceAll("\"","");
    }
}