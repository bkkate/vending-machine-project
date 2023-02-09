package com.techelevator;

public class Candy extends Purchasable {


    public Candy(String slotLocation, String productName, Double price, String type) {
        super(slotLocation, productName, price, type);
    }

    @Override
    public void slogan(){
        System.out.println("Munch Munch, Mmm Mmm Good!");
    }


}
