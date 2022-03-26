import React from 'react'
import { Navigate, Route, Routes } from 'react-router'
import LoginPage from './login.page'
import NotFoundPage from './NotFound.page'
import RegisterPage from './Register.page'

const AuthPages = () => {
    return (
        <div className='AuthPages'>
            <header>Auth Headers</header>
            <hr />
            <Routes>
                <Route path='' element={<Navigate to='login' />} />
                <Route path='login' element={<LoginPage />} />
                <Route path='sign-up' element={<RegisterPage />} />
                <Route path='*' element={<NotFoundPage />} />
            </Routes>
        </div>
    )
}

export default AuthPages