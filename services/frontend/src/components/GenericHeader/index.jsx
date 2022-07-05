import React from 'react'
import PropTypes from 'prop-types'
import { Link, NavLink } from 'react-router-dom'

import AppLogo from '@Assets/neogenia-logo1.png'

import './styles.css'

const generateNavLink = (nav, i) => {
    return (
        <NavLink key={i} className={`nav-elt ${nav.className || ''}`} to={nav.to}>{nav.content}</NavLink>
    )
}

const GenericHeader = ({ navigation }) => {
    return (
        <header className='App-Header'>
            <div className='left-side'>
                <Link className='logo' to='/'>
                    <img src={AppLogo} />
                </Link>
            </div>

            <div className='right-side'>    </div>
        </header>
    )
}

GenericHeader.propTypes = {
    navigation: PropTypes.arrayOf(
        PropTypes.shape({
            content: PropTypes.string.isRequired,
            to: PropTypes.string.isRequired,
            className: PropTypes.string
        })
    )
}

GenericHeader.defaultProps = {
    navigation: []
}


export default GenericHeader