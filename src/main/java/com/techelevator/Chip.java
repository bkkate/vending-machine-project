package com.techelevator;

public class Chip extends Purchasable{


    public Chip(String slotLocation, String productName, Double price, String type) {
        super(slotLocation, productName, price, type);
    }

    @Override
    public String slogan(){
        return "Crunch Crunch, It's Yummy!";
    }

}
