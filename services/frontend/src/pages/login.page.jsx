import React, { useContext, useState } from 'react'
import { useLocation, useNavigate } from 'react-router'

import GenericForm from '../components/GenericForm'
import logo from '../assets/neogenia-logo1.png'
import { DEFAULT_API_ERROR, sendLoginRequest } from '../utils/api'
import { loginFields } from '../utils/forms'
import { translateErrors } from '../utils/generic'
import { AuthContext } from '../context/AuthContext'

import '../styles/forms.css'
import '../styles/LoginPage.css'

const LoginPage = () => {
    const [errors, setErrors] = useState([])
    const { setAccountData } = useContext(AuthContext)
    const navigate = useNavigate()
    const location = useLocation()
    const fromUrl = location?.state?.from || '/'

     const onSubmitCallback = async (data) => {
        setErrors([])

        const [userData, authErrors] = await sendLoginRequest(data.username, data.password)

        if (!userData && authErrors && authErrors.length > 0) {
            setErrors(translateErrors(authErrors))
            return
        } else if (!userData) {
            setErrors([DEFAULT_API_ERROR])
            return
        }

        setAccountData(userData)
        navigate(fromUrl)
    }

    return (
        <div className='LoginPage'>
            <div className='form-container form-card'>
                <div className='form-header'>
                    <img src={logo} alt='neogenia logo' className='form-logo' />
                </div>

                <GenericForm submiBtnText='Sign In' fields={loginFields} onSubmitCallback={onSubmitCallback} />

                {errors.length > 0 ? (
                    <div className='errors-div'>
                        {errors.map((err, i) => <p key={i}>{err}</p>)}
                    </div>
                      ) : null}
            </div>
        </div>
    )
}

export default LoginPage