// really basic login component

import { useState } from 'react'
import type { FormEvent } from 'react'
import { useNavigate } from 'react-router-dom'
import { createUserWithEmailAndPassword, signInWithEmailAndPassword } from 'firebase/auth'
import { auth } from '../firebase'
import PixelPanel from './pixel/PixelPanel'
import PixelButton from './pixel/PixelButton'

function LoginBox() {
  const navigate = useNavigate()
  const [mode, setMode] = useState<'login' | 'signup'>('login')
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [confirm, setConfirm] = useState('')
  const [error, setError] = useState('')
  const isSignup = mode === 'signup'

  function switchMode(next: 'login' | 'signup') {
    setMode(next)
    setError('')
  }

  // error returns firebase error message, if any occurs during sign up (could be weak password, email already in use, etc.)
  async function handleSubmit(e: FormEvent) {
    e.preventDefault()
    setError('')
    if (isSignup && password !== confirm) {
      setError('Passwords do not match')
      return
    }
    try {
      if (isSignup) await createUserWithEmailAndPassword(auth, email, password)
      else await signInWithEmailAndPassword(auth, email, password)
      navigate('/main')
    } catch (err) {
      setError((err as Error).message)
    }
  }

  return (
    <PixelPanel className="login-box">
      <form onSubmit={handleSubmit} className="login-form">
        <div className="login-tabs">
          <PixelButton
            label="LOG IN"
            asset="loginTab"
            variant={isSignup ? 'sunken' : 'accent'}
            active={!isSignup}
            onClick={() => switchMode('login')}
          />
          <PixelButton
            label="SIGN UP"
            asset="signupTab"
            variant={isSignup ? 'accent' : 'sunken'}
            active={isSignup}
            onClick={() => switchMode('signup')}
          />
        </div>

        <div className="px-field">
          <label htmlFor="bf-email" className="px-label">EMAIL</label>
          <input id="bf-email" className="px-input" type="email" value={email} onChange={(e) => setEmail(e.target.value)} />
        </div>

        <div className="px-field">
          <label htmlFor="bf-password" className="px-label">PASSWORD</label>
          <input id="bf-password" className="px-input" type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
        </div>

        {isSignup && (
          <div className="px-field px-fade-in">
            <label htmlFor="bf-confirm" className="px-label">CONFIRM PASSWORD</label>
            <input id="bf-confirm" className="px-input" type="password" value={confirm} onChange={(e) => setConfirm(e.target.value)} />
          </div>
        )}

        {error && <p className="px-error">{error}</p>}

        <PixelButton
          type="submit"
          label={isSignup ? 'CREATE ACCOUNT' : 'LOG IN'}
          asset={isSignup ? 'signupButton' : 'loginButton'}
          variant="primary"
        />
      </form>
    </PixelPanel>
  )
}

export default LoginBox
