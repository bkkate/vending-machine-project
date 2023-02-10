package com.techelevator;

import com.techelevator.view.VendingMenu;
import com.techelevator.view.VendingOperations;

import javax.management.ObjectName;
import javax.management.PersistentMBean;
import javax.print.DocFlavor;
import java.io.*;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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


	//saves A1|Potato Crisps|3.05|Chip in a hashmap that has ( Potato Crisps as key, value = A1|3.05|Chip
	private File listOfItems = new File("vendingmachine.csv");

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
		VendingOperations vendingOperations = new VendingOperations(menu);
		while (running) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			List<String[]> arraysOfItems = new ArrayList<>();
			try (Scanner dataInput = new Scanner(listOfItems)) {
				while (dataInput.hasNextLine()) {
					String currentLine = dataInput.nextLine();
					String[] eachItem = currentLine.split("\\|");
					arraysOfItems.add(eachItem);
				}
				// if this is the first run, make a hash map and add each item with full quantity of 5
				if (firstRun) {
					vendingOperations.makeMapOfEveryItem(arraysOfItems);
					firstRun = false;
				}
			}
			catch (FileNotFoundException e){
				System.out.println("The file was not found! Please find it!");
			}
			// A switch statement could also be used here.  Your choice.
			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				// display vending machine items
				vendingOperations.getVendingMachineItems(arraysOfItems);
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				// do purchase
				String purchaseChoice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);
				File outputFile = new File("Log.txt");
				try(FileWriter fileWriter = new FileWriter(outputFile, true); PrintWriter dataOutput = new PrintWriter(fileWriter)){
					while (purchaseChoice != PURCHASE_MENU_OPTION_FINISH_TRANSACTION) {
						if (purchaseChoice.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {

						vendingOperations.purchaseMenuFeedMoneyOption(dataOutput, menu);

						} else if (purchaseChoice.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT)) {
							vendingOperations.getVendingMachineItems(arraysOfItems);
							vendingOperations.purchaseMenuSelectProduct(dataOutput);
						}
						purchaseChoice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);
					}
//						if (purchaseChoice.equals(PURCHASE_MENU_OPTION_FINISH_TRANSACTION) {
						vendingOperations.purchaseMenuOptionFinishTransaction(dataOutput);

				}catch (IOException e){
					System.out.println("File not found");
				}
			} else if (choice.equals(MAIN_MENU_SECRET_OPTION)) {
				File salesReportFile = new File("SalesReport" + vendingOperations.getTimeForSalesReport() + ".txt");
				try(PrintWriter SalesOutput = new PrintWriter(salesReportFile)){

					//saves A1|Potato Crisps|3.05|Chip in a hashmap that has ( Potato Crisps as key, value = A1|3.05|Chip
					//	private Map<String, Purchasable> itemInfo = new HashMap<>();
					double totalSalesAmount = vendingOperations.calculateTotalSalesAmount(SalesOutput);

					SalesOutput.println("\n**TOTAL SALES** $" + String.format("%.2f", totalSalesAmount));
					System.out.println("You can find the Sales Report here: " + salesReportFile.getName());
				}
			} else{
				running = false;
			}
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
