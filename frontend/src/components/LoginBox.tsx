// really basic login component

import { useEffect, useState } from 'react'
import type { FormEvent } from 'react'
import {
  createUserWithEmailAndPassword,
  onAuthStateChanged,
  signInWithEmailAndPassword,
  signOut,
} from 'firebase/auth'
import type { User } from 'firebase/auth'
import { auth } from '../firebase'

function LoginBox() {
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [user, setUser] = useState<User | null>(null)
  const [error, setError] = useState('')

  useEffect(() => onAuthStateChanged(auth, setUser), [])

  async function handleLogin(e: FormEvent) {
    e.preventDefault()
    setError('')
    try {
      await signInWithEmailAndPassword(auth, email, password)
    } catch (err) {
      setError((err as Error).message)
    }
  }

  // error returns firebase error message, if any occurs during sign up (could be weak password, email already in use, etc.)
  async function handleSignUp() {
    setError('')
    try {
      await createUserWithEmailAndPassword(auth, email, password)
    } catch (err) {
      setError((err as Error).message)
    }
  }

  if (user) {
    return (
      <div>
        <p>Logged in as {user.email}</p>
        <button onClick={() => signOut(auth)}>Log Out</button>
      </div>
    )
  }

  return (
    <form onSubmit={handleLogin}>
      <div>
        <label>
          Email:{' '}
          <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} />
        </label>
      </div>
      <div>
        <label>
          Password:{' '}
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </label>
      </div>
      <button type="submit">Log In</button>
      <button type="button" onClick={handleSignUp}>Sign Up</button>
      {error && <p>{error}</p>}
    </form>
  )
}

export default LoginBox
