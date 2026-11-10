package com.bitfit.plan;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.bitfit.account.AccountService;
import com.bitfit.auth.AuthenticatedUser;
import com.bitfit.auth.TokenVerifier;
import com.bitfit.model.WeeklyPlan;
import com.bitfit.model.Workout;
import com.bitfit.model.WorkoutLog;

/**
 * HANDLER layer: works out whose account the request is for (from their login token), and turns
 * path and query strings into days and dates for the service.
 */
@Component
public class PlanHandler {

    private final PlanService service;
    private final AccountService accountService;
    private final TokenVerifier tokenVerifier;

    public PlanHandler(PlanService service, AccountService accountService, TokenVerifier tokenVerifier) {
        this.service = service;
        this.accountService = accountService;
        this.tokenVerifier = tokenVerifier;
    }

    public WeeklyPlan getMyPlan(String authorizationHeader) {
        return service.getPlan(accountId(authorizationHeader));
    }

    public WeeklyPlan setDayWorkout(String authorizationHeader, String day, Workout workout) {
        return service.setDayWorkout(accountId(authorizationHeader), parseDay(day), workout);
    }

    public WeeklyPlan clearDay(String authorizationHeader, String day) {
        return service.clearDay(accountId(authorizationHeader), parseDay(day));
    }

    public WorkoutLog saveLog(String authorizationHeader, SaveWorkoutLogRequest request) {
        String accountId = accountId(authorizationHeader);
        if (request == null) {
            throw badRequest("Request body is required");
        }
        return service.saveLog(accountId, parseDate(request.date(), "date"), request.workout());
    }

    public List<WorkoutLog> getLogs(String authorizationHeader, String from, String to) {
        return service.getLogs(accountId(authorizationHeader), parseDate(from, "from"), parseDate(to, "to"));
    }

    public void deleteLog(String authorizationHeader, String date) {
        service.deleteLog(accountId(authorizationHeader), parseDate(date, "date"));
    }

    /** The account ID of the signed-in user. */
    private String accountId(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Sign in first");
        }
        AuthenticatedUser user = tokenVerifier.verify(authorizationHeader.substring("Bearer ".length()).trim());
        return accountService.getAccountByFirebaseUid(user.uid()).getAccountId();
    }

    static DayOfWeek parseDay(String day) {
        try {
            return DayOfWeek.valueOf(day.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException | NullPointerException e) {
            throw badRequest("Day must be MONDAY through SUNDAY");
        }
    }

    static LocalDate parseDate(String date, String field) {
        if (date == null || date.isBlank()) {
            throw badRequest("'" + field + "' is required (YYYY-MM-DD)");
        }
        try {
            return LocalDate.parse(date.trim());
        } catch (DateTimeParseException e) {
            throw badRequest("'" + field + "' must be a date like 2026-11-09");
        }
    }

    private static ResponseStatusException badRequest(String message) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
    }
}
