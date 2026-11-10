package com.bitfit.plan;

import java.util.List;
import java.util.Optional;

import com.bitfit.model.WeeklyPlan;
import com.bitfit.model.WorkoutLog;

/**
 * REPOSITORY layer: the only place that reads or writes weekly plans and workout logs.
 * {@link FirestorePlanRepository} in production, {@link InMemoryPlanRepository} for local runs
 * and tests.
 */
public interface PlanRepository {

    /** Inserts or replaces the plan and returns it. */
    WeeklyPlan savePlan(WeeklyPlan plan);

    Optional<WeeklyPlan> findPlanByAccountId(String accountId);

    /** Inserts or replaces the log (one per account per date) and returns it. */
    WorkoutLog saveLog(WorkoutLog log);

    Optional<WorkoutLog> findLog(String accountId, String date);

    /** The account's logs dated from..to (inclusive, ISO dates), oldest first. */
    List<WorkoutLog> findLogs(String accountId, String from, String to);

    void deleteLog(String accountId, String date);
}
