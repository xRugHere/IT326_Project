package com.bitfit.account;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.bitfit.model.Account;
import com.google.cloud.firestore.Firestore;

/**
 * Stores accounts in the Firestore collection "accounts", one document per account, with the
 * account ID as the document ID. Active with the "firebase" profile.
 */
@Repository
@Profile("firebase")
public class FirestoreAccountRepository implements AccountRepository {

    private static final String COLLECTION = "accounts";

    private final Firestore firestore;

    public FirestoreAccountRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public Account save(Account account) {
        // TODO: firestore.collection(COLLECTION).document(account.getAccountId()).set(account).get()
        return account;
    }

    @Override
    public Optional<Account> findById(String accountId) {
        // TODO: document(accountId).get(), then snapshot.toObject(Account.class)
        return Optional.empty();
    }

    @Override
    public Optional<Account> findByUsername(String username) {
        // TODO: whereEqualTo("usernameLower", username.toLowerCase())
        return Optional.empty();
    }

    @Override
    public Optional<Account> findByFirebaseUid(String firebaseUid) {
        // TODO: whereEqualTo("firebaseUid", firebaseUid)
        return Optional.empty();
    }

    @Override
    public Optional<Account> findByFriendCode(String friendCode) {
        // TODO: whereEqualTo("friendCode", friendCode)
        return Optional.empty();
    }

    @Override
    public List<Account> findByPendingRequestFrom(String accountId) {
        // TODO: whereArrayContains("profile.pendingRequestIds", accountId)
        return List.of();
    }

    @Override
    public void deleteById(String accountId) {
        // TODO
    }
}
