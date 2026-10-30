package com.bitfit.account;

/**
 * JSON body for POST /api/accounts. The user's ID and email come from their Firebase login
 * token, so they are not part of the body.
 */
public record CreateAccountRequest(
        String username,
        Integer age,
        Double heightInches,
        Double weightLbs) {
}
