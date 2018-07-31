package com.rainforestcommerce.rcdb.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Store {
    private static SimpleDateFormat stf = new SimpleDateFormat("HH:mm");

    private long storeId;
    private String name;
    private Date openingTime;
    private Date closingTime;
    private Address address;
    public ArrayList<ProductQuantityPrice> inventory = new ArrayList<ProductQuantityPrice>();
    public ArrayList<StorePurchase> purchase = new ArrayList<StorePurchase>();
    public ArrayList<Shipment> shipment = new ArrayList<Shipment>();



    public Store(List<String> data) throws ParseException{
        this.storeId = Long.parseLong(data.get(0));
        this.name = data.get(1);
        this.openingTime = stf.parse(data.get(2));
        this.closingTime = stf.parse(data.get(3));
        String city = data.get(4);
        String state = data.get(5);
        int zip = Integer.parseInt(data.get(5));
        String street = data.get(6);
        int number = Integer.parseInt(data.get(7));
        this.address = new Address(number, street, city, state, zip);
    }

    public Store(Long storeId, String name, String openingTime, String closingTime, String city, String state, int zip, String street, int number) {
        this.storeId = storeId;
        this.name = name;
        //this.openingTime = openingTime;
        //this.closingTime = closingTime;
        try{
            this.openingTime = stf.parse(openingTime);
            this.closingTime = stf.parse(closingTime);
        } catch (ParseException pe){
            pe.printStackTrace();
            System.exit(1);
        }
        this.address = new Address(number, street, city, state, zip);
    }

    public Store(long storeId){
        this.storeId = storeId;
    }

    public long getStoreId() {
        return storeId;
    }

    public String getName() {
        return name;
    }

    public Date getOpeningTime() {
        return openingTime;
    }

    public Date getClosingTime() {
        return closingTime;
    }

    public Address getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOpeningTime(Date openingTime) {
        this.openingTime = openingTime;
    }

    public void setClosingTime(Date closingTime) {
        this.closingTime = closingTime;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
