import { useState } from 'react'
import NavBar, { navTabs } from '../components/NavBar'
import PixelPanel from '../components/pixel/PixelPanel'
import WorkoutsTab from '../components/workouts/WorkoutsTab'

function MainPage() {
  const [active, setActive] = useState(navTabs[0].id)
  const activeTab = navTabs.find((tab) => tab.id === active) ?? navTabs[0]

  return (
    <div className="main-page">
      <NavBar active={active} onSelect={setActive} />

      <main className="main-content">
        <h1>{activeTab.label}</h1>
        <PixelPanel className="main-panel">
          {activeTab.id === 'workouts' ? (
            <WorkoutsTab />
          ) : (
            <div className="content-placeholder">
              <p>MAIN PAGE CONTENT</p>
            </div>
          )}
        </PixelPanel>
      </main>
    </div>
  )
}

export default MainPage
