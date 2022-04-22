import React from 'react'
import { Route, Routes } from 'react-router'

import AuthPages from './pages/Auth.pages'
import MainPages from './pages/Main.pages'
import AuthenticatedOnly from './components/AuthenticatedOnly'
import CreateProfilePage from './pages/CreateProfile.page'
import CompletedProfilesOnly from './components/CompletedProfilesOnly'

import './App.css'

const App = () => {
	return (
		<Routes>
			<Route index path='/auth/*' element={<AuthPages />}  />
			<Route path='/profile/create' element={(
				<AuthenticatedOnly>
					<CreateProfilePage />
				</AuthenticatedOnly>
			)} />
			<Route path='*' element={(
				<AuthenticatedOnly>
					<CompletedProfilesOnly>
						<MainPages />
					</CompletedProfilesOnly>
				</AuthenticatedOnly>
			)} />
		</Routes>
	)
}

export default App
