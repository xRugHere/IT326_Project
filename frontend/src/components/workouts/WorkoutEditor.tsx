import { useState } from 'react'
import PixelButton from '../pixel/PixelButton'
import type { Workout } from '../../workouts/types'

// Inputs are kept as strings while editing so a field can be blank; they become numbers on save.
type DraftSet = { reps: string; weightLbs: string }
type DraftExercise = { name: string; sets: DraftSet[] }

type WorkoutEditorProps = {
  title: string
  initial?: Workout
  saveLabel: string
  onSave: (workout: Workout) => void
  onCancel: () => void
  onClear?: () => void
  clearLabel?: string
}

const emptySet = (): DraftSet => ({ reps: '', weightLbs: '' })
const emptyExercise = (): DraftExercise => ({ name: '', sets: [emptySet()] })

function toDraft(workout?: Workout): { name: string; exercises: DraftExercise[] } {
  if (!workout) return { name: '', exercises: [emptyExercise()] }
  return {
    name: workout.name,
    exercises: workout.exercises.map((exercise) => ({
      name: exercise.name,
      sets: exercise.sets.map((set) => ({ reps: String(set.reps), weightLbs: String(set.weightLbs) })),
    })),
  }
}

/** Same rules as PlanService.validateWorkout on the back end. */
function validate(name: string, exercises: DraftExercise[]): { workout?: Workout; problems: string[] } {
  const problems: string[] = []
  if (!name.trim()) problems.push('Give the workout a name')
  if (exercises.length === 0) problems.push('Add at least one exercise')

  const workout: Workout = {
    name: name.trim(),
    exercises: exercises.map((exercise, i) => {
      const label = exercise.name.trim() || `Exercise ${i + 1}`
      if (!exercise.name.trim()) problems.push(`${label} needs a name`)
      if (exercise.sets.length === 0) problems.push(`${label} needs at least one set`)
      return {
        name: exercise.name.trim(),
        sets: exercise.sets.map((set, s) => {
          const reps = Number(set.reps)
          const weightLbs = set.weightLbs.trim() === '' ? 0 : Number(set.weightLbs)
          if (!Number.isInteger(reps) || reps < 1) problems.push(`${label} set ${s + 1}: enter reps`)
          if (!Number.isFinite(weightLbs) || weightLbs < 0) problems.push(`${label} set ${s + 1}: weight can't be negative`)
          return { reps, weightLbs }
        }),
      }
    }),
  }
  return problems.length ? { problems } : { workout, problems }
}

function WorkoutEditor({ title, initial, saveLabel, onSave, onCancel, onClear, clearLabel }: WorkoutEditorProps) {
  const [name, setName] = useState(() => toDraft(initial).name)
  const [exercises, setExercises] = useState(() => toDraft(initial).exercises)
  const [problems, setProblems] = useState<string[]>([])

  function updateExercise(index: number, change: (exercise: DraftExercise) => DraftExercise) {
    setExercises((current) => current.map((exercise, i) => (i === index ? change(exercise) : exercise)))
  }

  function updateSet(exerciseIndex: number, setIndex: number, field: keyof DraftSet, value: string) {
    updateExercise(exerciseIndex, (exercise) => ({
      ...exercise,
      sets: exercise.sets.map((set, i) => (i === setIndex ? { ...set, [field]: value } : set)),
    }))
  }

  /** New sets copy the previous set so the user only changes what's different. */
  function addSet(exerciseIndex: number) {
    updateExercise(exerciseIndex, (exercise) => ({
      ...exercise,
      sets: [...exercise.sets, exercise.sets.length ? { ...exercise.sets[exercise.sets.length - 1] } : emptySet()],
    }))
  }

  function removeSet(exerciseIndex: number, setIndex: number) {
    updateExercise(exerciseIndex, (exercise) => ({
      ...exercise,
      sets: exercise.sets.filter((_, i) => i !== setIndex),
    }))
  }

  function handleSave() {
    const result = validate(name, exercises)
    setProblems(result.problems)
    if (result.workout) onSave(result.workout)
  }

  return (
    <div className="workout-editor">
      <h2>{title}</h2>

      <label className="px-field">
        WORKOUT NAME
        <input
          className="px-input"
          value={name}
          placeholder="e.g. Push Day"
          maxLength={50}
          onChange={(e) => setName(e.target.value)}
        />
      </label>

      {exercises.map((exercise, exerciseIndex) => (
        <fieldset key={exerciseIndex} className="exercise-card">
          <div className="exercise-header">
            <input
              className="px-input"
              aria-label={`Exercise ${exerciseIndex + 1} name`}
              value={exercise.name}
              placeholder="Exercise, e.g. Bench Press"
              maxLength={50}
              onChange={(e) => updateExercise(exerciseIndex, (ex) => ({ ...ex, name: e.target.value }))}
            />
            <PixelButton
              label="REMOVE"
              variant="danger"
              onClick={() => setExercises((current) => current.filter((_, i) => i !== exerciseIndex))}
            />
          </div>

          <table className="set-table">
            <thead>
              <tr>
                <th>SET</th>
                <th>REPS</th>
                <th>WEIGHT (LBS)</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {exercise.sets.map((set, setIndex) => (
                <tr key={setIndex}>
                  <td>{setIndex + 1}</td>
                  <td>
                    <input
                      className="px-input"
                      type="number"
                      inputMode="numeric"
                      min={1}
                      aria-label={`Set ${setIndex + 1} reps`}
                      value={set.reps}
                      onChange={(e) => updateSet(exerciseIndex, setIndex, 'reps', e.target.value)}
                    />
                  </td>
                  <td>
                    <input
                      className="px-input"
                      type="number"
                      inputMode="decimal"
                      min={0}
                      step="any"
                      placeholder="0"
                      aria-label={`Set ${setIndex + 1} weight`}
                      value={set.weightLbs}
                      onChange={(e) => updateSet(exerciseIndex, setIndex, 'weightLbs', e.target.value)}
                    />
                  </td>
                  <td>
                    <PixelButton label="X" onClick={() => removeSet(exerciseIndex, setIndex)} />
                  </td>
                </tr>
              ))}
            </tbody>
          </table>

          <PixelButton label="+ ADD SET" onClick={() => addSet(exerciseIndex)} />
        </fieldset>
      ))}

      <PixelButton label="+ ADD EXERCISE" onClick={() => setExercises((current) => [...current, emptyExercise()])} />

      {problems.length > 0 && (
        <ul className="px-error workout-problems">
          {problems.map((problem) => (
            <li key={problem}>{problem}</li>
          ))}
        </ul>
      )}

      <div className="workout-actions">
        <PixelButton label={saveLabel} variant="primary" onClick={handleSave} />
        <PixelButton label="CANCEL" onClick={onCancel} />
        {onClear && clearLabel && <PixelButton label={clearLabel} variant="danger" onClick={onClear} />}
      </div>
    </div>
  )
}

export default WorkoutEditor
