import React, { useState } from 'react'

import GenericForm from '../components/GenericForm'
import { registerFields, registerRules } from '../utils/forms'
import { createAccount } from '../utils/api'

import '../styles/RegisterPage.css'
import { translateErrors } from '../utils/generic'

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
        } else if (authData && authData.ok) {
            setMessages(['Your account has been created.\nPlease check your inbox to verify your email.'])
            setTimeout(() => setMessages([]), 5000)
        } else {
            setErrors(['Something went wrong, please try again another time.'])
        }
    }

    return (
        <div className='RegisterPage LoginPage'>
            <div className='form-container form-card'>
                <GenericForm rules={registerRules} fields={registerFields} onSubmitCallback={onSubmitCallback} />
                
                
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