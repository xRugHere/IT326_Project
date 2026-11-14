package com.bitfit.model;

import java.util.ArrayList;
import java.util.List;

/** A meal made up of food items (breakfast, lunch, dinner or snack). */
public class Meal {

    private String mealId;
    private String name;
    private String mealType;
    private List<FoodItem> foodItems = new ArrayList<>();

    public Meal() {
    }

    public Meal(String mealId, String name, String mealType) {
        this.mealId = mealId;
        this.name = name;
        this.mealType = mealType;
    }

    public void addFoodItem(FoodItem item) {
        if (item == null) {
            return;
        }
        if (foodItems == null) {
            foodItems = new ArrayList<>();
        }
        foodItems.add(item);
    }

    /** Returns true if a food item was removed. */
    public boolean removeFoodItem(String foodName) {
        if (foodItems == null || foodName == null) {
            return false;
        }

        return foodItems.removeIf(item -> item != null && foodName.equalsIgnoreCase(item.getName()));
    }

    /** Total calories of every food in the meal (named "calculate" so Firestore does not store it). */
    public int calculateTotalCalories() {
        if (foodItems == null) {
            return 0;
        }

        int total = 0;
        for (FoodItem item : foodItems) {
            if (item != null) {
                total += item.getCalories();
            }
        }
        return total;
    }

    public String getMealId() { return mealId; }
    public void setMealId(String mealId) { this.mealId = mealId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getMealType() { return mealType; }
    public void setMealType(String mealType) { this.mealType = mealType; }

    public List<FoodItem> getFoodItems() { return foodItems; }
    public void setFoodItems(List<FoodItem> foodItems) { this.foodItems = foodItems == null ? new ArrayList<>() : foodItems; }
}
