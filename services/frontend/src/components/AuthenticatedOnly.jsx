import React, { useContext } from 'react'
import { Navigate, useLocation } from 'react-router'
import PropTypes from 'prop-types'

import { AuthContext } from '../context/AuthContext'

const AuthenticatedOnly = ({ children, loginUrl }) => {
    const { account } = useContext(AuthContext)
    const location = useLocation()

    if (account === undefined) {
        return (<></>)
    } else if (account === null) {
        return (<Navigate to={loginUrl} state={{ from: location?.pathname || '/'}} />)
    }

    return <>{children}</>
}

AuthenticatedOnly.propTypes = {
    children: PropTypes.oneOfType([
        PropTypes.arrayOf(PropTypes.node),
        PropTypes.node
    ]),
    loginUrl: PropTypes.string
}

AuthenticatedOnly.defaultProps = {
    loginUrl: '/auth/login'
}

export default AuthenticatedOnly