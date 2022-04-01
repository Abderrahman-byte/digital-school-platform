import { DEFAULT_API_ERROR } from "./api"

export const buildPath = (...args) => {
	return args
		.map((part, i) => {
			if (i === 0) {
				return part.trim().replace(/[\/]*$/g, '')
			} else {
				return part.trim().replace(/(^[\/]*|[\/]*$)/g, '')
			}
		})
		.filter((x) => x.length)
		.join('/')
}

// Translate errors messages that are not for clients, 
// like: authentication_required => You must login first to perform this action
export const translateError = (err) => {
	if (err === 'unknown_error') {
		return DEFAULT_API_ERROR
	} else if (err === 'authentication_required') {
		return 'You must login first to perform this action.'
	} else if (err === 'email_unverified') {
		return 'You need to verify your email to login.'
	}

	return err
}

export const translateErrors = (errors) => errors.map(err => translateError(err))

export const objectClone = (obj) => JSON.parse(JSON.stringify(obj))