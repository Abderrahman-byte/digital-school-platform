import React, { useCallback, useEffect, useState } from 'react'
import PropTypes from 'prop-types'

import { searchForLocation } from '../utils/api'

import '../styles/forms.css'
import DropDownInput from './DropDownInput'
import { schoolProfileFields, schoolProfileRules, validateForm } from '../utils/forms'

// TODO : This component need to handle updates too

const SchoolProfileForm = ({ onSubmitCallback }) => {
    const [locationId, setLocationId] = useState(null)
    const [errors, setErrors] = useState([])

    const searchLimit = 5
    
    const searchLocation = async (query) => {
        if (!query || query === '') return []

        const data = await searchForLocation(query)

        return (data || []).splice(0, searchLimit)
    }

    const locationChoosed = (id) => {
        if (!id) setLocationId(null)
        else setLocationId(id)
    }

    const beforeSubmit = (e) => {
        const elements = e.target.elements
        const data = {
            name: elements.name.value,
            subtitle: elements.subtitle.value,
            overview: elements.overview.value,
            location: locationId
        }

        const localErrors = validateForm(schoolProfileFields, schoolProfileRules, data)

        e.preventDefault()

        if(localErrors.length > 0) {
            setErrors(localErrors)
            return
        } else setErrors([])

        onSubmitCallback(data)
    }

    const fieldHasErrors = useCallback((fieldName) => {
        return errors.some(err => err.field === fieldName)
    }, [errors])

    const getFieldErrors = useCallback((fieldName) => {
        return errors.filter(err => err.field === fieldName)
    }, [errors])

    return (
        <form onSubmit={beforeSubmit} className='SchoolProfileForm form'>
            <h2>School Profile</h2>

            <div className='form-div'>
                <input type='text' name='name' className={`input-elt ${fieldHasErrors('name') ? 'hashErrors' : ''}`} placeholder='School Name' autoComplete='off' />
                
                {fieldHasErrors('name') ? (
                    <div className='field-errors'>
                        {getFieldErrors('name').map((err, i) => <p key={i}>{err.message}</p>)}
                    </div>
                ) : null}
            </div>

            <div className='form-div'>
                <input type='text' name='subtitle' className={`input-elt ${fieldHasErrors('subtitle') ? 'hashErrors' : ''}`} placeholder='Subtitle' autoComplete='off' />

                {fieldHasErrors('subtitle') ? (
                    <div className='field-errors'>
                        {getFieldErrors('subtitle').map((err, i) => <p key={i}>{err.message}</p>)}
                    </div>
                ) : null}
            </div>

            <DropDownInput errors={getFieldErrors('location')} label='Location' onChoiceChange={locationChoosed} onChangeInputCallback={searchLocation} fieldName='fullname' />

            <div className='form-div'>
                <textarea name='overview' className='input-elt' placeholder='overview' />
            </div>

            <button type='submit' className='submit-btn'>Save Profile</button>
        </form>
    )
}

SchoolProfileForm.propTypes = {
    onSubmitCallback: PropTypes.func.isRequired
}

export default SchoolProfileForm
