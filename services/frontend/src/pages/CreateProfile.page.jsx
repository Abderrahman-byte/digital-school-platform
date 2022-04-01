import React, { useContext, useState } from 'react'
import { useNavigate, useLocation } from 'react-router'

import { AuthContext } from '../context/AuthContext'
import SchoolProfileForm from '../components/SchoolProfileForm'
import { createProfile, DEFAULT_API_ERROR } from '../utils/api'

const CreateProfilePage = () => {
    const [errors, setErrors] = useState([])
    const { account, setProfile } = useContext(AuthContext)

    const navigate = useNavigate()
    const location = useLocation()
    const fromUrl = location?.state?.from || '/'

    const submitProfile = async (data) => {
        setErrors([])
        const [created, errors] = await createProfile(data)

        if (!created && errors) return setErrors(errors)
        if (!created) return setErrors([DEFAULT_API_ERROR])

        setProfile(data)
        navigate(fromUrl)
    }

    if (account.accountType === 'SCHOOL') {
        return (
            <div className='CreateProfilePage'>
                <SchoolProfileForm onSubmitCallback={submitProfile} />
            </div>
        )
    }

    return (
        <div className='CreateProfilePage'>
            <h1>Just hold on, we are gonna create your profile.</h1>
        </div>
    )    
}

export default CreateProfilePage