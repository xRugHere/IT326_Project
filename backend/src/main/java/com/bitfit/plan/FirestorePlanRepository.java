package com.bitfit.plan;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.bitfit.model.WeeklyPlan;
import com.bitfit.model.WorkoutLog;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;

/**
 * Stores plans in the Firestore collection "weeklyPlans" (document ID = account ID) and logs in
 * "workoutLogs" (document ID = "{accountId}_{date}"). Active with the "firebase" profile.
 */
@Repository
@Profile("firebase")
public class FirestorePlanRepository implements PlanRepository {

    private static final String PLANS = "weeklyPlans";
    private static final String LOGS = "workoutLogs";

    private final Firestore firestore;

    public FirestorePlanRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public WeeklyPlan savePlan(WeeklyPlan plan) {
        await(firestore.collection(PLANS).document(plan.getAccountId()).set(plan));
        return plan;
    }

    @Override
    public Optional<WeeklyPlan> findPlanByAccountId(String accountId) {
        DocumentSnapshot snapshot = await(firestore.collection(PLANS).document(accountId).get());
        return Optional.ofNullable(snapshot.exists() ? snapshot.toObject(WeeklyPlan.class) : null);
    }

    @Override
    public WorkoutLog saveLog(WorkoutLog log) {
        await(firestore.collection(LOGS).document(log.getLogId()).set(log));
        return log;
    }

    @Override
    public Optional<WorkoutLog> findLog(String accountId, String date) {
        DocumentSnapshot snapshot = await(firestore.collection(LOGS).document(WorkoutLog.idFor(accountId, date)).get());
        return Optional.ofNullable(snapshot.exists() ? snapshot.toObject(WorkoutLog.class) : null);
    }

    @Override
    public List<WorkoutLog> findLogs(String accountId, String from, String to) {
        // Filter the dates here rather than in the query so Firestore doesn't need a composite index.
        return await(firestore.collection(LOGS).whereEqualTo("accountId", accountId).get())
                .toObjects(WorkoutLog.class).stream()
                .filter(log -> log.getDate().compareTo(from) >= 0 && log.getDate().compareTo(to) <= 0)
                .sorted(Comparator.comparing(WorkoutLog::getDate))
                .toList();
    }

    @Override
    public void deleteLog(String accountId, String date) {
        await(firestore.collection(LOGS).document(WorkoutLog.idFor(accountId, date)).delete());
    }

    private static <T> T await(ApiFuture<T> future) {
        try {
            return future.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted while talking to Firestore", e);
        } catch (ExecutionException e) {
            throw new IllegalStateException("Firestore request failed", e.getCause());
        }
    }
}
