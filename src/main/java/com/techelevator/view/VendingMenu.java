package com.techelevator.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class VendingMenu {	//class provides an interface for a user to interact with a vending machine by displaying a list of options and receiving input from the user.

	private PrintWriter out;
	private Scanner in;
	//The constructor takes two arguments, input and output, which are the input and output streams for the class.
	// The constructor initializes the in and out instance variables, which are type Scanner and PrintWriter.
	// These are used to read input from the user and write output to the user.
	public VendingMenu(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output);
		this.in = new Scanner(input);
	}

	//The getChoiceFromOptions method takes an array of options as an argument and returns a selected option.
	// The method calls the displayMenuOptions method to display the options to the user.
	// Then, it calls the getChoiceFromUserInput method to receive the user's choice.
	// The method continues to call the getChoiceFromUserInput method until a valid choice has been selected.
	public Object getChoiceFromOptions(Object[] options) {
		Object choice = null;
		while (choice == null) {
			displayMenuOptions(options);
			choice = getChoiceFromUserInput(options);
		}
		return choice;
	}
	//The getChoiceFromUserInput method takes an array of options as an argument and returns a selected option.
	// The method reads input from the user and tries to parse it as an integer.
	// If the input is a valid integer, it checks if the integer is within the range of the options array.
	// If it is, it returns the corresponding option from the array.
	// If the input isn't a valid integer or is outside the range of the options array, the method returns null and writes an error message to the user.
	private Object getChoiceFromUserInput(Object[] options) {
		Object choice = null;
		String userInput = in.nextLine();
		try {
			int selectedOption = Integer.valueOf(userInput);
			if (selectedOption > 0 && selectedOption <= options.length) {
				choice = options[selectedOption - 1];
			}
		} catch (NumberFormatException e) {
			// eat the exception, an error message will be displayed below since choice will be null
		}
		if (choice == null) {
			out.println(System.lineSeparator() + "*** " + userInput + " is not a valid option ***" + System.lineSeparator());
		}
		return choice;
	}
	//The displayMenuOptions method takes an array of options as an argument and displays the options to the user.
	// The method loops through the options array and writes each option, preceded by its index number, to the output stream.
	// The method also writes a prompt for the user to choose an option.
	private void displayMenuOptions(Object[] options) {
		out.println();
		for (int i = 0; i < options.length; i++) {
			int optionNum = i + 1;
			out.println(optionNum + ") " + options[i]);
		}
		out.print(System.lineSeparator() + "Please choose an option >>> ");
		out.flush();
	}
}
