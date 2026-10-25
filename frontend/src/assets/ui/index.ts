export type UiAssetName =
  | 'logo'
  | 'title'
  | 'panel'
  | 'loginTab'
  | 'signupTab'
  | 'loginButton'
  | 'signupButton'
  | 'logoutButton'
  | 'navHome'
  | 'navWorkouts'
  | 'navChallenges'
  | 'navProfile'

export type UiAsset = {
  image: string
  pressed?: string
  active?: string
  slice?: number
}

// Example: import loginButton from './loginButton.png' then add  loginButton: { image: loginButton },
export const uiAssets: Partial<Record<UiAssetName, UiAsset>> = {
  logo: { image: '/BitFit_Logo.png' },
}
