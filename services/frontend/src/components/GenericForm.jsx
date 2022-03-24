import React from 'react'

const GenericForm = ({ className = '' }) => {
    return (
        <form className={`GenericForm form${className ? className + '' : ''}`}>

        </form>
    )
}

export default GenericForm