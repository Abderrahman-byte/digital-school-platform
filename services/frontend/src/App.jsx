import React from 'react'
import { Route, Routes } from 'react-router'

import './App.css'
import AuthPages from './pages/Auth.pages'

const App = () => {
	return (
		<Routes>
			<Route path='/auth/*' element={<AuthPages />}  />
		</Routes>
	)
}

export default App
