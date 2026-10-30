package com.bitfit.account;

import java.util.List;
import java.util.Optional;

import com.bitfit.model.Account;

/**
 * REPOSITORY layer: the only place that reads or writes stored accounts.
 * The service depends on this interface, not on Firestore, so the storage can be swapped:
 * {@link FirestoreAccountRepository} in production, {@link InMemoryAccountRepository} for
 * local runs and tests.
 */
public interface AccountRepository {

    /** Inserts or replaces the account and returns it. */
    Account save(Account account);

    Optional<Account> findById(String accountId);

    /** Case-insensitive. */
    Optional<Account> findByUsername(String username);

    Optional<Account> findByFirebaseUid(String firebaseUid);

    Optional<Account> findByFriendCode(String friendCode);

    /** Accounts that have a pending friend request from the given account. */
    List<Account> findByPendingRequestFrom(String accountId);

    void deleteById(String accountId);
}
