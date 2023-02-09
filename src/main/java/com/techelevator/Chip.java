package com.techelevator;

public class Chip extends Purchasable{


    public Chip(String slotLocation, String productName, Double price, String type) {
        super(slotLocation, productName, price, type);
    }

    @Override
    public void slogan(){
        System.out.println("Crunch Crunch, It's Yummy!");
    }

}
