package com.bitfit.account;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.bitfit.model.Account;

/** Keeps accounts in memory for local runs and tests (when the "firebase" profile is off). */
@Repository
@Profile("!firebase")
public class InMemoryAccountRepository implements AccountRepository {

    private final Map<String, Account> accounts = new ConcurrentHashMap<>();

    @Override
    public Account save(Account account) {
        // TODO
        return account;
    }

    @Override
    public Optional<Account> findById(String accountId) {
        // TODO
        return Optional.empty();
    }

    @Override
    public Optional<Account> findByUsername(String username) {
        // TODO: case-insensitive (compare against usernameLower)
        return Optional.empty();
    }

    @Override
    public Optional<Account> findByFirebaseUid(String firebaseUid) {
        // TODO
        return Optional.empty();
    }

    @Override
    public Optional<Account> findByFriendCode(String friendCode) {
        // TODO
        return Optional.empty();
    }

    @Override
    public List<Account> findByPendingRequestFrom(String accountId) {
        // TODO
        return List.of();
    }

    @Override
    public void deleteById(String accountId) {
        // TODO
    }
}
