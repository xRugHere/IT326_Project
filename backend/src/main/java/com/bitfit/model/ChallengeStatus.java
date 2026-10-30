package com.bitfit.model;

/** Where a challenge is in its life cycle. */
public enum ChallengeStatus {
    PENDING,     // sent, waiting for the recipient
    ACTIVE,      // accepted
    COMPLETED,
    DECLINED,
    CANCELLED,
    EXPIRED
}
