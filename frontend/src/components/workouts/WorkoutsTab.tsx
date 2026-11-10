import { useState } from 'react'
import PixelButton from '../pixel/PixelButton'
import WeekView from './WeekView'
import WorkoutEditor from './WorkoutEditor'
import { dayOf, todayIso } from '../../workouts/types'
import type { Day } from '../../workouts/types'
import { useWeeklyPlan } from '../../workouts/useWeeklyPlan'

type View = { kind: 'week' } | { kind: 'planDay'; day: Day } | { kind: 'logToday' }

function WorkoutsTab() {
  const { plan, logs, setDayWorkout, clearDay, saveLog, latestLogs } = useWeeklyPlan()
  const [view, setView] = useState<View>({ kind: 'week' })
  const [message, setMessage] = useState('')

  const date = todayIso()
  const today = dayOf(date)
  const todaysLog = logs[date]
  const backToWeek = (text = '') => {
    setMessage(text)
    setView({ kind: 'week' })
  }

  if (view.kind === 'planDay') {
    const planned = plan.days[view.day]
    return (
      <WorkoutEditor
        key={view.day}
        title={`PLAN ${view.day}`}
        initial={planned}
        saveLabel="SAVE TO PLAN"
        onSave={(workout) => {
          setDayWorkout(view.day, workout)
          backToWeek(`${view.day} saved to your weekly plan.`)
        }}
        onCancel={() => backToWeek()}
        onClear={
          planned
            ? () => {
                clearDay(view.day)
                backToWeek(`${view.day} is now a rest day.`)
              }
            : undefined
        }
        clearLabel="MAKE REST DAY"
      />
    )
  }

  if (view.kind === 'logToday') {
    // Continue today's log if there is one, otherwise start from today's plan.
    return (
      <WorkoutEditor
        title={`TODAY'S WORKOUT · ${date}`}
        initial={todaysLog?.workout ?? plan.days[today]}
        saveLabel="SAVE WORKOUT"
        onSave={(workout) => {
          saveLog(date, workout)
          backToWeek('Workout saved for today. Nice work!')
        }}
        onCancel={() => backToWeek()}
      />
    )
  }

  const history = latestLogs(5)

  return (
    <div className="workouts-tab">
      <div className="workouts-toolbar">
        <h2>WEEKLY PLAN</h2>
        <PixelButton
          label={todaysLog ? "EDIT TODAY'S WORKOUT" : "START TODAY'S WORKOUT"}
          variant="accent"
          onClick={() => {
            setMessage('')
            setView({ kind: 'logToday' })
          }}
        />
      </div>

      {message && <p role="status">{message}</p>}

      <WeekView
        plan={plan}
        today={today}
        loggedToday={Boolean(todaysLog)}
        onEditDay={(day) => {
          setMessage('')
          setView({ kind: 'planDay', day })
        }}
      />

      <section>
        <h2>RECENT WORKOUTS</h2>
        {history.length === 0 ? (
          <p>No workouts saved yet.</p>
        ) : (
          <ul className="workout-history">
            {history.map((log) => (
              <li key={log.date}>
                <strong>
                  {log.date} · {log.workout.name}
                </strong>
                <ul>
                  {log.workout.exercises.map((exercise, i) => (
                    <li key={i}>
                      {exercise.name}: {exercise.sets.map((set) => `${set.weightLbs}×${set.reps}`).join(', ')}
                    </li>
                  ))}
                </ul>
              </li>
            ))}
          </ul>
        )}
      </section>
    </div>
  )
}

export default WorkoutsTab
