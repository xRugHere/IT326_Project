package com.bitfit.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bitfit.auth.DevTokenVerifier;

class AccountHandlerTest {

    private static final String MARCOS = "Bearer dev:uid-marcos:marcos@example.com";
    private static final String ALEX = "Bearer dev:uid-alex:alex@example.com";

    private AccountHandler handler;

    @BeforeEach
    void setUp() {
        handler = new AccountHandler(new AccountService(new InMemoryAccountRepository()), new DevTokenVerifier());
    }

    @Test
    void createUsesTheLoginForIdAndEmail() {

    }

    @Test
    void requestsWithoutALoginAreRejected() {

    }

    @Test
    void createReportsEveryProblemAtOnce() {

    }

    @Test
    void missingBodyIsRejected() {

    }

    @Test
    void findNeedsExactlyOneSearchField() {

    }

    @Test
    void findByFriendCodeIgnoresCase() {

    }

    @Test
    void updateCleansPreferenceLists() {

    }

    @Test
    void cannotEditOrDeleteSomeoneElsesAccount() {

    }

    @Test
    void updateRejectsBadUsername() {

    }
}
