package com.techelevator;

import com.techelevator.view.VendingMenu;



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
	//The constructor takes a VendingMenu object as an argument and initializes the private member variable menu.
	public VendingMachineCLI(VendingMenu menu) {
		this.menu = menu;
	}
	//The run method has the main logic for the application. It uses a while loop to keep the program running until the running variable is set to false.
	// The getChoiceFromOptions method is called on the menu object to get the user's choice.
		//If the user chooses "Display Vending Machine Items", the corresponding action will be performed (currently nothing).
		//If the user chooses "Purchase", the corresponding action will be performed (currently nothing).
	public void run() {
		boolean running = true;
		while (running) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			// A switch statement could also be used here.  Your choice.
			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				// display vending machine items
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				// do purchase
			}
		}
	}
	//The main method creates a VendingMenu object and a VendingMachineCLI object, and calls the run method on the VendingMachineCLI object.
	//This code is just a skeleton for a CLI for a vending machine, we must implement it ourselves.
	public static void main(String[] args) {
		VendingMenu menu = new VendingMenu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}
}
