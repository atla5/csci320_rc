package com.rainforestcommerce.rcdb.models;

import java.util.Date;

/**
 * Created by aaa10 on 7/15/2018.
 */
public class Shipment {

    Integer id;
    String store;
    Date requestDate;
    Date arrivalDate;
    Integer cost;
    String vendorName;

    public Shipment(Integer ID, String store, Date requestDate, String vendorName, Integer cost, Date arrivalDate){
        this.id = ID;
        this.store = store;
        this.requestDate = requestDate;
        this.vendorName = vendorName;
        this.cost = cost;
        this.arrivalDate = arrivalDate;

    }

    public Integer getID() {
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
