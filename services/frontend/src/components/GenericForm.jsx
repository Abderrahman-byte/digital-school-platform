import React, { useState } from 'react'
import PropTypes from 'prop-types'

import '../styles/forms.css'

// This is the functions that generates input fields from gived fields objects
const generateFields = (fields, errors) => {
    return fields.map((field, i) => {
        const fieldErrors = errors.filter(err => err.field === field.name)
        const hasErrors = fieldErrors.length > 0
        let elt

        if (field.type === 'textarea') {
            elt = <textarea name={field.name} className={`input-elt ${hasErrors ? 'hashErrors' : ''}`} placeholder={field.label} />
        }

        elt = <input type={field.type} name={field.name} placeholder={field.label} className={`input-elt ${hasErrors ? 'hashErrors' : ''}`} autoComplete='off' />

        return (
            <div key={i} className='form-div'>
                {elt}

                {hasErrors ? (
                    <div className='field-errors'>
                        {fieldErrors.map((err, i) => <p key={i}>{err.message}</p>)}
                    </div>
                ): null}
            </div>
        )
    })
}

/**
* GenericForm component is used generate standard forms from fields objects
* It also validates the data by using rules objects if provided
* It may not work for all forms, some forms need specifique fonctionality
*/

const GenericForm = ({ className, fields, onSubmitCallback, submiBtnText }) => {
    const [errors, setErrors] = useState([])

    // Checking the data before submiting them to onSubmitCallback
    const beforeSubmit = (e) => {
        const localErrors = []
        const elements = e.target.elements
        const data = {}

        e.preventDefault()
        setErrors([])

        fields.forEach(field => {
            const value = elements[field.name]?.value

            if (field.isRequired && (!value || value === '')) {
                localErrors.push({ field: field.name, message: `The ${field.name} is required .`})
                return
            }

            data[field.name] = value
        })

        if (localErrors.length > 0) {
            setErrors(localErrors)
            return
        }

        if (typeof onSubmitCallback === 'function') onSubmitCallback(data)
    }

    return (
        <form onSubmit={beforeSubmit} className={`GenericForm form ${className ? className + '' : ''}`}>
            {generateFields(fields, errors)}

            <button className='submit-btn'>{submiBtnText}</button>
        </form>
    )
}

GenericForm.propTypes = {
	className: PropTypes.string,
	onSubmitCallback: PropTypes.func.isRequired,
	fields: PropTypes.arrayOf(
		PropTypes.shape({
			name: PropTypes.string.isRequired,
			type: PropTypes.string.isRequired,
			label: PropTypes.string.isRequired,
            isRequired: PropTypes.bool.isRequired
		})
	).isRequired,
    submiBtnText: PropTypes.string
}

GenericForm.defaultProps = {
    className: '',
    submiBtnText: 'Submit'
}

export default GenericForm