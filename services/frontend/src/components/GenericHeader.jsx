import React from 'react'
import PropTypes from 'prop-types'
import { NavLink } from 'react-router-dom'

import AppLogo from '../assets/neogenia-logo1.png'

import '../styles/GenericHeader.css'

const generateNavLink = (nav, i) => {
    return (
        <NavLink key={i} className={`nav-elt ${nav.className || ''}`} to={nav.to}>{nav.content}</NavLink>
    )
}

const GenericHeader = ({ navigation }) => {
    return (
        <header className='App-Header'>
            <a className='logo' href='/'>
                <img src={AppLogo} alt='neogenia logo' />
            </a>

            <div className='navigation'>
                <nav className='navbar'>
                    {navigation.map((elt, i) => generateNavLink(elt, i))}
                </nav>
            </div>
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