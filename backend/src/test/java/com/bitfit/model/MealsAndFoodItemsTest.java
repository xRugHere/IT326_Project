package com.bitfit.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.bitfit.model.Meal;
import com.bitfit.model.MealPlan;
import com.bitfit.model.FoodItem;

public class MealsAndFoodItemsTest {

    public static void main(String[] args) {
        MealsAndFoodItemsTest tests = new MealsAndFoodItemsTest();
        tests.foodItemStoresNutritionFacts();
        tests.foodItemUpdatesNutritionFacts();
        tests.mealAddsFoodItemsAndCalculatesCalories();
        tests.mealIgnoresNullFoodItems();
        tests.mealRemovesFoodItemsByName();
        tests.mealPlanAddsMealsAndCalculatesPlannedCalories();
        tests.mealPlanIgnoresNullMeals();
        tests.mealPlanStoresPlanDetails();
        tests.mealPlanRemovesMealsAndUpdatesCalories();
        System.out.println("All MealsAndFoodItems tests passed.");
    }

    @Test
    void foodItemStoresNutritionFacts() {
        FoodItem item = new FoodItem("Chicken Breast", 6.0, "oz", 280, 30.0, 0.0, 6.0);

        assertEquals("Chicken Breast", item.getName());
        assertEquals(6.0, item.getAmount());
        assertEquals("oz", item.getUnit());
        assertEquals(280, item.getCalories());
        assertEquals(30.0, item.getProtein());
        assertEquals(0.0, item.getCarbs());
        assertEquals(6.0, item.getFat());
    }

    @Test
    void foodItemUpdatesNutritionFacts() {
        FoodItem item = new FoodItem("Chicken Breast", 6.0, "oz", 280, 30.0, 0.0, 6.0);
        item.setName("Turkey Breast");
        item.setAmount(8.0);
        item.setUnit("oz");
        item.setCalories(320);
        item.setProtein(35.0);
        item.setCarbs(2.0);
        item.setFat(7.0);

        assertEquals("Turkey Breast", item.getName());
        assertEquals(8.0, item.getAmount());
        assertEquals("oz", item.getUnit());
        assertEquals(320, item.getCalories());
        assertEquals(35.0, item.getProtein());
        assertEquals(2.0, item.getCarbs());
        assertEquals(7.0, item.getFat());
    }

    @Test
    void mealAddsFoodItemsAndCalculatesCalories() {
        Meal meal = new Meal("meal-1", "Lunch", "lunch");
        FoodItem chicken = new FoodItem("Chicken Breast", 6.0, "oz", 280, 30.0, 0.0, 6.0);
        FoodItem rice = new FoodItem("Rice", 1.0, "cup", 205, 4.0, 45.0, 0.5);

        meal.addFoodItem(chicken);
        meal.addFoodItem(rice);

        assertEquals(2, meal.getFoodItems().size());
        assertEquals(485, meal.calculateTotalCalories());
    }

    @Test
    void mealIgnoresNullFoodItems() {
        Meal meal = new Meal("meal-1", "Lunch", "lunch");

        meal.addFoodItem(null);

        assertEquals(0, meal.getFoodItems().size());
        assertEquals(0, meal.calculateTotalCalories());
    }

    @Test
    void mealRemovesFoodItemsByName() {
        Meal meal = new Meal("meal-1", "Lunch", "lunch");
        meal.addFoodItem(new FoodItem("Rice", 1.0, "cup", 205, 4.0, 45.0, 0.5));

        assertEquals(1, meal.getFoodItems().size());
        assertTrue(meal.removeFoodItem("rice"));
        assertEquals(0, meal.getFoodItems().size());
        assertFalse(meal.removeFoodItem("salad"));
    }

    @Test
    void mealPlanAddsMealsAndCalculatesPlannedCalories() {
        MealPlan plan = new MealPlan("plan-1", "acct-1", FitnessGoal.LOSE_WEIGHT, 2000);

        Meal breakfast = new Meal("breakfast-1", "Breakfast", "breakfast");
        breakfast.addFoodItem(new FoodItem("Oats", 1.0, "cup", 150, 5.0, 27.0, 3.0));
        breakfast.addFoodItem(new FoodItem("Banana", 1.0, "fruit", 105, 1.3, 27.0, 0.3));

        Meal dinner = new Meal("dinner-1", "Dinner", "dinner");
        dinner.addFoodItem(new FoodItem("Salmon", 8.0, "oz", 420, 50.0, 0.0, 18.0));

        plan.addMeal(breakfast);
        plan.addMeal(dinner);

        assertEquals(2, plan.getMeals().size());
        assertEquals(675, plan.calculatePlannedCalories());
    }

    @Test
    void mealPlanIgnoresNullMeals() {
        MealPlan plan = new MealPlan("plan-1", "acct-1", FitnessGoal.LOSE_WEIGHT, 2000);

        plan.addMeal(null);

        assertEquals(0, plan.getMeals().size());
        assertEquals(0, plan.calculatePlannedCalories());
    }

    @Test
    void mealPlanStoresPlanDetails() {
        MealPlan plan = new MealPlan("plan-1", "acct-1", FitnessGoal.LOSE_WEIGHT, 2000);

        assertEquals("plan-1", plan.getPlanId());
        assertEquals("acct-1", plan.getAccountId());
        assertEquals(FitnessGoal.LOSE_WEIGHT, plan.getGoal());
        assertEquals(2000, plan.getDailyCalorieGoal());
    }

    @Test
    void mealPlanRemovesMealsAndUpdatesCalories() {
        MealPlan plan = new MealPlan("plan-1", "acct-1", FitnessGoal.LOSE_WEIGHT, 2000);
        Meal breakfast = new Meal("breakfast-1", "Breakfast", "breakfast");
        Meal dinner = new Meal("dinner-1", "Dinner", "dinner");
        breakfast.addFoodItem(new FoodItem("Oats", 1.0, "cup", 150, 5.0, 27.0, 3.0));
        dinner.addFoodItem(new FoodItem("Salmon", 8.0, "oz", 420, 50.0, 0.0, 18.0));
        plan.addMeal(breakfast);
        plan.addMeal(dinner);

        assertEquals(2, plan.getMeals().size());
        assertTrue(plan.removeMeal("breakfast-1"));
        assertEquals(1, plan.getMeals().size());
        assertEquals(420, plan.calculatePlannedCalories());
        assertFalse(plan.removeMeal("missing-meal"));
    }
}
