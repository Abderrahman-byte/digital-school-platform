import React from 'react'
import { Route, Routes } from 'react-router'
import NotFoundPage from './NotFound.page'

const SchoolManagementPages = () => {
    return (
        <Routes>
            <Route path='' element={<div>This must be the feeds page</div>} />
            <Route path='/teachers' element={<div>This must be the teachers page</div>} />
            <Route path='/teachers/requests' element={<div>This must be the teachers requests page</div>} />
            <Route path='/teachers/archive' element={<div>This must be the teachers archive page</div>} />
            <Route path='*' element={<NotFoundPage />} />
        </Routes>
    )
}

export default SchoolManagementPages