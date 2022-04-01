import React, { useEffect, useState } from 'react'

import { searchForLocation } from '../utils/api'

import '../styles/forms.css'
import DropDownInput from './DropDownInput'

const SchoolProfileForm = () => {
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

    const saveSchoolProfile = (e) => {
        const elements = e.target.elements

        const data = {
            name: elements.name.value,
            subtitle: elements.subtitle.value,
            overview: elements.overview.value,
            location: locationId
        }

        console.log(data)

        e.preventDefault()
    }

    return (
        <form onSubmit={saveSchoolProfile} className='SchoolProfileForm form'>
            <h2>School Profile</h2>

            <div className='form-div'>
                <input type='text' name='name' className='input-elt' placeholder='School Name' autoComplete='off' />
            </div>

            <div className='form-div'>
                <input type='text' name='subtitle' className='input-elt' placeholder='Subtitle' autoComplete='off' />
            </div>

            <DropDownInput label='Location' onChoiceChange={locationChoosed} onChangeInputCallback={searchLocation} fieldName='fullname' />

            <div className='form-div'>
                <textarea name='overview' className='input-elt' placeholder='overview' />
            </div>

            <button type='submit' className='submit-btn'>Save Profile</button>
        </form>
    )
}

export default SchoolProfileForm
