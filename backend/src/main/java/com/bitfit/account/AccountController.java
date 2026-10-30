package com.bitfit.account;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * CONTROLLER layer: maps HTTP requests to handler calls and picks the status code.
 * It holds no validation or business logic.
 *
 * Every request needs the header "Authorization: Bearer <Firebase ID token>".
 *
 *   POST   /api/accounts                      create the signed-in user's account   201
 *   GET    /api/accounts/me                   the signed-in user's account          200 / 404
 *   GET    /api/accounts/{id}                 get an account                        200 / 404
 *   GET    /api/accounts?username=...         find by username                      200 / 404
 *   GET    /api/accounts?friendCode=...       find by friend code                   200 / 404
 *   PATCH  /api/accounts/{id}                 edit account preferences (own only)   200 / 403 / 404
 *   DELETE /api/accounts/{id}                 delete the account (own only)         204 / 403 / 404
 */
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private static final String AUTH = "Authorization";

    private final AccountHandler handler;

    public AccountController(AccountHandler handler) {
        this.handler = handler;
    }

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@RequestHeader(name = AUTH, required = false) String auth,
                                                         @RequestBody CreateAccountRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(handler.createAccount(auth, request));
    }

    @GetMapping("/me")
    public ResponseEntity<AccountResponse> getMyAccount(@RequestHeader(name = AUTH, required = false) String auth) {
        return ResponseEntity.ok(handler.getMyAccount(auth));
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponse> getAccount(@RequestHeader(name = AUTH, required = false) String auth,
                                                      @PathVariable("accountId") String accountId) {
        return ResponseEntity.ok(handler.getAccount(auth, accountId));
    }

    @GetMapping
    public ResponseEntity<AccountResponse> findAccount(
            @RequestHeader(name = AUTH, required = false) String auth,
            @RequestParam(name = "username", required = false) String username,
            @RequestParam(name = "friendCode", required = false) String friendCode) {
        return ResponseEntity.ok(handler.findAccount(auth, username, friendCode));
    }

    @PatchMapping("/{accountId}")
    public ResponseEntity<AccountResponse> updateAccount(@RequestHeader(name = AUTH, required = false) String auth,
                                                         @PathVariable("accountId") String accountId,
                                                         @RequestBody UpdateAccountRequest request) {
        return ResponseEntity.ok(handler.updateAccount(auth, accountId, request));
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteAccount(@RequestHeader(name = AUTH, required = false) String auth,
                                              @PathVariable("accountId") String accountId) {
        handler.deleteAccount(auth, accountId);
        return ResponseEntity.noContent().build();
    }
}
