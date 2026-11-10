package com.bitfit.plan;

import com.bitfit.model.Workout;

/**
 * JSON body for POST /api/workout-logs. The date comes from the user's device ("2026-11-09")
 * so "today" matches their time zone, not the server's.
 */
public record SaveWorkoutLogRequest(
        String date,
        Workout workout) {
}
