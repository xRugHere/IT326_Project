package com.bitfit.account;

import java.util.List;

/**
 * The changes to make to an account, already validated by the handler.
 * A null field means "leave it unchanged".
 */
public record AccountUpdate(
        String username,
        String profilePicture,
        String bio,
        Integer age,
        Double heightInches,
        Double weightLbs,
        List<String> allergies,
        List<String> dislikedFoods,
        List<String> favoriteFoods,
        List<String> dietaryRestrictions) {
}
