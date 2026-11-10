package com.bitfit.model;

/**
 * A workout the user actually did on a given date, with the real reps and weight of every set.
 * Usually starts as a copy of that day's planned workout and is edited at the gym.
 *
 * Stored in the Firestore collection "workoutLogs". There is one log per account per date, so
 * the document ID is "{accountId}_{date}".
 */
public class WorkoutLog {

    private String logId;
    private String accountId;
    private String date;          // ISO date, e.g. "2026-11-09" (strings sort in date order)
    private String dayOfWeek;     // "MONDAY" ... "SUNDAY"
    private Workout workout;
    private long completedAt;

    public WorkoutLog() {
    }

    public WorkoutLog(String accountId, String date, String dayOfWeek, Workout workout) {
        this.logId = idFor(accountId, date);
        this.accountId = accountId;
        this.date = date;
        this.dayOfWeek = dayOfWeek;
        this.workout = workout;
    }

    public static String idFor(String accountId, String date) {
        return accountId + "_" + date;
    }

    public String getLogId() { return logId; }
    public void setLogId(String logId) { this.logId = logId; }

    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(String dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public Workout getWorkout() { return workout; }
    public void setWorkout(Workout workout) { this.workout = workout; }

    public long getCompletedAt() { return completedAt; }
    public void setCompletedAt(long completedAt) { this.completedAt = completedAt; }
}
