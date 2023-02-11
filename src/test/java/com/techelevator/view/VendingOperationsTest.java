package com.techelevator.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import com.techelevator.Chip;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class VendingOperationsTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private VendingOperations operationsClass;
    private VendingMenu menu;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));

        operationsClass = new VendingOperations(menu);


    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testPrintOutput() {
        PrintWriter dataOutput = new PrintWriter(System.out);
        operationsClass.setTotalBalance(1.00);
        operationsClass.purchaseMenuOptionFinishTransaction(dataOutput);
        assertEquals("Here's your change: 4 quarters, 0 dimes, 0 nickels!" + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void makes_map_for_each_item() {
        List<String[]> arraysOfItems = new ArrayList<>();

        arraysOfItems.add(new String[]{"A1", "Potato Crisps", "3.05", "Chip"});
        arraysOfItems.add(new String[]{"A2", "Candy Bar", "1.5", "Candy"});
        arraysOfItems.add(new String[]{"A3", "Soda", "2.0", "Drink"});
        arraysOfItems.add(new String[]{"A4", "Chewing Gum", "0.5", "Gum"});

        operationsClass.makeMapOfEveryItem(arraysOfItems);

        assertEquals(4, operationsClass.getItemInfo().size());
        assertEquals("Chip", operationsClass.getItemInfo().get("A1").getType());
        assertEquals("Potato Crisps", operationsClass.getItemInfo().get("A1").getProductName());
        assertEquals(3.05, operationsClass.getItemInfo().get("A1").getPrice(), 0.00);
        assertEquals("A1", operationsClass.getItemInfo().get("A1").getSlotLocation());
    }

    @Test
    public void returns_total_sales_amount() {
        List<String[]> arraysOfItems = new ArrayList<>();
        File outputFile = new File("LogTest.txt");
        try (PrintWriter SalesOutput = new PrintWriter(outputFile)) {

            arraysOfItems.add(new String[]{"A1", "Potato Crisps", "3.05", "Chip"});
            arraysOfItems.add(new String[]{"A2", "Candy Bar", "1.5", "Candy"});
            arraysOfItems.add(new String[]{"A3", "Soda", "2.0", "Drink"});
            arraysOfItems.add(new String[]{"A4", "Chewing Gum", "0.5", "Gum"});

            operationsClass.makeMapOfEveryItem(arraysOfItems);

            operationsClass.getItemInfo().get("A1").setQuantity("A1");
            operationsClass.getItemInfo().get("A4").setQuantity("A4");
            operationsClass.getItemInfo().get("A2").setQuantity("A2");

            assertEquals(5.05, operationsClass.calculateTotalSalesAmount(SalesOutput), 0.00);
        }

        catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }
}