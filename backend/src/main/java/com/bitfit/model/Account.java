package com.bitfit.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A BitFit user. Holds the user's profile details and meal preferences, and owns one
 * {@link AccountProfile} (friends and challenges) and one {@link Progress} (points and rank).
 *
 * Stored in Firestore as one document in the "accounts" collection. Firestore needs the
 * no-argument constructor and a getter and setter for every stored field.
 */
public class Account {

    private String accountId;
    private String firebaseUid;       // the user's ID in Firebase Authentication (links the login to this account)
    private String username;
    private String usernameLower;     // lowercase copy so usernames can be looked up case-insensitively
    private String email;
    private String friendCode;        // unique 8-character code other users enter to send a friend request
    private String profilePicture;    // image URL
    private String bio;
    private Integer age;
    private Double heightInches;
    private Double weightLbs;

    private List<String> allergies = new ArrayList<>();
    private List<String> dislikedFoods = new ArrayList<>();
    private List<String> favoriteFoods = new ArrayList<>();
    private List<String> dietaryRestrictions = new ArrayList<>();

    private AccountProfile profile = new AccountProfile();
    private Progress progress = new Progress();
    private long createdAt;           // milliseconds since 1970 (UTC)

    public Account() {
    }

    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }

    public String getFirebaseUid() { return firebaseUid; }
    public void setFirebaseUid(String firebaseUid) { this.firebaseUid = firebaseUid; }

    public String getUsername() { return username; }
    public void setUsername(String username) {
        this.username = username;
        this.usernameLower = username == null ? null : username.toLowerCase(Locale.ROOT);
    }

    public String getUsernameLower() { return usernameLower; }
    public void setUsernameLower(String usernameLower) { this.usernameLower = usernameLower; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFriendCode() { return friendCode; }
    public void setFriendCode(String friendCode) { this.friendCode = friendCode; }

    public String getProfilePicture() { return profilePicture; }
    public void setProfilePicture(String profilePicture) { this.profilePicture = profilePicture; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public Double getHeightInches() { return heightInches; }
    public void setHeightInches(Double heightInches) { this.heightInches = heightInches; }

    public Double getWeightLbs() { return weightLbs; }
    public void setWeightLbs(Double weightLbs) { this.weightLbs = weightLbs; }

    public List<String> getAllergies() { return allergies; }
    public void setAllergies(List<String> allergies) { this.allergies = allergies; }

    public List<String> getDislikedFoods() { return dislikedFoods; }
    public void setDislikedFoods(List<String> dislikedFoods) { this.dislikedFoods = dislikedFoods; }

    public List<String> getFavoriteFoods() { return favoriteFoods; }
    public void setFavoriteFoods(List<String> favoriteFoods) { this.favoriteFoods = favoriteFoods; }

    public List<String> getDietaryRestrictions() { return dietaryRestrictions; }
    public void setDietaryRestrictions(List<String> dietaryRestrictions) { this.dietaryRestrictions = dietaryRestrictions; }

    public AccountProfile getProfile() { return profile; }
    public void setProfile(AccountProfile profile) { this.profile = profile; }

    public Progress getProgress() { return progress; }
    public void setProgress(Progress progress) { this.progress = progress; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
}
