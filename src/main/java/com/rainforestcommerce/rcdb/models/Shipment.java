package com.rainforestcommerce.rcdb.models;

import java.util.Date;

/**
 * Created by aaa10 on 7/15/2018.
 */
public class Shipment {

    Integer id;
    String store;
    Date requestDate;

    public Shipment(Integer ID, String store, Date requestDate){
        this.id = ID;
        this.store = store;
        this.requestDate = requestDate;
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
}
