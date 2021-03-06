package com.rainforestcommerce.rcdb.models;

public class Address {

    private String city;
    private String state;
    private String addr_line1;
    private int zipcode;

    public Address(String addr_line1, String city, String state, int zipcode){
        this.addr_line1 = addr_line1;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
    }

    public String toValues() { return String.format("%s, '%s', '%s', '%s'", this.addr_line1, city, state, zipcode); }

    @Override
    public String toString(){
        return String.format("%s %s, %s %s", addr_line1, city, state, zipcode);
    }
}
