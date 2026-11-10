package com.bitfit.plan;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.bitfit.model.Exercise;
import com.bitfit.model.ExerciseSet;
import com.bitfit.model.WeeklyPlan;
import com.bitfit.model.Workout;
import com.bitfit.model.WorkoutLog;

/**
 * SERVICE layer: the business rules for weekly plans and workout logs. Every account has one
 * weekly plan (made the first time it is asked for) and at most one workout log per date.
 * Callers pass the account ID of the signed-in user, so users only ever touch their own data.
 */
@Service
public class PlanService {

    static final int MAX_NAME_LENGTH = 50;
    static final int MAX_EXERCISES = 30;
    static final int MAX_SETS = 30;
    static final int MAX_REPS = 1000;
    static final double MAX_WEIGHT_LBS = 2000;

    private final PlanRepository repository;
    private final Clock clock;

    @Autowired
    public PlanService(PlanRepository repository) {
        this(repository, Clock.systemDefaultZone());
    }

    PlanService(PlanRepository repository, Clock clock) {
        this.repository = repository;
        this.clock = clock;
    }

    /** The account's weekly plan, made empty (every day a rest day) if it doesn't exist yet. */
    public WeeklyPlan getPlan(String accountId) {
        return repository.findPlanByAccountId(accountId)
                .orElseGet(() -> repository.savePlan(new WeeklyPlan(accountId)));
    }

    /** Sets or replaces the workout planned for one day of the week. */
    public WeeklyPlan setDayWorkout(String accountId, DayOfWeek day, Workout workout) {
        Workout cleaned = validateWorkout(workout);
        WeeklyPlan plan = getPlan(accountId);
        plan.getWorkoutPlan().setDay(day, cleaned);
        return touchAndSave(plan);
    }

    /** Makes a day of the week a rest day. */
    public WeeklyPlan clearDay(String accountId, DayOfWeek day) {
        WeeklyPlan plan = getPlan(accountId);
        plan.getWorkoutPlan().clearDay(day);
        return touchAndSave(plan);
    }

    /**
     * Saves the workout the user did on a date. Saving the same date again replaces that day's
     * log, so the user can keep adding sets while they are at the gym.
     */
    public WorkoutLog saveLog(String accountId, LocalDate date, Workout workout) {
        if (date.isAfter(LocalDate.now(clock))) {
            throw badRequest("Can't log a workout for a future date");
        }
        Workout cleaned = validateWorkout(workout);
        boolean firstLogForDate = repository.findLog(accountId, date.toString()).isEmpty();

        WorkoutLog log = new WorkoutLog(accountId, date.toString(), date.getDayOfWeek().name(), cleaned);
        log.setCompletedAt(clock.millis());
        repository.saveLog(log);

        if (firstLogForDate) {
            // TODO: award points once scoring is decided, e.g.
            //       account.getProgress().recordWorkout(log.getLogId(), points) and save the account
        }
        return log;
    }

    /** The account's logs from..to (inclusive), oldest first. */
    public List<WorkoutLog> getLogs(String accountId, LocalDate from, LocalDate to) {
        if (from.isAfter(to)) {
            throw badRequest("'from' must be on or before 'to'");
        }
        return repository.findLogs(accountId, from.toString(), to.toString());
    }

    public void deleteLog(String accountId, LocalDate date) {
        if (repository.findLog(accountId, date.toString()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No workout logged on " + date);
        }
        repository.deleteLog(accountId, date.toString());
    }

    /** Checks the workout and returns a copy with names trimmed. Reports every problem at once. */
    Workout validateWorkout(Workout workout) {
        if (workout == null) {
            throw badRequest("A workout is required");
        }
        List<String> problems = new ArrayList<>();
        String name = checkName(workout.getName(), "Workout name", problems);

        List<Exercise> exercises = workout.getExercises() == null ? List.of() : workout.getExercises();
        if (exercises.isEmpty()) {
            problems.add("Add at least one exercise");
        } else if (exercises.size() > MAX_EXERCISES) {
            problems.add("A workout can have at most " + MAX_EXERCISES + " exercises");
        }

        Workout cleaned = new Workout(workout.getWorkoutId(), name);
        for (int i = 0; i < exercises.size(); i++) {
            Exercise exercise = exercises.get(i);
            String label = "Exercise " + (i + 1);
            if (exercise == null) {
                problems.add(label + " is empty");
                continue;
            }
            Exercise cleanedExercise = new Exercise(checkName(exercise.getName(), label + " name", problems));
            if (cleanedExercise.getName() != null) {
                label = cleanedExercise.getName();
            }

            List<ExerciseSet> sets = exercise.getSets() == null ? List.of() : exercise.getSets();
            if (sets.isEmpty()) {
                problems.add(label + " needs at least one set");
            } else if (sets.size() > MAX_SETS) {
                problems.add(label + " can have at most " + MAX_SETS + " sets");
            }
            for (int s = 0; s < sets.size(); s++) {
                ExerciseSet set = sets.get(s);
                String setLabel = label + " set " + (s + 1);
                if (set == null) {
                    problems.add(setLabel + " is empty");
                    continue;
                }
                if (set.getReps() < 1 || set.getReps() > MAX_REPS) {
                    problems.add(setLabel + ": reps must be 1-" + MAX_REPS);
                }
                if (set.getWeightLbs() < 0 || set.getWeightLbs() > MAX_WEIGHT_LBS) {
                    problems.add(setLabel + ": weight must be 0-" + (int) MAX_WEIGHT_LBS + " lbs");
                }
                cleanedExercise.addSet(new ExerciseSet(set.getReps(), set.getWeightLbs()));
            }
            cleaned.addExercise(cleanedExercise);
        }

        if (!problems.isEmpty()) {
            throw badRequest(String.join("; ", problems));
        }
        return cleaned;
    }

    private static String checkName(String name, String label, List<String> problems) {
        String trimmed = name == null ? "" : name.trim();
        if (trimmed.isEmpty()) {
            problems.add(label + " is required");
            return null;
        }
        if (trimmed.length() > MAX_NAME_LENGTH) {
            problems.add(label + " must be at most " + MAX_NAME_LENGTH + " characters");
        }
        return trimmed;
    }

    private WeeklyPlan touchAndSave(WeeklyPlan plan) {
        plan.setUpdatedAt(clock.millis());
        return repository.savePlan(plan);
    }

    private static ResponseStatusException badRequest(String message) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
    }
}
