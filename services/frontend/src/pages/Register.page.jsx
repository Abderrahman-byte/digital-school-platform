import React, { useState } from 'react'

import GenericForm from '@Components/GenericForm'
import { registerFields, registerRules } from '@Utils/forms'
import { createAccount, DEFAULT_API_ERROR } from '@Utils/api'
import { translateErrors } from '@Utils/generic'
import AppLogo from '@Assets/neogenia-logo1.png'

import '@Styles/RegisterPage.css'

const RegisterPage = () => {
    const [errors, setErrors] = useState([])
    const [messages, setMessages] = useState([])
    
    const onSubmitCallback = async (data) => {
        setMessages([])

        if (data.password !== data.password2) {
            setErrors(['Passwords doesn\'t match.'])
            return
        }

        setErrors([])

        const [authData, authErrors] = await createAccount(data)

        if (!authData && authErrors) {
            setErrors(translateErrors(authErrors))
        } else if (authData && authData.success) {
            setMessages(['Your account has been created.\nPlease check your inbox to verify your email.'])
            setTimeout(() => setMessages([]), 5000)
        } else {
            setErrors([DEFAULT_API_ERROR])
        }
    }

    return (
        <div className='RegisterPage LoginPage'>
            <div className='form-container form-card'>
                <div className='form-header'>
                    <img src={AppLogo} alt='neogenia logo' className='form-logo' />
                </div>

                <GenericForm submiBtnText='Sign Up' rules={registerRules} fields={registerFields} onSubmitCallback={onSubmitCallback} />
                
                
                {errors.length > 0 ? (
                    <div className='errors-div'>
                        {errors.map((err, i) => <p key={i}>{err}</p>)}
                    </div>
                ) : null}

                {messages.length > 0 ? (
                    <div className='messages-div'>
                        {messages.map((msg, i) => <p key={i}>{msg}</p>)}
                    </div>
                ) : null}
            </div>
        </div>
    )
}

export default RegisterPage