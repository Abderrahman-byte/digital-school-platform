import React, { useContext, useState } from 'react'
import { useLocation, useNavigate } from 'react-router'

import GenericForm from '@Components/GenericForm'
import { AuthContext } from '@Context/AuthContext'
import { DEFAULT_API_ERROR, sendLoginRequest } from '@Utils/api'
import { loginFields } from '@Utils/forms'
import { translateErrors } from '@Utils/generic'
import logo from '@Assets/neogenia-logo1.png'

import '@Styles/forms.css'
import '@Styles/LoginPage.css'

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