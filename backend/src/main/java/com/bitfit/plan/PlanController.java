package com.bitfit.plan;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bitfit.model.WeeklyPlan;
import com.bitfit.model.Workout;
import com.bitfit.model.WorkoutLog;

/**
 * CONTROLLER layer: maps HTTP requests to handler calls and picks the status code.
 * It holds no validation or business logic.
 *
 * Every request needs the header "Authorization: Bearer <Firebase ID token>", and only ever
 * reads or changes the signed-in user's own plan and logs.
 *
 *   GET    /api/plans/me                        my weekly plan                           200
 *   PUT    /api/plans/me/workouts/{day}         set the workout for MONDAY..SUNDAY       200 / 400
 *   DELETE /api/plans/me/workouts/{day}         make that day a rest day                 200 / 400
 *   POST   /api/workout-logs                    save the workout I did on a date         200 / 400
 *   GET    /api/workout-logs?from=...&to=...    my logs between two dates (inclusive)    200 / 400
 *   DELETE /api/workout-logs/{date}             delete my log for a date                 204 / 404
 */
@RestController
@RequestMapping("/api")
public class PlanController {

    private static final String AUTH = "Authorization";

    private final PlanHandler handler;

    public PlanController(PlanHandler handler) {
        this.handler = handler;
    }

    @GetMapping("/plans/me")
    public ResponseEntity<WeeklyPlan> getMyPlan(@RequestHeader(name = AUTH, required = false) String auth) {
        return ResponseEntity.ok(handler.getMyPlan(auth));
    }

    @PutMapping("/plans/me/workouts/{day}")
    public ResponseEntity<WeeklyPlan> setDayWorkout(@RequestHeader(name = AUTH, required = false) String auth,
                                                    @PathVariable("day") String day,
                                                    @RequestBody Workout workout) {
        return ResponseEntity.ok(handler.setDayWorkout(auth, day, workout));
    }

    @DeleteMapping("/plans/me/workouts/{day}")
    public ResponseEntity<WeeklyPlan> clearDay(@RequestHeader(name = AUTH, required = false) String auth,
                                               @PathVariable("day") String day) {
        return ResponseEntity.ok(handler.clearDay(auth, day));
    }

    @PostMapping("/workout-logs")
    public ResponseEntity<WorkoutLog> saveLog(@RequestHeader(name = AUTH, required = false) String auth,
                                              @RequestBody SaveWorkoutLogRequest request) {
        return ResponseEntity.ok(handler.saveLog(auth, request));
    }

    @GetMapping("/workout-logs")
    public ResponseEntity<List<WorkoutLog>> getLogs(@RequestHeader(name = AUTH, required = false) String auth,
                                                    @RequestParam(name = "from", required = false) String from,
                                                    @RequestParam(name = "to", required = false) String to) {
        return ResponseEntity.ok(handler.getLogs(auth, from, to));
    }

    @DeleteMapping("/workout-logs/{date}")
    public ResponseEntity<Void> deleteLog(@RequestHeader(name = AUTH, required = false) String auth,
                                          @PathVariable("date") String date) {
        handler.deleteLog(auth, date);
        return ResponseEntity.noContent().build();
    }
}
