import { useNavigate } from 'react-router-dom'
import { signOut } from 'firebase/auth'
import { auth } from '../firebase'
import PixelArt from './pixel/PixelArt'
import PixelButton from './pixel/PixelButton'
import type { UiAssetName } from '../assets/ui'

export type NavTab = {
  id: string
  label: string
  asset: UiAssetName
}

export const navTabs: NavTab[] = [
  { id: 'home', label: 'HOME', asset: 'navHome' },
  { id: 'workouts', label: 'WORKOUTS', asset: 'navWorkouts' },
  { id: 'challenges', label: 'CHALLENGES', asset: 'navChallenges' },
  { id: 'profile', label: 'PROFILE', asset: 'navProfile' },
]

type NavBarProps = {
  active: string
  onSelect: (id: string) => void
}

function NavBar({ active, onSelect }: NavBarProps) {
  const navigate = useNavigate()

  async function handleLogout() {
    await signOut(auth)
    navigate('/')
  }

  return (
    <header className="main-header">
      <div className="main-brand">
        <PixelArt asset="logo" alt="" />
        <PixelArt asset="title" alt="BitFit" fallback={<span>BITFIT</span>} />
      </div>

      <nav aria-label="Main" className="main-nav">
        {navTabs.map((tab) => (
          <PixelButton
            key={tab.id}
            label={tab.label}
            asset={tab.asset}
            variant={tab.id === active ? 'accent' : 'idle'}
            active={tab.id === active}
            onClick={() => onSelect(tab.id)}
          />
        ))}
      </nav>

      <PixelButton label="LOG OUT" asset="logoutButton" variant="danger" onClick={handleLogout} />
    </header>
  )
}

export default NavBar
