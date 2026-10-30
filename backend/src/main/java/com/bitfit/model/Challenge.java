package com.bitfit.model;

/** A head-to-head challenge one friend sends another, based on a workout and a deadline. */
public class Challenge {

    private String challengeId;
    private String senderId;
    private String recipientId;
    private String workoutId;
    private long deadline;          // milliseconds since 1970 (UTC)
    private ChallengeStatus status = ChallengeStatus.PENDING;
    private int pointValue;
    private String winnerId;

    public Challenge() {
    }

    public Challenge(String challengeId, String senderId, String recipientId,
                     String workoutId, long deadline, int pointValue) {
        // TODO: a user should not be able to challenge themselves
        this.challengeId = challengeId;
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.workoutId = workoutId;
        this.deadline = deadline;
        this.pointValue = pointValue;
    }

    /** Recipient accepts (SRS 3.1.21). */
    public void accept() {
        // TODO: only a PENDING challenge can be accepted -> ACTIVE
    }

    /** Recipient declines (SRS 3.1.22). */
    public void decline() {
        // TODO: only a PENDING challenge can be declined -> DECLINED
    }

    /** Sender cancels before it is accepted (SRS 3.1.20). */
    public void cancel() {
        // TODO: only a PENDING challenge can be cancelled -> CANCELLED
    }

    /** Ends an active challenge with a winner. */
    public void complete(String winnerAccountId) {
        // TODO
    }

    /** Marks the challenge expired if its deadline has passed. Returns true if it expired. */
    public boolean checkExpired(long nowMillis) {
        // TODO
        return false;
    }

    public String getChallengeId() { return challengeId; }
    public void setChallengeId(String challengeId) { this.challengeId = challengeId; }

    public String getSenderId() { return senderId; }
    public void setSenderId(String senderId) { this.senderId = senderId; }

    public String getRecipientId() { return recipientId; }
    public void setRecipientId(String recipientId) { this.recipientId = recipientId; }

    public String getWorkoutId() { return workoutId; }
    public void setWorkoutId(String workoutId) { this.workoutId = workoutId; }

    public long getDeadline() { return deadline; }
    public void setDeadline(long deadline) { this.deadline = deadline; }

    public ChallengeStatus getStatus() { return status; }
    public void setStatus(ChallengeStatus status) { this.status = status; }

    public int getPointValue() { return pointValue; }
    public void setPointValue(int pointValue) { this.pointValue = pointValue; }

    public String getWinnerId() { return winnerId; }
    public void setWinnerId(String winnerId) { this.winnerId = winnerId; }
}
