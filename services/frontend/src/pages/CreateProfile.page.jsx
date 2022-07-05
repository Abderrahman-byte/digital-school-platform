import React, { useContext, useState } from 'react'
import { useNavigate, useLocation, Navigate } from 'react-router'

import { AuthContext } from '@Context/AuthContext'
import SchoolProfileForm from '@Components/SchoolProfileForm'
import { createProfile, DEFAULT_API_ERROR } from '@Utils/api'
import { translateErrors } from '@Utils/generic'

import '@Styles/CreateProfilePage.css'

// FIXME: This page should be accessed by uncompleted accounts otherwise they should be redirected to update there account

const CreateProfilePage = () => {
    const [errors, setErrors] = useState([])
    const { account, setProfile, profile } = useContext(AuthContext)

    const navigate = useNavigate()
    const location = useLocation()
    const fromUrl = location?.state?.from || '/'

    const submitProfile = async (data) => {
        setErrors([])
        const [created, errors] = await createProfile(data)

        if (!created && errors) return setErrors(translateErrors(errors))
        if (!created) return setErrors([DEFAULT_API_ERROR])

        setProfile(undefined)
        navigate(fromUrl)
        setProfile(data)
    }

    if (profile === undefined) return <></>

    if (profile) return (<Navigate to='/profile/edit' />)

    if (account.accountType === 'SCHOOL') {
        return (
            <div className='CreateProfilePage'>
                <div className='form-card'>
                    <div className='form-header'>
                        <h3>Create School Profile</h3>
                    </div>

                    <SchoolProfileForm errors={errors} setErrors={setErrors} onSubmitCallback={submitProfile} />
                </div>
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