// really basic login component

import { useState } from 'react'
import type { FormEvent } from 'react'

function LoginBox() {
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')

  function handleSubmit(e: FormEvent) {
    e.preventDefault()
    console.log('Login attempt:', username)
  }

  return (
    <form onSubmit={handleSubmit}>
      <div>
        <label>
          Username:{' '}
          <input value={username} onChange={(e) => setUsername(e.target.value)} />
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
    </form>
  )
}

export default LoginBox
