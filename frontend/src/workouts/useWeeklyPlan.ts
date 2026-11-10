import { useEffect, useState } from 'react'
import { dayOf } from './types'
import type { Day, Workout, WorkoutLog, WorkoutPlan } from './types'

// TODO: swap localStorage for the back end (GET /api/plans/me, PUT/DELETE /api/plans/me/workouts/{day},
// POST/GET /api/workout-logs) once it runs. Components only use this hook, so nothing else changes.

const PLAN_KEY = 'bitfit.workoutPlan'
const LOGS_KEY = 'bitfit.workoutLogs'

function load<T>(key: string, fallback: T): T {
  try {
    const saved = localStorage.getItem(key)
    return saved ? (JSON.parse(saved) as T) : fallback
  } catch {
    return fallback
  }
}

function store(key: string, value: unknown) {
  try {
    localStorage.setItem(key, JSON.stringify(value))
  } catch {
    // Storage can be blocked (private windows); the data just won't survive a refresh.
  }
}

export function useWeeklyPlan() {
  const [plan, setPlan] = useState<WorkoutPlan>(() => load(PLAN_KEY, { days: {} }))
  const [logs, setLogs] = useState<Record<string, WorkoutLog>>(() => load(LOGS_KEY, {}))

  useEffect(() => store(PLAN_KEY, plan), [plan])
  useEffect(() => store(LOGS_KEY, logs), [logs])

  function setDayWorkout(day: Day, workout: Workout) {
    setPlan((current) => ({ days: { ...current.days, [day]: workout } }))
  }

  function clearDay(day: Day) {
    setPlan((current) => {
      const days = { ...current.days }
      delete days[day]
      return { days }
    })
  }

  /** One log per date; saving the same date again replaces it. */
  function saveLog(date: string, workout: Workout) {
    setLogs((current) => ({
      ...current,
      [date]: { date, dayOfWeek: dayOf(date), workout, completedAt: Date.now() },
    }))
  }

  /** The most recent logs, newest first. */
  function latestLogs(count: number): WorkoutLog[] {
    return Object.values(logs)
      .sort((a, b) => b.date.localeCompare(a.date))
      .slice(0, count)
  }

  return { plan, logs, setDayWorkout, clearDay, saveLog, latestLogs }
}
