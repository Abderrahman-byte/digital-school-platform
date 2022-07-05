import React, { useContext } from 'react'

import { AuthContext } from '@Context/AuthContext'
import SchoolManagementPages from './SchoolManagement.pages'

// The MainPages Component display routes specifique to each account type

const MainPages = () => {
    const { account } = useContext(AuthContext)

    if (account === undefined) return <></>

    if (account.accountType === 'SCHOOL') return <SchoolManagementPages />

    return (
        <div className='MainPages'>
            <h1>Welcome to main pages</h1>
        </div>
    )
}

export default MainPages