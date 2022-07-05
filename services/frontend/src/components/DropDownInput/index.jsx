import React, { useState } from 'react'
import PropTypes from 'prop-types'

import { objectClone } from '@Utils/generic'

import './styles.css'

const DropDownInput = ({ onChangeInputCallback, onChoiceChange, label, fieldName, errors }) => {
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

            {dropdownElts.length > 0 ? (
                <div className='dropdown-box'>
                    {dropdownElts.map((elt,i) => <div key={i} onClick={() => onDropDownEltClick(elt)} className='dropdown-elt'>
                        <p>{elt[fieldName]}</p>
                    </div>)}
                </div>
            ) : null}

            {errors.length > 0 ? (
                <div className='field-errors'>
                    {errors.map((err, i) => <p key={i}>{err.message}</p>)}
                </div>
            ) : null}
        </div>
    )
}

DropDownInput.propTypes = {
    onChangeInputCallback: PropTypes.func.isRequired,
    onChoiceChange: PropTypes.func.isRequired,
    label: PropTypes.string.isRequired,
    fieldName: PropTypes.string,
    errors: PropTypes.arrayOf(
        PropTypes.shape({
            field: PropTypes.string.isRequired,
            message: PropTypes.string.isRequired
        })
    )
}

DropDownInput.defaultProps = {
    fieldName: 'name',
    errors: []
}

export default DropDownInput