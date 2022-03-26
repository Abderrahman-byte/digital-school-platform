import React, { useState } from 'react'

import GenericForm from '../components/GenericForm'
import { registerFiedds } from '../utils/forms'

import '../styles/RegisterPage.css'

const RegisterPage = () => {
    const [errors, setErrors] = useState([])
    
    const onSubmitCallback = (data) => {
        console.log(data)
    }

    return (
        <div className='RegisterPage LoginPage'>
            <div className='form-container form-card'>
                <GenericForm fields={registerFiedds} onSubmitCallback={onSubmitCallback} />
            </div>
        </div>
    )
}

export default RegisterPage