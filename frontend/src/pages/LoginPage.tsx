import { Link } from 'react-router-dom'
import LoginBox from '../components/LoginBox'
import PixelArt from '../components/pixel/PixelArt'

function LoginPage() {
  return (
    <main className="login-page">
      <div className="login-brand">
        <PixelArt asset="logo" alt="BitFit logo" className="login-logo" />
        <PixelArt asset="title" alt="BitFit" fallback={<h1 className="px-title">BITFIT</h1>} />
      </div>

      <LoginBox />

      <Link to="/main" className="dev-link">SKIP TO MAIN PAGE</Link>
    </main>
  )
}

export default LoginPage
