package com.bitfit.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The social side of an account: friends, pending friend requests and challenges.
 * (Replaces the separate FriendList and ChallengeList classes.) Other users are stored by account ID.
 */
public class AccountProfile {

    private List<String> friendIds = new ArrayList<>();
    private List<String> pendingRequestIds = new ArrayList<>();   // accounts that sent this user a request
    private List<String> challengeIds = new ArrayList<>();

    public AccountProfile() {
    }

    public void receiveFriendRequest(String fromAccountId) {
        // TODO: add to pendingRequestIds unless already a friend or already pending
    }

    public void acceptFriendRequest(String fromAccountId) {
        // TODO: move from pendingRequestIds to friendIds
    }

    public void denyFriendRequest(String fromAccountId) {
        // TODO: remove from pendingRequestIds
    }

    public void addFriend(String accountId) {
        // TODO
    }

    public void removeFriend(String accountId) {
        // TODO
    }

    public boolean hasFriend(String accountId) {
        // TODO
        return false;
    }

    public void addChallenge(String challengeId) {
        // TODO
    }

    public void removeChallenge(String challengeId) {
        // TODO
    }

    public List<String> getFriendIds() { return friendIds; }
    public void setFriendIds(List<String> friendIds) { this.friendIds = friendIds; }

    public List<String> getPendingRequestIds() { return pendingRequestIds; }
    public void setPendingRequestIds(List<String> pendingRequestIds) { this.pendingRequestIds = pendingRequestIds; }

    public List<String> getChallengeIds() { return challengeIds; }
    public void setChallengeIds(List<String> challengeIds) { this.challengeIds = challengeIds; }
}
