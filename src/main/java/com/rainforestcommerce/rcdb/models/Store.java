package com.rainforestcommerce.rcdb.models;

import java.text.SimpleDateFormat;
import java.util.HashMap;

public class Store {
    private static SimpleDateFormat stf = new SimpleDateFormat("HH:mm");

    private long storeId;
    private String name;
    private Address address;
    public HashMap<Long, ProductQuantityPrice> inventory = new HashMap<>();
    public HashMap<Long, StorePurchase> purchase = new HashMap<>();
    public HashMap<Long, Shipment> shipment = new HashMap<>();


    public Store(Long storeId, String name, String addrLine1, String city, String state, String zip){
        this.storeId = storeId;
        this.name = name;
        this.address = new Address(addrLine1, city, state, zip);
    }

    public Store(Long storeId, String name, int number, String street, String city, String state, int zip) {
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
