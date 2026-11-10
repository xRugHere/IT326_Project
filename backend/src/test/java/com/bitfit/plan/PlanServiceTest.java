package com.bitfit.plan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.bitfit.model.Exercise;
import com.bitfit.model.ExerciseSet;
import com.bitfit.model.WeeklyPlan;
import com.bitfit.model.Workout;
import com.bitfit.model.WorkoutLog;

class PlanServiceTest {

    private static final LocalDate TODAY = LocalDate.of(2026, 11, 9);   // a Monday

    private InMemoryPlanRepository repository;
    private PlanService service;

    @BeforeEach
    void setUp() {
        repository = new InMemoryPlanRepository();
        Clock clock = Clock.fixed(TODAY.atStartOfDay().toInstant(ZoneOffset.UTC), ZoneOffset.UTC);
        service = new PlanService(repository, clock);
    }

    @Test
    void newPlanIsAllRestDays() {
        WeeklyPlan plan = service.getPlan("acct-1");

        assertEquals("acct-1", plan.getAccountId());
        assertTrue(plan.getWorkoutPlan().getDays().isEmpty());
        assertTrue(repository.findPlanByAccountId("acct-1").isPresent());
    }

    @Test
    void setDayKeepsEverySetSeparately() {
        service.setDayWorkout("acct-1", DayOfWeek.MONDAY, pushDay());

        Workout monday = service.getPlan("acct-1").getWorkoutPlan().workoutFor(DayOfWeek.MONDAY);
        List<ExerciseSet> benchSets = monday.getExercises().get(0).getSets();
        assertEquals("Push Day", monday.getName());
        assertEquals(3, benchSets.size());
        assertEquals(155, benchSets.get(1).getWeightLbs());
        assertEquals(4, benchSets.get(2).getReps());
        assertNull(service.getPlan("acct-1").getWorkoutPlan().workoutFor(DayOfWeek.TUESDAY));
    }

    @Test
    void clearDayMakesItARestDay() {
        service.setDayWorkout("acct-1", DayOfWeek.MONDAY, pushDay());
        service.clearDay("acct-1", DayOfWeek.MONDAY);

        assertNull(service.getPlan("acct-1").getWorkoutPlan().workoutFor(DayOfWeek.MONDAY));
    }

    @Test
    void plansAreSeparatePerAccount() {
        service.setDayWorkout("acct-1", DayOfWeek.MONDAY, pushDay());

        assertTrue(service.getPlan("acct-2").getWorkoutPlan().getDays().isEmpty());
    }

    @Test
    void namesAreTrimmed() {
        Workout workout = pushDay();
        workout.setName("  Push Day  ");
        workout.getExercises().get(0).setName(" Bench Press ");

        Workout saved = service.setDayWorkout("acct-1", DayOfWeek.MONDAY, workout)
                .getWorkoutPlan().workoutFor(DayOfWeek.MONDAY);
        assertEquals("Push Day", saved.getName());
        assertEquals("Bench Press", saved.getExercises().get(0).getName());
    }

    @Test
    void rejectsBadWorkoutsAndReportsEveryProblem() {
        Workout workout = new Workout(null, "");
        Exercise noSets = new Exercise("Squat");
        Exercise badSet = new Exercise("Bench Press");
        badSet.addSet(new ExerciseSet(0, -5));
        workout.addExercise(noSets);
        workout.addExercise(badSet);

        ResponseStatusException e = assertThrows(ResponseStatusException.class,
                () -> service.setDayWorkout("acct-1", DayOfWeek.MONDAY, workout));
        assertEquals(HttpStatus.BAD_REQUEST, e.getStatusCode());
        assertTrue(e.getReason().contains("Workout name is required"));
        assertTrue(e.getReason().contains("Squat needs at least one set"));
        assertTrue(e.getReason().contains("Bench Press set 1: reps"));
        assertTrue(e.getReason().contains("Bench Press set 1: weight"));
    }

    @Test
    void rejectsWorkoutWithNoExercises() {
        assertThrows(ResponseStatusException.class,
                () -> service.setDayWorkout("acct-1", DayOfWeek.MONDAY, new Workout(null, "Empty")));
    }

    @Test
    void bodyweightSetsAreAllowed() {
        Workout workout = new Workout(null, "Calisthenics");
        Exercise pushUps = new Exercise("Push-ups");
        pushUps.addSet(new ExerciseSet(20, 0));
        workout.addExercise(pushUps);

        service.setDayWorkout("acct-1", DayOfWeek.FRIDAY, workout);
    }

    @Test
    void saveLogRecordsDateAndDay() {
        WorkoutLog log = service.saveLog("acct-1", TODAY, pushDay());

        assertEquals("acct-1_2026-11-09", log.getLogId());
        assertEquals("2026-11-09", log.getDate());
        assertEquals("MONDAY", log.getDayOfWeek());
        assertEquals(3, log.getWorkout().getExercises().get(0).getSets().size());
    }

    @Test
    void savingTheSameDateAgainReplacesTheLog() {
        service.saveLog("acct-1", TODAY, pushDay());
        Workout more = pushDay();
        more.getExercises().get(0).addSet(new ExerciseSet(2, 185));
        service.saveLog("acct-1", TODAY, more);

        List<WorkoutLog> logs = service.getLogs("acct-1", TODAY, TODAY);
        assertEquals(1, logs.size());
        assertEquals(4, logs.get(0).getWorkout().getExercises().get(0).getSets().size());
    }

    @Test
    void cannotLogAFutureDate() {
        assertThrows(ResponseStatusException.class,
                () -> service.saveLog("acct-1", TODAY.plusDays(1), pushDay()));
    }

    @Test
    void getLogsFiltersByDateAndAccountOldestFirst() {
        service.saveLog("acct-1", TODAY, pushDay());
        service.saveLog("acct-1", TODAY.minusDays(2), pushDay());
        service.saveLog("acct-1", TODAY.minusDays(10), pushDay());
        service.saveLog("acct-2", TODAY, pushDay());

        List<WorkoutLog> logs = service.getLogs("acct-1", TODAY.minusDays(6), TODAY);
        assertEquals(List.of("2026-11-07", "2026-11-09"), logs.stream().map(WorkoutLog::getDate).toList());
    }

    @Test
    void getLogsRejectsBackwardsRange() {
        assertThrows(ResponseStatusException.class, () -> service.getLogs("acct-1", TODAY, TODAY.minusDays(1)));
    }

    @Test
    void deleteLog() {
        service.saveLog("acct-1", TODAY, pushDay());
        service.deleteLog("acct-1", TODAY);

        assertTrue(service.getLogs("acct-1", TODAY, TODAY).isEmpty());
        ResponseStatusException e = assertThrows(ResponseStatusException.class,
                () -> service.deleteLog("acct-1", TODAY));
        assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
    }

    /** Bench press pyramid: 135x8, 155x6, 175x4. */
    private static Workout pushDay() {
        Workout workout = new Workout(null, "Push Day");
        Exercise bench = new Exercise("Bench Press");
        bench.addSet(new ExerciseSet(8, 135));
        bench.addSet(new ExerciseSet(6, 155));
        bench.addSet(new ExerciseSet(4, 175));
        workout.addExercise(bench);
        return workout;
    }
}
