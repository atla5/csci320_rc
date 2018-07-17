package com.rainforestcommerce.rcdb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataLoader {
    private static String dataDirectory = "/Users/aidan/projects/school/csci-320/src/main/resources/sample_data";
    private static String GENERIC_INSERT_STATEMENT = "INSERT into %s VALUES %s;";

    public static void main(String[] args){
        //loadProducts();
        //loadCustomers();
        //loadStores();
        //loadInventory();
        //loadPurchases(); // store and product purchases
        System.exit(0);
    }

    private static void loadProducts(){
        List<String[]> csvData = readCsvIntoListOfStringArrays("Product.csv");
        String values;
        for(String[] data : csvData){
            String upcCode = data[0];
            String productName = data[1];
            String weight = data[2];
            String brand = data[3];

            values = String.format("(%s, '%s', %s, '%s')",
                                    upcCode, productName, weight, brand);
            System.out.println(String.format(GENERIC_INSERT_STATEMENT, "products", values));
        }
    }

    private static void loadCustomers(){
        List<String[]> csvData = readCsvIntoListOfStringArrays("Customer.csv");
        String values;
        for(String[] data : csvData){
            String customerId = data[0];
            String customerName = data[1];
            String birthDate = data[2];
            boolean isMale = data[3].equalsIgnoreCase("male");
            String ethnicity = data[4];
            String phone = data[5];
            int purchasePoints = Integer.parseInt(data[6]);

            values = String.format("(%s, '%s', '%s', %b, '%s', '%s', %d)",
                    customerId, customerName, birthDate, isMale, ethnicity, phone, purchasePoints);
            System.out.println(String.format(GENERIC_INSERT_STATEMENT, "customers", values));
        }
    }

    private static void loadStores(){
        List<String[]> csvData = readCsvIntoListOfStringArrays("Store.csv");
        String values;
        for(String[] data : csvData){
            String storeId = data[0];
            String storeName = data[1];
            String openingTime = data[2];
            String closingTime = data[3];
            String addrCity = data[4];
            String addrState = data[5];
            String addrZip = data[6];
            String addrStreet = data[7];
            String addrNum = data[8];

            values = String.format("(%s, '%s', '%s', '%s', %s, '%s', '%s', '%s', '%s')",
                    storeId, storeName, openingTime, closingTime, addrNum, addrStreet, addrCity, addrState, addrZip
            );
            System.out.println(String.format(GENERIC_INSERT_STATEMENT, "stores", values));
        }
    }

    private static void loadInventory(){
        List<String[]> csvData = readCsvIntoListOfStringArrays("Store_Inventory.csv");
        String values;
        Random random = new Random();
        for(String[] data : csvData){
            String storeId = "" + random.nextInt(1000);
            String productId = "" + random.nextInt(150);;
            float unitPrice = Integer.parseInt(data[2])/(100*1.0f);
            int quantity = Integer.parseInt(data[3]);

            values = String.format("(%s, %s, %s, %d)", storeId, productId, unitPrice, quantity);
            System.out.println(String.format(GENERIC_INSERT_STATEMENT, "store_inventory", values));
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
            int customerId = random.nextInt(150);
            String productId = "" + random.nextInt(150);
            boolean online = data[5].equalsIgnoreCase("true");
            int quantity = random.nextInt(8);

            values = String.format("(%s, %s, %s, '%s', %b)", purchaseId, storeId, customerId, datePurchased, online);
            System.out.println(String.format(GENERIC_INSERT_STATEMENT, "store_purchases", values));
            values = String.format("(%s, %s, %d)", purchaseId, productId, quantity);
            System.out.println(String.format(GENERIC_INSERT_STATEMENT, "product_purchases", values));
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
            System.err.println(String.format("Error reading filename %s", filename));
            exception.printStackTrace();
        }finally{
            try {
                if (br != null) { br.close(); }
            }catch(IOException ioe){
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
}
