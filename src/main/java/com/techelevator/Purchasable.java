package com.techelevator;

import java.util.ArrayList;
import java.util.List;

public class Purchasable {

    private String slotLocation;
    private String productName;
    private Double price;
    private String type;
    private int quantity = 5;
    private List<Purchasable> purchaseableItems = new ArrayList<>();


    public Purchasable(String slotLocation, String productName, Double price, String type){
        this.slotLocation = slotLocation;
        this.productName = productName;
        this.price = price;
        this.type = type;
    }

    public String getSlotLocation() {
        return slotLocation;
    }

    public String getProductName() {
        return productName;
    }

    public Double getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(String slotLocation) {
        if ( quantity > 0) {
            this.quantity -= 1;
        }
    }


    public void add(Purchasable itemToAdd){
        purchaseableItems.add(itemToAdd);
    }

    public String slogan(){
        return "YUM YUM";
    }

//    @Override
//    public String toString(){
//       return
//    }

}
