package com.bitfit.account;

import org.springframework.stereotype.Service;

import com.bitfit.model.Account;

/**
 * SERVICE layer: the business rules for accounts (one BitFit account per login, unique
 * usernames and friend codes, users may only change their own account). Talks to the
 * database only through {@link AccountRepository}.
 */
@Service
public class AccountService {

    static final int FRIEND_CODE_LENGTH = 8;

    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    /** SRS 3.1.1 Create User Account. */
    public Account createAccount(Account newAccount) {
        // TODO: reject a second account for the same login or a taken username (ConflictException),
        //       then set the accountId, friend code, empty AccountProfile, new Progress, createdAt and save
        return null;
    }

    public Account getAccount(String accountId) {
        // TODO: NotFoundException if missing
        return null;
    }

    public Account getAccountByFirebaseUid(String firebaseUid) {
        // TODO
        return null;
    }

    public Account getAccountByUsername(String username) {
        // TODO
        return null;
    }

    /** Used by Send Friend Request (SRS 3.1.30). */
    public Account getAccountByFriendCode(String friendCode) {
        // TODO
        return null;
    }

    /** SRS 3.1.2 Edit Account Preferences. Only the owner may edit; only non-null fields change. */
    public Account updateAccount(String requesterUid, String accountId, AccountUpdate update) {
        // TODO: ForbiddenException if the requester doesn't own the account
        return null;
    }

    /** SRS 3.1.5 Delete User Account. Only the owner may delete. */
    public void deleteAccount(String requesterUid, String accountId) {
        // TODO: also remove the account from friends' lists and pending requests
    }

    private String generateUniqueFriendCode() {
        // TODO: random FRIEND_CODE_LENGTH-character code that no other account has
        return null;
    }
}
