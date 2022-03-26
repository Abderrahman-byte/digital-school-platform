import React, { useState } from 'react'

import GenericForm from '../components/GenericForm'
import { sendLoginRequest } from '../utils/api'
import { loginFields } from '../utils/forms'
import { translateErrors } from '../utils/generic'

const LoginPage = () => {
    const [errors, setErrors] = useState([])

    const onSubmitCallback = async (data) => {
        setErrors([])

        const [userData, authErrors] = await sendLoginRequest(data.username, data.password)

        if (!userData && authErrors && authErrors.length > 0) {
            setErrors(translateErrors(authErrors))
            return
        } else if (!userData) {
            setErrors(['Something went wrong, please try again another time.'])
            return
        }

        console.log(userData)
    }

    return (
        <div className='LoginPage'>
            <GenericForm submiBtnText='Sign In' fields={loginFields} onSubmitCallback={onSubmitCallback} />

            {errors.length > 0 ? (
                <div className='errors-div'>
                    {errors.map((err, i) => <p key={i}>{err}</p>)}
                </div>
            ) : null}
        </div>
    )
}

export default LoginPage