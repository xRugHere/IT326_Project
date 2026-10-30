package com.bitfit.account;

import java.util.List;

/**
 * JSON body for PATCH /api/accounts/{id} (SRS 3.1.2 Edit Account Preferences).
 * Every field is optional: only the fields that are sent are changed.
 */
public record UpdateAccountRequest(
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
