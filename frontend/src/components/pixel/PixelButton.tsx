import { useState } from 'react'
import { uiAssets } from '../../assets/ui'
import type { UiAssetName } from '../../assets/ui'

type PixelButtonProps = {
  label: string
  asset?: UiAssetName
  variant?: 'primary' | 'accent' | 'idle' | 'sunken' | 'danger'
  active?: boolean
  type?: 'button' | 'submit'
  onClick?: () => void
}

function PixelButton({ label, asset, variant = 'idle', active = false, type = 'button', onClick }: PixelButtonProps) {
  const [pressed, setPressed] = useState(false)
  const art = asset ? uiAssets[asset] : undefined

  if (art) {
    let src = art.image
    if (pressed && art.pressed) src = art.pressed
    else if (active && art.active) src = art.active

    return (
      <button
        type={type}
        className="px-btn-image"
        onClick={onClick}
        onPointerDown={() => setPressed(true)}
        onPointerUp={() => setPressed(false)}
        onPointerLeave={() => setPressed(false)}
      >
        <img src={src} alt={label} />
      </button>
    )
  }

  return (
    <button type={type} className={`px-btn px-btn--${variant}`} onClick={onClick}>
      {label}
    </button>
  )
}

export default PixelButton
