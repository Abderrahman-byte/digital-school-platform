import React, { useCallback, useState } from 'react'
import PropTypes from 'prop-types'

import DropDownInput from '@Components/DropDownInput'
import { searchForLocation } from '@Utils/api'
import { schoolProfileFields, schoolProfileRules, validateForm } from '@Utils/forms'

import './styles.css'
// TODO : This component need to handle updates too

const SchoolProfileForm = ({ onSubmitCallback, errors, setErrors }) => {
    const [locationId, setLocationId] = useState(null)
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
            overview: elements.overview.value !== '' ? elements.overview.value : null,
            cityId: locationId
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
            <div className='form-div'>
                <label className='form-label'>School Name</label>
                <input type='text' name='name' className={`input-elt ${fieldHasErrors('name') ? 'hasErrors' : ''}`} autoComplete='off' />
                
                {fieldHasErrors('name') ? (
                    <div className='field-errors'>
                        {getFieldErrors('name').map((err, i) => <p key={i}>{err.message}</p>)}
                    </div>
                ) : null}
            </div>

            <div className='form-div'>  
                <label className='form-label'>Subtitle</label>
                <input type='text' name='subtitle' className={`input-elt ${fieldHasErrors('subtitle') ? 'hasErrors' : ''}`}  autoComplete='off' />

                {fieldHasErrors('subtitle') ? (
                    <div className='field-errors'>
                        {getFieldErrors('subtitle').map((err, i) => <p key={i}>{err.message}</p>)}
                    </div>
                ) : null}
            </div>

            <div className='form-div'>
            <label className='form-label'>Location</label>
                <DropDownInput errors={getFieldErrors('location')} label='' onChoiceChange={locationChoosed} onChangeInputCallback={searchLocation} fieldName='fullname' />
            </div>

            <div className='form-div'>
                <label className='form-label'>Overview</label>
                <textarea name='overview' className='input-elt' />
            </div>

            <button type='submit' className='btn btn-blue'>Save Profile</button>
        </form>
    )
}

SchoolProfileForm.propTypes = {
    onSubmitCallback: PropTypes.func.isRequired
}

export default SchoolProfileForm
