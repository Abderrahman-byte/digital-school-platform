import React, { useContext } from 'react'
import PropTypes from 'prop-types'
import { Navigate, useLocation } from 'react-router'

import { AuthContext } from '@Context/AuthContext'

const CompletedProfilesOnly = ({ children }) => {
    const { profile } = useContext(AuthContext)
    const location = useLocation()

    if (profile === undefined) return <></>
    else if (profile === null) return <Navigate to='/profile/create' state={{ from: location?.pathname || '/'}} />
    return <>{children}</>
}

CompletedProfilesOnly.propTypes = {
    children: PropTypes.oneOfType([
        PropTypes.node,
        PropTypes.arrayOf(PropTypes.node)
    ])
}

export default CompletedProfilesOnly