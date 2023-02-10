package com.techelevator.view;

import com.techelevator.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

public class VendingOperations {
	private Map<String, Purchasable> itemInfo = new HashMap<>();
	private File listOfItems = new File("vendingmachine.csv");
	private VendingMenu menu;

	private double totalBalance;
	public VendingOperations(VendingMenu menu) {
		this.menu = menu;
	}

	public void purchaseMenuSelectProduct(PrintWriter dataOutput) throws FileNotFoundException {
		try (Scanner dataInput = new Scanner(listOfItems)) {

			//change to method for printing out items

			System.out.println("Please choose item!: ");
			String itemPurchaseChoice = menu.getIn().nextLine().toUpperCase();
			//itemPurchaseChoice == "A1"
			if (itemInfo.containsKey(itemPurchaseChoice)) {
				if (itemInfo.get(itemPurchaseChoice).getQuantity() == 0) {
					System.out.println("Item is out of stock, please choose another item!");

				} else if (itemInfo.get(itemPurchaseChoice).getPrice() > totalBalance) {
					System.out.println("You don't have enough balance");

				} else {
					totalBalance -= itemInfo.get(itemPurchaseChoice).getPrice();
					itemInfo.get(itemPurchaseChoice).setQuantity(itemPurchaseChoice);

					dataOutput.println( getTime() + " " + itemInfo.get(itemPurchaseChoice).getProductName() + " " + itemPurchaseChoice + " $"
							+ String.format("%.2f", itemInfo.get(itemPurchaseChoice).getPrice()) + " $" + String.format("%.2f", totalBalance));

					System.out.println("Dispensing " + itemInfo.get(itemPurchaseChoice).getProductName() + ": price of $" +
							String.format("%.2f", itemInfo.get(itemPurchaseChoice).getPrice()));

					System.out.println("Your current balance is $" + String.format("%.2f", totalBalance));
					System.out.println(itemInfo.get(itemPurchaseChoice).slogan());

				}

			} else {
				System.out.println("Item doesn't exist, please try again.");

			}
		}
	}
    public void purchaseMenuFeedMoneyOption(PrintWriter dataOutput, VendingMenu menu){
		System.out.println("Please enter money in whole dollar amounts");

		double feedMoney = menu.getIn().nextDouble();
		totalBalance += feedMoney;

		dataOutput.println(getTime() + " " + "FEED MONEY: $" + String.format("%.2f", feedMoney) + " $" + String.format("%.2f", totalBalance));

		System.out.println("Current Money Provided: " + String.format("%.2f", totalBalance));
		System.out.println("Would like to add more money? y/n");
		menu.getIn().nextLine();
		String yesOrNo = menu.getIn().nextLine();

		while (yesOrNo.equalsIgnoreCase("y")) {
			System.out.println("Please enter money in whole dollar amounts");

			feedMoney = menu.getIn().nextDouble();
			totalBalance += feedMoney;
			menu.getIn().nextLine();
			System.out.println("Current Money Provided: " + String.format("%.2f", totalBalance));
			System.out.println("Would like to add more money? y/n");
			yesOrNo = menu.getIn().nextLine();
		}

	}

	public void purchaseMenuOptionFinishTransaction(PrintWriter dataOutput){
		dataOutput.println(getTime() + " " + "GIVE CHANGE: $" + String.format("%.2f", totalBalance) + " $" + String.format("%.2f", 0.00));
		BigDecimal totalBalanceBigDecimal = new BigDecimal(totalBalance);
		BigDecimal quarterValue = new BigDecimal(".25");
		BigDecimal dimeValue = new BigDecimal(".10");
		BigDecimal nickelValue = new BigDecimal(".05");

		BigDecimal quartersReturned = totalBalanceBigDecimal.divideToIntegralValue(quarterValue);
		BigDecimal quartersRemainder = totalBalanceBigDecimal.subtract(quartersReturned.multiply(quarterValue));
		BigDecimal dimesReturned = quartersRemainder.divideToIntegralValue(dimeValue);
		BigDecimal dimesRemainder = quartersRemainder.subtract(dimesReturned.multiply(dimeValue));
		BigDecimal nickelsReturned = dimesRemainder.divideToIntegralValue(nickelValue);
		BigDecimal nickelsRemainder = dimesRemainder.subtract(nickelsReturned.multiply(nickelValue));



		System.out.println("Here's your change: " + quartersReturned.intValue() + " quarters, " + dimesReturned.intValue() + " dimes, " + nickelsReturned.intValue() + " nickels!");
		totalBalance = 0.00;
	}

	public void makeMapOfEveryItem(List<String[]> arraysOfItems){
		for (String[] item : arraysOfItems) {
			//saves A1|Potato Crisps|3.05|Chip in a hashmap that has ( Potato Crisps as key, value = A1|3.05|Chip
			//itemInfo.put(item[0], new String[]{item[1], item[2], item[3]});
			if (item[3].equals("Chip")) {
				itemInfo.put(item[0], new Chip(item[0], item[1], Double.parseDouble(item[2]), item[3]));
			}
			if (item[3].equals("Candy")) {
				itemInfo.put(item[0], new Candy(item[0], item[1], Double.parseDouble(item[2]), item[3]));
			}
			if (item[3].equals("Drink")) {
				itemInfo.put(item[0], new Beverage(item[0], item[1], Double.parseDouble(item[2]), item[3]));
			}
			if (item[3].equals("Gum")) {
				itemInfo.put(item[0], new Gum(item[0], item[1], Double.parseDouble(item[2]), item[3]));
			}
		}
	}
	public void getVendingMachineItems(List<String[]>  arraysOfItems){
		System.out.println("Slot   Item                 Price   Amount");
		for (String[] item : arraysOfItems) {
			//print out the name and quantity
			String itemName = item[0];

			if (itemInfo.get(itemName).getQuantity() == 0) {
//							System.out.println(item[1] + "| " + "SOLD OUT");
				System.out.printf("%-6s %-20s %-9s %-5s\n", item[0], item[1], item[2],"SOLD OUT");
			}
			else {
//							System.out.println(item[1] + "| " + itemInfo.get(itemName).getQuantity());
				System.out.printf("%-6s %-20s %-9s %-5d\n", item[0], item[1], item[2], itemInfo.get(itemName).getQuantity());
			}
		}
	}
	public double calculateTotalSalesAmount(PrintWriter SalesOutput){
		double totalSalesAmount = 0.00;
		for (Map.Entry<String, Purchasable> entry : itemInfo.entrySet()) {
			int quantitySold = 5 - entry.getValue().getQuantity();
			SalesOutput.println(entry.getValue().getProductName() + "|" + quantitySold);
			totalSalesAmount += ((entry.getValue().getPrice()) * quantitySold);
		}
		return totalSalesAmount;
	}
    public String getTime(){
        SimpleDateFormat dateTime = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
        String dateTimeStr = dateTime.format(new Date());
        return dateTimeStr;
    }
	public String getTimeForSalesReport(){
		SimpleDateFormat dateTime = new SimpleDateFormat("MM_dd_yyyy_hh_mm_ss");
		String dateTimeStr = dateTime.format(new Date());
		return dateTimeStr;
	}

}
