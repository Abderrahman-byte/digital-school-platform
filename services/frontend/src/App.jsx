import React from 'react'
import { Route, Routes } from 'react-router'

import AuthPages from '@Pages/Auth.pages'
import MainPages from '@Pages/Main.pages'
import CreateProfilePage from '@Pages/CreateProfile.page'
import AuthenticatedOnly from '@Components/AuthenticatedOnly'
import CompletedProfilesOnly from '@Components/CompletedProfilesOnly'

import '@Styles/App.css'

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
