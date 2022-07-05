import { buildPath } from "./generic"
import { getRequest, postRequest } from "./http"

export const DEFAULT_API_ERROR = 'Something went wrong, please try again another time.'
export const apiPrefix = process.env.REACT_APP_API_PREFIX
export const apiHost = process.env.REACT_APP_API_HOST || window.location.href

export const getApiUrl = (path) => {
    const url = new URL(apiHost)
    url.pathname = buildPath(apiPrefix, path)
    return url.href
}

export const sendLoginRequest = async (username, password) => {
    try {
        const response = await postRequest(getApiUrl('/auth/login'), JSON.stringify({ username, password }))

        if (response && response.success && response.data) return [response.data, null]
        else if (response && response.errors) return [null, response.errors]
    } catch {}

    return [null, null]
}

export const createAccount = async (data) => {
    try {
        const response = await postRequest(getApiUrl('/auth/register'), JSON.stringify(data)) 

        if (response && response.success) return [response, null]
        else if (response && response.errors) return [null, response.errors]
    } catch {}

    return [null, null]
}

// Check if user is logged in
export const checkAccount = async () => {
    try {
        const response = await getRequest(getApiUrl('/auth/isLoggedIn'))

        if (response && response.isLoggedIn && response.data) return response.data
    } catch {}

    return null
}

export const searchForLocation = async (query) => {
    try {
        const response = await getRequest(getApiUrl('/social/search/city') + `?query=${encodeURIComponent(query)}`)

        if (response && response.success && response.data) return response.data
    } catch {}

    return []
}

export const createProfile = async (data) => {
    try {
        const response = await postRequest(getApiUrl('/social/profile'), JSON.stringify(data))

        if (response && response.success) return [true, null]
        else if (response && response.errors) return [false, response.errors]
    } catch {}

    return [null, null]
}
