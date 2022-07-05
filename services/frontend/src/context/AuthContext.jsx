import React, { createContext, useEffect, useState } from 'react'
import { checkAccount } from '@Utils/api'

export const AuthContext = createContext({})

export const AuthProvider = ({ children }) => {
    const [account, setAccount] = useState(undefined)
    const [profile, setProfile] = useState(undefined)

    const setAccountData = (accountData) => {
        const profileData = accountData?.profile || null

        if (accountData) delete accountData['profile']

        setAccount(accountData || null)
        setProfile(profileData || null)
    }

    const performAuthCheck = async () => {
        const accountData = await checkAccount()
        setAccountData(accountData)
    }

    useEffect(() => {
        performAuthCheck()
    }, [])


    return (
        <AuthContext.Provider value={{ account, profile, setAccountData, setProfile }}>
            {children}
        </AuthContext.Provider>
    )
}