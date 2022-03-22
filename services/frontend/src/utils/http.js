const DEFAULT_HEADERS = { 'Content-Type': 'application/json' }

export const httpRequest = async (url, method, body, headers = {}, options = {}) => {
    const response = await fetch(url, {
        credentials: 'include',
        method,
        body,
        headers,
        ...options
    })

    const contentType = response.headers.get('Content-Type') || response.headers.get('content-type')

    const data = /json/.test(contentType) ? await response.json() : response.text()

    return data
}

export const postRequest = async (url, body, headers = {}) => await httpRequest(url, 'POST', body, {...DEFAULT_HEADERS, ...headers})

export const putRequest =  async(url, body, headers = {}) => await httpRequest(url, 'PUT', body, {...DEFAULT_HEADERS, ...headers})

export const getRequest = async (url) => await httpRequest(url, 'GET', null)