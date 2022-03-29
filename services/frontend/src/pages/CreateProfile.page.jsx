import React, { useContext } from 'react'
import SchoolProfileForm from '../components/SchoolProfileForm'

import { AuthContext } from '../context/AuthContext'

const CreateProfilePage = () => {
    const { account } = useContext(AuthContext)

    if (account.accountType === 'SCHOOL') {
        return (
            <div className='CreateProfilePage'>
                <SchoolProfileForm />
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