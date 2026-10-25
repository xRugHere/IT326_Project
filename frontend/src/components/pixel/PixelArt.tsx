import type { ReactNode } from 'react'
import { uiAssets } from '../../assets/ui'
import type { UiAssetName } from '../../assets/ui'

type PixelArtProps = {
  asset: UiAssetName
  alt: string
  className?: string
  fallback?: ReactNode
}

function PixelArt({ asset, alt, className, fallback = null }: PixelArtProps) {
  const art = uiAssets[asset]
  if (!art) return <>{fallback}</>
  return <img src={art.image} alt={alt} className={className} />
}

export default PixelArt
