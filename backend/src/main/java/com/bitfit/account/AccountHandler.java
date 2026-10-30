package com.bitfit.account;

import org.springframework.stereotype.Component;

import com.bitfit.auth.AuthenticatedUser;
import com.bitfit.auth.TokenVerifier;
import com.bitfit.model.Account;

/**
 * HANDLER layer: works out who is making the request (from their login token), checks that
 * request input is well-formed, converts request DTOs into model objects for the service, and
 * converts the results back into response DTOs.
 */
@Component
public class AccountHandler {

    private final AccountService service;
    private final TokenVerifier tokenVerifier;

    public AccountHandler(AccountService service, TokenVerifier tokenVerifier) {
        this.service = service;
        this.tokenVerifier = tokenVerifier;
    }

    /** Creates the BitFit account for the signed-in user. */
    public AccountResponse createAccount(String authorizationHeader, CreateAccountRequest request) {
        // TODO: authenticate, validate the request (username 3-20 letters/numbers/underscores,
        //       age/height/weight in range), build an Account with the user's uid and email,
        //       call service.createAccount, return toResponse(...)
        return null;
    }

    /** The signed-in user's own account. */
    public AccountResponse getMyAccount(String authorizationHeader) {
        // TODO
        return null;
    }

    public AccountResponse getAccount(String authorizationHeader, String accountId) {
        // TODO
        return null;
    }

    /** Finds an account by exactly one of username or friend code. */
    public AccountResponse findAccount(String authorizationHeader, String username, String friendCode) {
        // TODO
        return null;
    }

    public AccountResponse updateAccount(String authorizationHeader, String accountId, UpdateAccountRequest request) {
        // TODO: authenticate, validate, convert to an AccountUpdate, call service.updateAccount
        return null;
    }

    public void deleteAccount(String authorizationHeader, String accountId) {
        // TODO
    }

    /** Reads "Authorization: Bearer <Firebase ID token>" and returns the verified user. */
    AuthenticatedUser authenticate(String authorizationHeader) {
        // TODO: throw UnauthorizedException if the header is missing or not "Bearer ...",
        //       otherwise return tokenVerifier.verify(token)
        return null;
    }

    AccountResponse toResponse(Account account) {
        // TODO: copy the account's fields into an AccountResponse
        return null;
    }
}
