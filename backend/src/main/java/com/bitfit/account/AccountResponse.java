package com.bitfit.account;

import java.util.List;

/** JSON returned for an account. Leaves out internal fields such as the Firebase user ID. */
public record AccountResponse(
        String accountId,
        String username,
        String email,
        String friendCode,
        String profilePicture,
        String bio,
        Integer age,
        Double heightInches,
        Double weightLbs,
        List<String> allergies,
        List<String> dislikedFoods,
        List<String> favoriteFoods,
        List<String> dietaryRestrictions,
        int totalPoints,
        String rank,
        int pointsToNextRank,
        int friendCount,
        int pendingFriendRequests,
        long createdAt) {
}
