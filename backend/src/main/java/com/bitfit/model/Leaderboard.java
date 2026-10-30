package com.bitfit.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * A ranked list of accounts (SRS 3.1.27-3.1.28). It is built on request from the accounts
 * it ranks, so it is not stored in the database.
 */
public final class Leaderboard {

    public enum Type { FRIENDS, GLOBAL }

    public enum SortMetric { POINTS, WORKOUTS_COMPLETED }

    private final Type type;
    private final List<Account> entries;
    private SortMetric sortMetric = SortMetric.POINTS;

    public Leaderboard(Type type, Collection<Account> accounts) {
        this.type = type;
        this.entries = new ArrayList<>(accounts);
        // TODO: sort by points
    }

    /** Re-orders the board, highest first; ties broken by username. */
    public void sortBy(SortMetric metric) {
        this.sortMetric = metric;
        // TODO
    }

    /** 1-based position of an account on the board, or -1 if it is not on it. */
    public int positionOf(String accountId) {
        // TODO
        return -1;
    }

    /** Accounts whose username contains the search text. */
    public List<Account> filterByUsername(String searchText) {
        // TODO
        return List.of();
    }

    public Type getType() { return type; }

    public SortMetric getSortMetric() { return sortMetric; }

    public List<Account> getEntries() { return Collections.unmodifiableList(entries); }
}
