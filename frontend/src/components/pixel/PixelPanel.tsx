import type { CSSProperties, ReactNode } from 'react'
import { uiAssets } from '../../assets/ui'

type PixelPanelProps = {
  children: ReactNode
  className?: string
}

function PixelPanel({ children, className = '' }: PixelPanelProps) {
  const art = uiAssets.panel

  if (art) {
    const slice = art.slice ?? 8
    const style: CSSProperties = {
      borderWidth: slice,
      borderImage: `url(${art.image}) ${slice} fill stretch`,
    }
    return (
      <section className={`px-panel px-panel--drawn ${className}`} style={style}>
        {children}
      </section>
    )
  }

  return <section className={`px-panel ${className}`}>{children}</section>
}

export default PixelPanel
