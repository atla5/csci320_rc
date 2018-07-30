package com.rainforestcommerce.rcdb.controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import static com.rainforestcommerce.rcdb.RcdbApplication.RESOURCES_DIRECTORY;

public class DataLoader {
    private static final Logger logger = Logger.getLogger( DataLoader.class.getName() );
    private static final Random random = new Random();

    public static boolean RUN_INSERTIONS_AGAINST_REAL_DB_CONNECTION = false;

    public static void main(String[] args){
        if(RUN_INSERTIONS_AGAINST_REAL_DB_CONNECTION) {
            ConnectionProxy.startConnection();
            TableCreator.dropAllTables();
            TableCreator.createTables();
        }

        loadData();
    }

    public static boolean loadData(){
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
        //logger.info(insertCommand);
        Connection conn = null;
        try{
            if(!RUN_INSERTIONS_AGAINST_REAL_DB_CONNECTION){ return true; }
            conn = ConnectionProxy.connect();
            PreparedStatement statement = conn.prepareStatement(insertCommand);
            statement.executeUpdate();
        }catch(SQLException sqle){
            String firstValue = values.substring(1, values.indexOf(','));
            logger.warning(String.format("Exception loading new item '%s' into table '%s'", firstValue, tableName));
            sqle.printStackTrace();
            return false;
        }finally{
            try {
                if(conn != null){ conn.close(); }
            }catch(SQLException sqle){
                logger.warning("unable to close connection used to: " + insertCommand);
                return false;
            }
        }
        return true;
    }

    private static void loadProducts(){
        List<String[]> csvData = readCsvIntoListOfStringArrays("Product.csv");
        int numErrantLines = 0;
        String values;
        for(String[] data : csvData){
            if(data.length != 4){ numErrantLines++; continue; }
            String upcCode = data[0];
            String productName = data[1];
            String weight = data[2];
            String brand = data[3];

            values = String.format("(%s, '%s', %s, '%s')", upcCode, productName, weight, brand);
            if(!insertValuesIntoTable(values, "products")){
                numErrantLines++;
            }
        }
        if(numErrantLines > 0){ logger.warning("Number of errant lines in PRODUCTS: " + numErrantLines );}
    }

    private static void loadCustomers(){
        List<String[]> csvData = readCsvIntoListOfStringArrays("Customer.csv");
        String values;
        int numErrantLines = 0;
        for(String[] data : csvData){
            if(data.length != 4){ numErrantLines++; continue; }
            String customerId = data[0];
            String customerName = data[1];
            String phone = data[2];
            int purchasePoints = Integer.parseInt(data[3]);

            values = String.format("(%s, '%s', '%s', %d)", customerId, customerName, phone, purchasePoints);
            if(!insertValuesIntoTable(values, "customers")){
                numErrantLines++;
            }
        }
        if(numErrantLines > 0){ logger.warning("Number of errant lines in CUSTOMERS: " + numErrantLines );}
    }

    private static void loadStores(){
        List<String[]> csvData = readCsvIntoListOfStringArrays("Store.csv");
        int numErrantLines = 0;
        String values;
        for(String[] data : csvData){
            if(data.length != 6){ numErrantLines++; continue; }
            String storeId = data[0];
            String storeName = data[1];
            String addrLine1 = data[2];
            String addrCity = data[3];
            String addrState = data[4];
            String addrZip = data[5];

            values = String.format("(%s, '%s', '%s', '%s', '%s', '%s')",
                    storeId, storeName, addrLine1, addrCity, addrState, addrZip
            );
            if(!insertValuesIntoTable(values, "stores")){
                numErrantLines++;
            }
        }
        if(numErrantLines > 0){ logger.warning("Number of errant lines in STORE: " + numErrantLines );}
    }

    private static void loadInventory(){
        List<String[]> csvData = readCsvIntoListOfStringArrays("Store_Inventory.csv");
        int numErrantLines = 0;
        String values;
        for(String[] data : csvData){
            if(data.length != 4){ numErrantLines++; continue; }
            String storeId = data[0];
            String productId = data[1];
            float unitPrice = Integer.parseInt(data[2])/(100*1.0f);
            int quantity = Integer.parseInt(data[3]);

            values = String.format("(%s, %s, %s, %d)", storeId, productId, unitPrice, quantity);
            if(!insertValuesIntoTable(values, "store_inventory")){
                numErrantLines++;
            }
        }
        if(numErrantLines > 0){ logger.warning("Number of errant lines in INVENTORY: " + numErrantLines );}
    }

    private static void loadPurchases(){
        List<String[]> csvData = readCsvIntoListOfStringArrays("Store_Purchase.csv");
        String values; int numErrantLines = 0;
        for(String[] data : csvData){
            if(data.length != 5){ numErrantLines++; continue; }
            String purchaseId = data[0];
            String storeId = data[1];
            int customerId = random.nextInt(1000); //data[2] //TODO: resolve errors in sample data (phone vs account_number)
            float totalPrice = Integer.parseInt(data[3])*(1.00f/100);
            boolean online_store = data[4].equalsIgnoreCase("true");

            values = String.format("(%s, %s, %s, %s, %b)", purchaseId, storeId, customerId, totalPrice, online_store);
            if(!insertValuesIntoTable(values, "store_purchases")){
                numErrantLines++;
            }
        }
        if(numErrantLines > 0){ logger.warning("Number of errant lines in STORE_PURCHASE: " + numErrantLines );}

        csvData.clear(); numErrantLines = 0;
        csvData = readCsvIntoListOfStringArrays("Product_Purchase.csv");
        for(String[] data : csvData){
            if(data.length != 3){ numErrantLines++; continue; }
            int product_id = Integer.parseInt(data[0]);
            int purchase_id = Integer.parseInt(data[1]);
            int quantity = Integer.parseInt(data[2]);
            values = String.format("(%s, %s, %s)", product_id, purchase_id, quantity);
            if(!insertValuesIntoTable(values, "product_purchases")){
                numErrantLines++;
            }
        }
        if(numErrantLines > 0){ logger.warning("Number of errant lines in PRODUCT_PURCHASE: " + numErrantLines );}
    }

    private static void loadVendors(){
        List<String[]> csvData = readCsvIntoListOfStringArrays("Vendor.csv");
        String values; int numErrantLines = 0;
        String vendorId, vendorName, addrLine1, addrCity, addrState, addrZip;
        for(String[] data : csvData) {
            if(data.length != 6){ numErrantLines++; continue; }
            vendorId = data[0];
            vendorName = data[1];
            addrLine1 = data[2];
            addrCity = data[3];
            addrState = data[4];
            addrZip = data[5];

            values = String.format("(%s, '%s', '%s', '%s', '%s', %s)", vendorId, vendorName, addrLine1, addrCity, addrState, addrZip);
            if( !insertValuesIntoTable(values, "vendors")){
                numErrantLines++;
            }
        }
        if(numErrantLines > 0){ logger.warning("Number of errant lines in VENDOR: " + numErrantLines );}

        csvData.clear(); numErrantLines = 0;
        csvData = readCsvIntoListOfStringArrays("Vendor_Distribution.csv");
        String productId; float unitPrice;
        for(String[] data : csvData){
            if(data.length != 3){ numErrantLines++; continue; }
            vendorId = data[0];
            productId = data[1];
            unitPrice = Integer.parseInt(data[2])*(1.00f/100);

            values = String.format("(%s, %s, %f)", vendorId, productId, unitPrice);
            if(!insertValuesIntoTable(values, "vendor_distributions")){
                numErrantLines++;
            }
        }
        if(numErrantLines > 0){ logger.warning("Number of errant lines in VENDOR_DISTRIBUTION: " + numErrantLines );}
    }

    private static void loadShipments(){
        List<String[]> csvData = readCsvIntoListOfStringArrays("Shipment.csv");
        String values; int numErrantLines = 0;

        for(String[] data : csvData){
            if(data.length != 3){ numErrantLines++; continue; }
            String shipment_id = data[0];
            String store_id = data[1];
            String vendor_id = data[2];
            values = (String.format("(%s, %s, %s)", shipment_id, store_id, vendor_id));
            if(!insertValuesIntoTable(values, "shipments")){
                numErrantLines++;
            }

            // each shipment contains 10 products
            int product_id, quantity;
            for(int i=0; i<10; i++) {
                product_id = random.nextInt(1000);
                quantity = random.nextInt(250);
                values = (String.format("(%s, %d, %d)", shipment_id, product_id, quantity));
                if(!insertValuesIntoTable(values, "shipment_contents")){
                    numErrantLines++;
                }
            }
        }
        if(numErrantLines > 0){ logger.warning("Number of errant lines in SHIPMENT: " + numErrantLines );}
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