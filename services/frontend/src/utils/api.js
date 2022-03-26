import { buildPath } from "./generic"
import { getRequest, postRequest } from "./http"

export const apiPrefix = process.env.REACT_APP_API_PREFIX

export const services = {
    authService:process.env.REACT_APP_AUTH_SERVICE_HOST || window.location.origin,
    socialService:process.env.REACT_APP_SOCIAL_SERVICE_HOST || window.location.origin,
    schoolService:process.env.REACT_APP_SCHOOL_SERVICE_HOST || window.location.origin,
}

export const getApiUrl = (serviceOrigin, path) => {
    const url = new URL(serviceOrigin)
    url.pathname = buildPath(apiPrefix, path)
    return url.href
}

export const getAuthApiUrl = (path) => getApiUrl(services.authService, path)
export const getSocialApiUrl = (path) => getApiUrl(services.socialService, path)
export const getSchoolApiUrl = (path) => getApiUrl(services.schoolService, path)

export const sendLoginRequest = async (username, password) => {
    try {
        const response = await postRequest(getAuthApiUrl('/login'), JSON.stringify({ username, password }))

        if (response && response.ok && response.data) return [response.data, null]
        else if (response && response.errors) return [null, response.errors]
    } catch {}

    return [null, null]
}

export const createAccount = async (data) => {
    try {
        const response = await postRequest(getAuthApiUrl('/register'), JSON.stringify(data)) 

        if (response && response.ok) return [response, null]
        else if (response && response.errors) return [null, response.errors]
    } catch {}

    return [null, null]
}

export const checkAccount = async () => {
    try {
        const response = await getRequest(getAuthApiUrl('/isLoggedIn'))

        if (response && response.isLoggedIn && response.data) return response.data
    } catch {}

    return null
}