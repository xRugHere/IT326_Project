package com.bitfit.model;

/**
 * A rank tier such as "Gold II", decided by a user's total points.
 * Tiers: Bronze, Silver, Gold, Platinum (divisions III, II, I of 200 points each), then Diamond.
 */
public class Rank {

    public static final String[] TIERS = {"Bronze", "Silver", "Gold", "Platinum", "Diamond"};
    public static final int DIVISIONS_PER_TIER = 3;
    public static final int POINTS_PER_DIVISION = 200;

    private String tier;
    private int division;
    private int minPoints;
    private int maxPoints;

    /** Required by Firestore to rebuild the object from the database. */
    public Rank() {
    }

    public Rank(String tier, int division, int minPoints, int maxPoints) {
        this.tier = tier;
        this.division = division;
        this.minPoints = minPoints;
        this.maxPoints = maxPoints;
    }

    /** Returns the rank a user holds with the given number of points. */
    public static Rank forPoints(int points) {
        // TODO: work out the tier and division for this many points (Diamond has no max)
        return null;
    }

    /** Points still needed to reach the next division (0 once a user is in Diamond). */
    public int pointsToNextDivision(int currentPoints) {
        // TODO
        return 0;
    }

    /** True if this rank is higher than the other one. */
    public boolean outranks(Rank other) {
        // TODO: compare minPoints
        return false;
    }

    /** Readable name such as "Gold II" (not named getX so Firestore does not store it). */
    public String displayName() {
        // TODO: tier + division as Roman numerals
        return tier;
    }

    public String getTier() { return tier; }
    public void setTier(String tier) { this.tier = tier; }

    public int getDivision() { return division; }
    public void setDivision(int division) { this.division = division; }

    public int getMinPoints() { return minPoints; }
    public void setMinPoints(int minPoints) { this.minPoints = minPoints; }

    public int getMaxPoints() { return maxPoints; }
    public void setMaxPoints(int maxPoints) { this.maxPoints = maxPoints; }
}
