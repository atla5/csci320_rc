package com.rainforestcommerce.rcdb.models;

public class Address {

    private String city;
    private String state;
    private String zipcode;
    private String street;
    private String number;

    public Address(String number, String street, String city, String state, String zipcode){
        this.number = number;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
    }

    public String toValues() { return String.format("%s, '%s', '%s', '%s', '%s'", number, street, city, state, zipcode); }

    @Override
    public String toString(){
        return String.format("%s %s %s, %s %s", number, street, city, state, zipcode);
    }
}
