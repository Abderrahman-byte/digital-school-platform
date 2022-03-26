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
		return 'Something went wrong, please try again another time.'
	} else if (err === 'authentication_required') {
		return 'You must login first to perform this action.'
	}

	return err
}

export const translateErrors = (errors) => errors.map(err => translateError(err))