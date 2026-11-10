package com.bitfit.plan;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.bitfit.model.WeeklyPlan;
import com.bitfit.model.WorkoutLog;

/** Keeps plans and logs in memory for local runs and tests (when the "firebase" profile is off). */
@Repository
@Profile("!firebase")
public class InMemoryPlanRepository implements PlanRepository {

    private final Map<String, WeeklyPlan> plans = new ConcurrentHashMap<>();
    private final Map<String, WorkoutLog> logs = new ConcurrentHashMap<>();

    @Override
    public WeeklyPlan savePlan(WeeklyPlan plan) {
        plans.put(plan.getAccountId(), plan);
        return plan;
    }

    @Override
    public Optional<WeeklyPlan> findPlanByAccountId(String accountId) {
        return Optional.ofNullable(plans.get(accountId));
    }

    @Override
    public WorkoutLog saveLog(WorkoutLog log) {
        logs.put(log.getLogId(), log);
        return log;
    }

    @Override
    public Optional<WorkoutLog> findLog(String accountId, String date) {
        return Optional.ofNullable(logs.get(WorkoutLog.idFor(accountId, date)));
    }

    @Override
    public List<WorkoutLog> findLogs(String accountId, String from, String to) {
        return logs.values().stream()
                .filter(log -> log.getAccountId().equals(accountId))
                .filter(log -> log.getDate().compareTo(from) >= 0 && log.getDate().compareTo(to) <= 0)
                .sorted(Comparator.comparing(WorkoutLog::getDate))
                .toList();
    }

    @Override
    public void deleteLog(String accountId, String date) {
        logs.remove(WorkoutLog.idFor(accountId, date));
    }
}
