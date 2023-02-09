package com.techelevator;

import com.techelevator.view.VendingMenu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;


public class VendingMachineCLI {
	//Constant string values are defined for various options in the main menu and the purchase menu.
	//main menu options
	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String MAIN_MENU_SECRET_OPTION = "*Sales Report";
	//purchase options
	private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";
	//Arrays are created for each menu, containing the respective options.
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT, MAIN_MENU_SECRET_OPTION };
	private static final String[] PURCHASE_MENU_OPTIONS = { PURCHASE_MENU_OPTION_FEED_MONEY, PURCHASE_MENU_OPTION_SELECT_PRODUCT, PURCHASE_MENU_OPTION_FINISH_TRANSACTION};
	//A private member variable of type VendingMenu is defined.
	private VendingMenu menu;

	// items with quantity
	//saves A1|Potato Crisps|3.05|Chip in a hashmap that has ( Potato Crisps as key, value = A1|3.05|Chip
	private Map<String, String[] > itemInfo = new HashMap<>();
	private Map<String, Integer > itemAndQuantity = new HashMap<>();

	private double totalBalance = 0.00;

	//The constructor takes a VendingMenu object as an argument and initializes the private member variable menu.
	public VendingMachineCLI(VendingMenu menu) {
		this.menu = menu;
	}
	//The run method has the main logic for the application. It uses a while loop to keep the program running until the running variable is set to false.
	// The getChoiceFromOptions method is called on the menu object to get the user's choice.
		//If the user chooses "Display Vending Machine Items", the corresponding action will be performed (currently nothing).
		//If the user chooses "Purchase", the corresponding action will be performed (currently nothing).
	public void run() throws FileNotFoundException {
		boolean running = true;
		boolean firstRun = true;

		while (running) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			// A switch statement could also be used here.  Your choice.
			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				// display vending machine items
				File listOfItems = new File("vendingmachine.csv");

				try (Scanner dataInput = new Scanner(listOfItems)){
					List<String[]> arraysOfItems = new ArrayList<>();
					while(dataInput.hasNextLine()) {
						String currentLine = dataInput.nextLine();
						String[] eachItem = currentLine.split("\\|");
						arraysOfItems.add(eachItem);
					}

					// if this is the first run, make a hash map and add each item with full quantity of 5
					if (firstRun) {
						for (String[] item : arraysOfItems) {
							//saves A1|Potato Crisps|3.05|Chip in a hashmap that has ( Potato Crisps as key, value = A1|3.05|Chip
							itemInfo.put(item[0], new String[]{item[1], item[2], item[3]});
							itemAndQuantity.put(item[0], 5);
						}
					}

					for (String[] item : arraysOfItems) {
						//print out the name and quantity
						String itemName = item[0];
						if (itemAndQuantity.get(itemName) == 0) {

							System.out.println(item[1] + "| " + "SOLD OUT");
						}
						System.out.println(item[1] + "| " + itemAndQuantity.get(itemName));
						System.out.println(item[1] + "| " + itemInfo);
					}
				}

				catch (FileNotFoundException e){
					//
				}


			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				// do purchase
				String purchaseChoice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);

				while(!purchaseChoice.equals(PURCHASE_MENU_OPTION_FINISH_TRANSACTION)) {
					if (purchaseChoice.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {
						System.out.println("Please enter money in whole dollar amounts");

						double feedMoney = menu.getIn().nextDouble();
						totalBalance += feedMoney;

						System.out.println("Current Money Provided: " + totalBalance);
					}
					else if(purchaseChoice.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT)) {
						File listOfItems = new File("vendingmachine.csv");

						try (Scanner dataInput = new Scanner(listOfItems)){

							String currentLine = "";
							while(dataInput.hasNextLine()) {
								currentLine = dataInput.nextLine();
								System.out.println(currentLine);

							}
							System.out.println("Please choose item!");
							String itemPurchaseChoice = menu.getIn().nextLine();
							//itemPurchaseChoice == "A1"
							if(itemInfo.containsKey(itemPurchaseChoice) ){
								if(itemAndQuantity.get(itemPurchaseChoice) == 0){
									System.out.println("Item is out of stock, please choose another item!");
									continue;
								}
							}else {
								System.out.println(itemInfo);
								System.out.println(itemAndQuantity);
								System.out.println("Item doesn't exist, please try again.");
								continue;
							}
						}
					}
				}

			}
		}
	}

	public Map<String, Integer> getItemAndQuantity() {
		return this.itemAndQuantity;
	}

	// each time you call this setter, it'll count down quantity of the item by 1
	public void setItemAndQuantity (String item) {
		if (itemAndQuantity.get(item)!= 0) {
			this.itemAndQuantity.put(item, itemAndQuantity.get(item)-1) ;
		}
	}

	//The main method creates a VendingMenu object and a VendingMachineCLI object, and calls the run method on the VendingMachineCLI object.
	//This code is just a skeleton for a CLI for a vending machine, we must implement it ourselves.
	public static void main(String[] args) throws FileNotFoundException {
		VendingMenu menu = new VendingMenu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}
}
