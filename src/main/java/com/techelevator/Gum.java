package com.techelevator;

public class Gum extends Purchasable{

    public Gum(String slotLocation, String productName, Double price, String type) {
        super(slotLocation, productName, price, type);
    }

    @Override
    public String slogan(){
        return "Chew Chew, Pop!";
    }

}
