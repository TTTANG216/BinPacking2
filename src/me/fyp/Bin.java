package me.fyp;

import java.awt.*;
import java.util.ArrayList;

public class Bin {
    private int xLoc, yLoc, height, width, remainingHeight, remainingWidth, upperRemainingWidth;
    private boolean isFull = false;

    private boolean upperFull = false, lowerFul = false;
    private ArrayList<Item> itemList;

    // These 2 variables will store for the class instead of the object
    // Meaning all Bin Object will share the same variables.
    private static int widthDistance = 0;
    private static int heightDistance = Main.IMAGE_HEIGHT;

    public void resetDistance() {
        widthDistance = 0;
        heightDistance = Main.IMAGE_HEIGHT;
    }

    public Bin(Graphics pen, int height, int width) {
        // Size of the bin
        this.height = height;
        this.width = width;

        // A list to store items that packed into the bin
        itemList = new ArrayList<>();

        // Variables to store the remaining Height / Width
        remainingHeight = height;
        remainingWidth = width;

        // Variables to calculate the location that placing the bin
        if (widthDistance + width > Main.IMAGE_WIDTH) {
            widthDistance = 0;
            heightDistance -= (height + 5);
        }
        // Set the paint color of bin
        pen.setColor(Color.WHITE);

        // Update the location of the bin
        xLoc = widthDistance + 5;
        yLoc = heightDistance - height - 5;

        // Paint the bin
        pen.fillRect(xLoc, yLoc, width, height);

        // Update the distance for the location of next bin
        widthDistance += width + 5;
    }

    public int getXLoc() { return xLoc; }

    public int getYLoc() { return yLoc; }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getRemainingHeight() {
        return remainingHeight;
    }

    public int getRemainingWidth() {
        return remainingWidth;
    }

    public void setRemainingHeight(int remainingHeight) {
        this.remainingHeight = remainingHeight;
    }

    public void setRemainingWidth(int remainingWidth) {
        this.remainingWidth = remainingWidth;
    }

    public int getRemainingXLoc() { return getXLoc() + (getWidth() - getRemainingWidth()); }

    public int getRemainingYLoc() { return getYLoc(); }

    public void addItem(Item item) {
        itemList.add(item);
    }

    public int getUpperRemainingWidth() {
        return upperRemainingWidth;
    }

    public void setUpperRemainingWidth(int upperRemainingWidth) {
        this.upperRemainingWidth = upperRemainingWidth;
    }

    public int getUpperNextLoc() {
        return getWidth() - getUpperRemainingWidth();
    }

    public boolean isFull() {
        return isFull;
    }

    public void setFull() {
        isFull = true;
    }

    public boolean isUpperFull() {
        return upperFull;
    }

    public boolean isLowerFul() {
        return lowerFul;
    }

    public void setUpperFull() {
        this.upperFull = true;
    }

    public void setLowerFul() {
        this.lowerFul = true;
    }

    public int getFirstWidth() {
        if (countItems() > 0)
            return itemList.get(0).getWidth();
        return 0;
    }

    public int countItems() {
        return itemList.size();
    }

    @Override
    public String toString() {
        return "[Items count: " + countItems() + " | Items packed: " + itemList.toString() + "]";
    }
}
