import React, { useCallback, useState } from 'react'
import PropTypes from 'prop-types'

import { searchForLocation } from '../utils/api'
import DropDownInput from './DropDownInput'
import { schoolProfileFields, schoolProfileRules, validateForm } from '../utils/forms'
import GenericHeader from './GenericHeader'

import '../styles/SchoolProfile.css'
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

        <div>
              <GenericHeader />

    
        <form onSubmit={beforeSubmit} className='SchoolProfileFormCard'>
           <div className='Header'> 
                <h2 className='title'>School Profile</h2> 
           </div>  

            <div className='formDiv'>
                <br />
                <div className='Name'>School Name</div>
                <br />
                <input type='text' name='name' className={`input-elt ${fieldHasErrors('name') ? 'hashErrors' : ''}`} autoComplete='off' />
                
                {fieldHasErrors('name') ? (
                    <div className='field-errors'>
                        {getFieldErrors('name').map((err, i) => <p key={i}>{err.message}</p>)}
                    </div>
                ) : null}
            </div>

            <div className='formDiv'>  
                <div className='Name'>Subtitle</div>
                <br />
                <input type='text' name='subtitle' className={`input-elt ${fieldHasErrors('subtitle') ? 'hashErrors' : ''}`}  autoComplete='off' />

                {fieldHasErrors('subtitle') ? (
                    <div className='field-errors'>
                        {getFieldErrors('subtitle').map((err, i) => <p key={i}>{err.message}</p>)}
                    </div>
                ) : null}
            </div>

            <div className='formDiv'>
            <div className='Name'>Location</div>
                <br />
         <DropDownInput errors={getFieldErrors('location')} label='' onChoiceChange={locationChoosed} onChangeInputCallback={searchLocation} fieldName='fullname' />
            </div>

            <div className='formDiv'>
             <div className='Name'>Overview</div>
                <br />
                <textarea name='overview' className='input-elt' />
            </div>
            <div className='formDiv'>
            <br />
            <button type='submit' className='Savebtn'>Save Profile</button>
            </div>
        </form>
        </div>
    )
}

SchoolProfileForm.propTypes = {
    onSubmitCallback: PropTypes.func.isRequired
}

export default SchoolProfileForm
