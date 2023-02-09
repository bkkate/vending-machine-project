package com.techelevator;

public class Beverage extends Purchasable{


    public Beverage(String slotLocation, String productName, Double price, String type) {
        super(slotLocation, productName, price, type);
    }

    @Override
    public void slogan(){
        System.out.println("Glug Glug, Chug Chug!");
    }


}
