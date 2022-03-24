import React from 'react'
import { Route, Routes } from 'react-router'

import './App.css'
import AuthPages from './pages/Auth.pages'
import NotFoundPage from './pages/NotFound.page'

const App = () => {
	return (
		<Routes>
			<Route index path='/auth/*' element={<AuthPages />}  />
			<Route path='*' element={<NotFoundPage />} />
		</Routes>
	)
}

export default App
