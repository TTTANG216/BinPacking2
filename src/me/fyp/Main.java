package me.fyp;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

/*
*   This project is in the 2nd phrase, implemented online algorithm and enhanced the offline algorithm
*   Author: TANG Tsz Tsun
*   SID: 1155125182
*
 */

public class Main {
    public static int IMAGE_WIDTH  = 1400;
    public static int IMAGE_HEIGHT = 700;
    public static final int BIN_WIDTH  = 170;
    public static final int BIN_HEIGHT = 220;
    public final int ITEM_SIZE = 20;
    public final boolean TXT = true;
    private String dataSetMsg = "";
    private ArrayList<Bin> bins;
    private ArrayList<Item> items;
    private ArrayList<Item> copy;
    private ArrayList<Item> mItems;
    Random rngObj;
    private Graphics pen;
    private ImageIcon icon;
    private boolean isOnline, isInitialized = false;
    int k1 = 0, k2 = 0, i = 0;
    // k1 = index of bins, k2 = index of items.

    public Main() {
        // Initialize all the objects
        BufferedImage img = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        icon = new ImageIcon(img);
        pen = img.createGraphics();
        bins = new ArrayList<>();
        items = new ArrayList<>();
        copy = new ArrayList<>();
        mItems = new ArrayList<>();
        rngObj = new Random();
    }

    public static void main(String[] args) {
        // Create paint object
        Main main = new Main();
        // Call for painting loop
        main.paintByUser();
    }

    public void paintByUser() {
        bins.add(new Bin(pen,BIN_HEIGHT, BIN_WIDTH));
        // Title string
        String title   = "Painter";
        // Button to choose offline or online mode
        Object[] options = {"", "", "", "Offline", "Online"};

        for ( ; ; )
        {
            int response;
            // Message to show how many item is added and how many bin is used
            String message = (isOnline? "Online Mode": "Offline Mode") + "\nNumber of Item: " + (ITEM_SIZE - items.size()) + "\nNumber of Bin: " + (k1 + 1);
            // Same as array, 0 is the first button pressed
            response = JOptionPane.showOptionDialog(null, message, title, JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE, icon, options, null);
            // Show which button is pressed in the console
            println("Response = " + response);

            // Buttons to be pressed after selecting online or offline
            if (!isInitialized) {
                println("Not yet initialized");
                if (response == 0 || response == 1 || response == 2)
                    continue;
            }

            // Start the offline mode and use offline algorithm
            if (response == 3) {
                isOnline = false;
                isInitialized = true;
                createItems(ITEM_SIZE);
                // Update the buttons
                options = new Object[]{"Next Item", "Print", "Dataset"};
                continue;
            }
            // Start the online mode and use online algorithm
            else if (response == 4) {
                isOnline = true;
                isInitialized = true;
                createItems(ITEM_SIZE);
                // Update the buttons
                options = new Object[]{"Next Item", "Print", "Dataset"};
                continue;
            }

            // If "Next Item" is pressed
            // Put an item
            if(response == 0) {
                if (!isOnline)
                    putItemOffline();
                else
                    putItemOnline();
            }

            // Print message
            else if (response == 1) {

                for (Item item : copy) {
                    println(item.getHeight() + "x" + item.getWidth() + "x1");
                }

                // Print the dataset
                println("===============================");
                println(dataSetMsg);
                // Print the information of the bins
                for (int m = 0; m <= k1; m++)
                    println(bins.get(m).toString());
            }

            // Print the dataset
            else if (response == 2) {
                for (Item item : copy) {
                    println(item.getHeight() + "x" + item.getWidth() + "x1");
                }
            }

            // If "X" is pressed (i.e., closing the window), return to finish the program
            else {
                return;
            }
        }
    }

    public void createItems(int size) {
        // Get the dataset from txt file
        if (TXT) {
            try {
                getData();
            } catch (Exception e) {
                e.printStackTrace();
            }
            items = mItems;
        } else { // Create the dataset in random
            // Create n items and add them into an arraylist
            for (int i=0; i<size; i++) {
                items.add(new Item(pen, rngObj.nextInt(BIN_HEIGHT) + 1, rngObj.nextInt(BIN_WIDTH) + 1));
            }
        }

        // Rotate the item
        for (Item item : items) {
            if (item.getHeight() < item.getWidth()) {
                int temp = item.getHeight();
                item.setHeight(item.getWidth());
                item.setWidth(temp);
            }
        }

        // Sort the item list only if offline mode
        if (!isOnline)
            Collections.sort(items);

        // Save the data for message printing
        for (Item item : items) {
            copy.add(item);
            dataSetMsg = "".concat(dataSetMsg + item + " ");
        }
        println(dataSetMsg);
    }

    // Function to get the dataset from txt file
    public void getData() throws Exception {
        Scanner scanner = new Scanner(new File("data.txt"));
        while(scanner.hasNextLine()) {
            String data = scanner.next();
            String[] splitData = data.replace("[", "").replace("]", "").split("x");
            mItems.add(new Item(pen, Integer.parseInt(splitData[0]), Integer.parseInt(splitData[1])));
        }
        scanner.close();
    }

    public void putItemOnline() {
        // Do whenever there is an item that not yet packed
        if (items.size() <= 0) {
            return;
        }

        // Prevent out of bound
        if (k2 >= items.size()) {
            println("Out of bound; End of algorithm");
            return;
        }

        // Just place the item
        int flag = placeItemOnline(bins.get(k1), items.get(k2));
        if (flag == 1) {
            items.remove(k2);
            return;
        }

        // If we can't place item, start a new bin
        bins.get(k1).setFull();
        println("bin full, add new bin");
        println(bins.get(k1).toString());
        bins.add(new Bin(pen,BIN_HEIGHT, BIN_WIDTH));
        k1++;

    }

    public void putItemOffline() {
        // Do whenever there is an item that not yet packed
        if (items.size() <= 0) {
            return;
        }

        // Prevent out of bound
        if (k2 >= items.size()) {
            println("Out of bound, k2 = 0");
            k2 = 0;
        }

        int flag = placeItemOffline(bins.get(k1), items.get(k2));
        println("flag, k1, k2: " + flag + " " + k1 + " " + k2 + "/" + (items.size()-1));
        // When an item is packed (flag == 1)
        // Remove the item from the arraylist
        if (flag == 1)
            items.remove(k2);
        else if (flag == -1) { // Can't place at lower part
            if (k2 + 2 > items.size()) {
                println("SET LOWER FULL");
                bins.get(k1).setLowerFul();
                bins.get(k1).setUpperFull();
                k2 = 0;
            } else {
                k2++;
                return;
            }
        } else if (flag == -2) {
            if (k2 + 2 > items.size()) {
                println("SET UPPER FULL");
                bins.get(k1).setUpperFull();
                k2 = 0;
            } else {
                k2++;
                return;
            }
        }

        if (bins.get(k1).isLowerFul() && bins.get(k1).isUpperFull())
            bins.get(k1).setFull();

        // If the bin is full, add a new one
        if (bins.get(k1).isFull()) {
            println("bin full, add new bin");
            println(bins.get(k1).toString());
            bins.add(new Bin(pen,BIN_HEIGHT, BIN_WIDTH));
            k1++;
        }
    }

    public int placeItemOffline(Bin bin, Item item) {
        // CASE 1: Bin is empty
        // Always put the first item at the left-bottom corner
        if (bin.countItems() == 0) {
            // Set the location of the item and update the remaining dimension of the bin
            // i = # of item placed, used for generating color of the item
            // X = left, Y = bottom
            item.setLoc(i++, bin.getXLoc(), bin.getYLoc() + (bin.getHeight() - item.getHeight()));

            // Set remaining height & width of bin
            bin.setRemainingHeight(bin.getHeight() - item.getHeight());
            bin.setRemainingWidth(bin.getWidth() - item.getWidth());

            // Upper Width = Bin Width at the beginning
            bin.setUpperRemainingWidth(bin.getWidth());
            // Add the item to bin
            bin.addItem(item);
            return 1;
        }

        // If the item can be packed on top of the first item packed
        if (item.getHeight() <= bin.getRemainingHeight()) {
            // No space for upper width
            if (item.getWidth() > bin.getUpperRemainingWidth()) {
                return -2;
            }
            println("PLACING NEXT UPPER");
            // Placing the item at the upper part
            item.setLoc(i++, bin.getXLoc() + bin.getUpperNextLoc(),
                    bin.getYLoc() + bin.getRemainingHeight() - item.getHeight());
            bin.setUpperRemainingWidth(bin.getUpperRemainingWidth() - item.getWidth());
            bin.addItem(item);
            return 1;
        }


        // Put the item next to the first item packed
        // Check if there is enough space for the next item
        if (item.getWidth() > bin.getRemainingWidth())
            return -1;

        println("PLACING NEXT LOWER");
        // Set the location of the item and add the item to the bin
        item.setLoc(i++, bin.getRemainingXLoc(), bin.getYLoc() + (bin.getHeight() - item.getHeight()));
        // If new item has a greater height than old item, update the remaining height of the bin.
        int oldHeight = bin.getHeight() - bin.getRemainingHeight();
        if (item.getHeight() > oldHeight)
            bin.setRemainingHeight(bin.getHeight() - item.getHeight());
        bin.setRemainingWidth(bin.getRemainingWidth() - item.getWidth());
        bin.addItem(item);
        return 1;
    }

    public int placeItemOnline(Bin bin, Item item) {
        // CASE 1: Bin is empty
        // Always put the first item at the left-bottom corner
        if (bin.countItems() == 0) {
            // Set the location of the item and update the remaining dimension of the bin
            // i = # of item placed, used for generating color of the item
            // X = left, Y = bottom
            item.setLoc(i++, bin.getXLoc(), bin.getYLoc() + (bin.getHeight() - item.getHeight()));

            // Set remaining height & width of bin
            bin.setRemainingHeight(bin.getHeight() - item.getHeight());
            bin.setRemainingWidth(bin.getWidth() - item.getWidth());

            // Upper Width = Bin Width at the beginning
            bin.setUpperRemainingWidth(bin.getWidth());
            // Add the item to bin
            bin.addItem(item);
            return 1;
        }

        // If the item can be packed on top of the first item packed
        if (item.getHeight() <= bin.getRemainingHeight()) {
            // No space for upper width
            if (item.getWidth() > bin.getUpperRemainingWidth()) {
                return -2;
            }
            println("PLACING NEXT UPPER");
            item.setLoc(i++, bin.getXLoc() + bin.getUpperNextLoc(),
                    bin.getYLoc() + bin.getRemainingHeight() - item.getHeight());
            bin.setUpperRemainingWidth(bin.getUpperRemainingWidth() - item.getWidth());
            // If upper item width > lower item width
            // Change the remaining width of bin to prevent overlap since the item are not sorted
            if (bin.getRemainingWidth() > bin.getUpperRemainingWidth()) {
                println("Upper > Lower");
                bin.setRemainingWidth(bin.getUpperRemainingWidth());
            }
            bin.addItem(item);
            return 1;
        }

        // Put the item next to the first item packed
        // Check if there is enough space for the next item
        if (item.getWidth() > bin.getRemainingWidth())
            return -1;

        println("PLACING NEXT LOWER");
        // Set the location of the item and add the item to the bin
        item.setLoc(i++, bin.getRemainingXLoc(), bin.getYLoc() + (bin.getHeight() - item.getHeight()));
        // If new item has a greater height than old item, update the remaining height of the bin.
        int oldHeight = bin.getHeight() - bin.getRemainingHeight();
        if (item.getHeight() > oldHeight)
            bin.setRemainingHeight(bin.getHeight() - item.getHeight());
        bin.setRemainingWidth(bin.getRemainingWidth() - item.getWidth());
        bin.addItem(item);
        return 1;

    }

    // A function just to get rid of the "System.out."
    public void println(String str) {System.out.println(str);}
}