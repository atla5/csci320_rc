package com.rainforestcommerce.rcdb.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Store {

    private long storeId;
    private String name;
    private Address address;
    public HashMap<Long, ProductQuantityPrice> inventory = new HashMap<>();
    public HashMap<Long, StorePurchase> purchase = new HashMap<>();
    public HashMap<Long, Shipment> shipment = new HashMap<>();



    public Store(List<String> data) throws ParseException{
        this.storeId = Long.parseLong(data.get(0));
        this.name = data.get(1);
        String city = data.get(4);
        String state = data.get(5);
        int zip = Integer.parseInt(data.get(5));
        String street = data.get(6);
        int number = Integer.parseInt(data.get(7));
        this.address = new Address(number, street, city, state, zip);
    }

    public Store(Long storeId, String name, String city, String state, int zip, String street, int number) {
        this.storeId = storeId;
        this.name = name;
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

    public Address getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
