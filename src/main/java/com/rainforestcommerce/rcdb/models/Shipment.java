package com.rainforestcommerce.rcdb.models;

import java.util.Date;

/**
 * Created by aaa10 on 7/15/2018.
 */
public class Shipment {

    long id;
    String store;
    Date requestDate;
    Date arrivalDate;
    Integer cost;
    String vendorName;

    public Shipment(long ID, String store, Date requestDate){
        this.id = ID;
        this.store = store;
        this.requestDate = requestDate;
        this.vendorName = vendorName;
        this.cost = cost;
        this.arrivalDate = arrivalDate;

    }

    public long getID() {
        return id;
    }

    public String getStore() {
        return store;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setArrivalDate(Date arrivalDate){
        this.arrivalDate = arrivalDate;
    }

    public Date getArrivalDate() {return arrivalDate;}

    public Integer getCost() {
        return cost;
    }

    public String getVendorName(){
        return vendorName;
    }
}
