// Mirrors the back-end models in com.bitfit.model so the same JSON works once the API is wired up.

export const DAYS = ['MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY'] as const
export type Day = (typeof DAYS)[number]

export type ExerciseSet = {
  reps: number
  weightLbs: number
}

export type Exercise = {
  name: string
  sets: ExerciseSet[]
}

export type Workout = {
  name: string
  exercises: Exercise[]
}

/** Repeats every week. A day missing from `days` is a rest day. */
export type WorkoutPlan = {
  days: Partial<Record<Day, Workout>>
}

export type WorkoutLog = {
  date: string // "2026-11-09"
  dayOfWeek: Day
  workout: Workout
  completedAt: number
}

/** Today's date as "YYYY-MM-DD" in the user's own time zone. */
export function todayIso(): string {
  const now = new Date()
  const month = String(now.getMonth() + 1).padStart(2, '0')
  const day = String(now.getDate()).padStart(2, '0')
  return `${now.getFullYear()}-${month}-${day}`
}

export function dayOf(isoDate: string): Day {
  const [year, month, day] = isoDate.split('-').map(Number)
  const jsDay = new Date(year, month - 1, day).getDay() // 0 = Sunday
  return DAYS[(jsDay + 6) % 7]
}

export function dayLabel(day: Day): string {
  return day.slice(0, 3)
}
