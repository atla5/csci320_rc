package com.rainforestcommerce.rcdb.models;

public class Address {

    private String addrLine1;
    private String state;
    private String city;
    private String zipcode;

    public Address(String addrLine1, String city, String state, String zip){
        this.addrLine1 = addrLine1;
        this.city = city;
        this.state = state;
        this.zipcode = zip;
    }

    public Address(int number, String street, String city, String state, int zipcode){
        this.addrLine1 = number+" "+street;
        this.city = city;
        this.state = state;
        this.zipcode = Integer.toString(zipcode);
    }

    public String toValues() { return String.format("%s, '%s', '%s', '%s'", this.addrLine1, city, state, zipcode); }

    @Override
    public String toString(){
        return String.format("%s %s, %s %s", addrLine1, city, state, zipcode);
    }
}
