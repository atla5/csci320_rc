package com.rainforestcommerce.rcdb.models;

public class Address {

    private String city;
    private String state;
    private int zipcode;
    private String street;
    private int number;

    public Address(int number, String street, String city, String state, int zipcode){
        this.number = number;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
    }

    @Override
    public String toString(){
        return String.format("%s %s %s, %s %s", number, street, city, state, zipcode);
    }
}
