package com.bitfit.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bitfit.model.Account;

class AccountServiceTest {

    private InMemoryAccountRepository repository;
    private AccountService service;

    @BeforeEach
    void setUp() {
        repository = new InMemoryAccountRepository();
        service = new AccountService(repository);
    }

    @Test
    void createAccountSetsUpIdFriendCodeAndStartingRank() {

    }

    @Test
    void friendCodesAreUnique() {

    }

    @Test
    void sameLoginCannotCreateTwoAccounts() {

    }

    @Test
    void usernamesAreUniqueIgnoringCase() {

    }

    @Test
    void usersCanOnlyChangeTheirOwnAccount() {

    }

    @Test
    void findsAccountByLogin() {

    }

    @Test
    void findsAccountsByUsernameAndFriendCode() {

    }

    @Test
    void missingAccountThrowsNotFound() {

    }

    @Test
    void updateChangesOnlyTheFieldsSent() {

    }

    @Test
    void cannotRenameToSomeoneElsesUsername() {

    }

    @Test
    void deleteRemovesAccountFromFriendsAndPendingRequests() {

    }

    private Account newAccount(String firebaseUid, String username) {
        // TODO: build an Account with this login ID and username
        return null;
    }
}
