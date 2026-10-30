package com.bitfit.model;

/** One food in a meal with its nutrition facts, for example 6 oz of chicken breast. */
public class FoodItem {

    private String name;
    private double amount;
    private String unit;      // "oz", "cups", "g", ...
    private int calories;
    private double protein;   // grams
    private double carbs;     // grams
    private double fat;       // grams

    public FoodItem() {
    }

    public FoodItem(String name, double amount, String unit, int calories, double protein, double carbs, double fat) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public int getCalories() { return calories; }
    public void setCalories(int calories) { this.calories = calories; }

    public double getProtein() { return protein; }
    public void setProtein(double protein) { this.protein = protein; }

    public double getCarbs() { return carbs; }
    public void setCarbs(double carbs) { this.carbs = carbs; }

    public double getFat() { return fat; }
    public void setFat(double fat) { this.fat = fat; }
}
