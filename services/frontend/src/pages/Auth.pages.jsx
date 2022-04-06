import React from 'react'
import {Navigate, Route, Routes } from 'react-router'

import LoginPage from './login.page'
import NotFoundPage from './NotFound.page'
import RegisterPage from './Register.page'

import AuthPage from '../styles/AuthPage.css';
import Layout from './Layout'


const AuthPages = () => {
    return (
        <div className='AuthPages'>
         <Layout />  
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