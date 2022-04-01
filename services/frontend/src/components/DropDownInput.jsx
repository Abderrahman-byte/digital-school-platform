import React, { useState } from 'react'
import PropTypes from 'prop-types'

import { objectClone } from '../utils/generic'

import '../styles/DropDownInput.css'

const DropDownInput = ({ onChangeInputCallback, onChoiceChange, label, fieldName }) => {
    const [dropdownElts, setDropdownElts] = useState([])
    const [inputValue, setInputValue] = useState('')

    const onInputChange = async (e) => {
        const value = e.target.value

        setInputValue(value)

        if (value === '') {
            setDropdownElts([])
            onChangeInputCallback(null)
            onChoiceChange(null)
            return
        }

        const data = await onChangeInputCallback(value)

        setDropdownElts(objectClone(data))
    }

    const onDropDownEltClick = (elt) => {
        onChoiceChange(elt.id)
        setInputValue(elt[fieldName])
        setDropdownElts([])
    }

    return (
        <div className='DropDownInput form-div'>
            <input onChange={onInputChange} value={inputValue} className='input-elt dropdown-input' placeholder={label} autoComplete='off' />

            <div className='dropdown-box'>
                {dropdownElts.map(elt => <div onClick={() => onDropDownEltClick(elt)} className='dropdown-elt'>
                    <p>{elt[fieldName]}</p>
                </div>)}
            </div>
        </div>
    )
}

DropDownInput.propTypes = {
    onChangeInputCallback: PropTypes.func.isRequired,
    onChoiceChange: PropTypes.func.isRequired,
    label: PropTypes.string.isRequired,
    fieldName: PropTypes.string
}

DropDownInput.defaultProps = {
    fieldName: 'name'
}

export default DropDownInput