package com.colorpalettegenerator.model;

public class ColorModel {
    private int red;
    private int green;
    private int blue;

    // Constructors, getters, and setters

    // Default constructor
    public ColorModel() {
    }

    // Parameterized constructor
    public ColorModel(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    // Getters and setters
    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }
    public String toHexString() {
        return String.format("#%02X%02X%02X", red, green, blue);
    }
}

