package me.fyp;

import java.awt.*;

public class Item implements Comparable{
    private int xLoc, yLoc, height, width;
    private static Graphics mPen;

    public Item(Graphics pen, int height, int width) {
        mPen = pen;
        this.height = height;
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setLoc(int k, int x, int y) {
        // Set the location for the item
        xLoc = x;
        yLoc = y;

        // Paint the item with colors to indicate different items
        if (k % 5 == 0) {
            mPen.setColor(Color.RED);
        } else if (k % 5 == 1) {
            mPen.setColor(Color.GREEN);
        } else if (k % 5 == 2) {
            mPen.setColor(Color.BLUE);
        } else if (k % 5 == 3) {
            mPen.setColor(Color.ORANGE);
        } else if (k % 5 == 4) {
            mPen.setColor(Color.DARK_GRAY);
        }

        // Paint the item
        mPen.fillRect(x, y, width, height);
    }

    public int getXLoc() {
        return xLoc;
    }

    public int getYLoc() {
        return yLoc;
    }

    // Use for sorting the object by variable height
    @Override
    public int compareTo(Object o) {
        int compareH = ((Item) o).getHeight();
        return compareH - this.height;
    }

    @Override
    public String toString() {
        return "[" + height + "x" + width + "]";
    }
}