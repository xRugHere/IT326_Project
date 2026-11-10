import PixelButton from '../pixel/PixelButton'
import { DAYS, dayLabel } from '../../workouts/types'
import type { Day, WorkoutPlan } from '../../workouts/types'

type WeekViewProps = {
  plan: WorkoutPlan
  today: Day
  loggedToday: boolean
  onEditDay: (day: Day) => void
}

function WeekView({ plan, today, loggedToday, onEditDay }: WeekViewProps) {
  return (
    <ol className="week-grid">
      {DAYS.map((day) => {
        const workout = plan.days[day]
        const sets = workout?.exercises.reduce((total, exercise) => total + exercise.sets.length, 0) ?? 0
        return (
          <li key={day} className={`day-card${day === today ? ' day-card--today' : ''}`}>
            <div className="day-card-label">
              {dayLabel(day)}
              {day === today && <span>{loggedToday ? ' · DONE' : ' · TODAY'}</span>}
            </div>
            {workout ? (
              <>
                <strong>{workout.name}</strong>
                <ul className="day-card-exercises">
                  {workout.exercises.map((exercise, i) => (
                    <li key={i}>
                      {exercise.name} ×{exercise.sets.length}
                    </li>
                  ))}
                </ul>
                <small>
                  {workout.exercises.length} exercises · {sets} sets
                </small>
              </>
            ) : (
              <strong className="day-card-rest">REST</strong>
            )}
            <PixelButton label={workout ? 'EDIT' : 'PLAN'} onClick={() => onEditDay(day)} />
          </li>
        )
      })}
    </ol>
  )
}

export default WeekView
