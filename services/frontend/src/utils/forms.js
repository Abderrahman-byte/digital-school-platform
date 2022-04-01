export const validateForm = (fields, rules, data) => {
    const errors = []

    fields.forEach(field => {
        const value = data[field.name]
        const fieldRules = rules.filter(rule => rule.field === field.name)

        if (field.isRequired && (!value || value === '')) {
            errors.push({ field: field.name, message: `The ${field.label} field is required .`})
            return
        }

        for (let rule of fieldRules) {
            if (!rule.rule.test(value)) {
                errors.push({ field: field.name, message: rule.message })
                break
            }
        }
    })

    return errors
}

export const loginFields = [
    {
        name: 'username',
        type: 'text',
        label: 'Username or Email',
        isRequired: true
    },
    {
        name: 'password',
        type: 'password',
        label: 'Password',
        isRequired: true
    }
]

export const registerFields = [
    {
        name: 'username',
        type: 'text',
        label: 'Username',
        isRequired: true
    }, 
    {
        name: 'email',
        type: 'email',
        label: 'Email Address',
        isRequired: true
    },
    {
        name: 'password',
        type: 'password',
        label: 'Password',
        isRequired: true
    },
    {
        name: 'password2',
        type: 'password',
        label: 'Password Confirmation',
        isRequired: true
    },
    {
        name: 'accountType',
        type: 'select',
        label: 'Account Type',
        isRequired: true,
        options: [
            {
                name: 'Student',
                value: 'STUDENT'
            },{
                name: 'Teacher',
                value: 'TEACHER'
            },{
                name: 'School',
                value: 'SCHOOL'
            }
        ]
    }
]

export const registerRules = [
    {
        field: 'username',
        rule: /.{5,}/,
        message: 'Username field is too short.'
    },
    {
        field: 'username',
        rule: /^[A-Za-z]/,
        message: 'Username should start with a letter.'
    },
    {
        field: 'password',
        rule: /(?=.*[A-Z].*)(?=.*[a-z].*)(?=.*[0-9].*)(?=.{8,})/,
        message: 'Minimum eight characters, at least one uppercase letter, one lowercase letter and one number.'
    },
    {
        field: 'email',
        rule: /^[\w\-\.]+@([\w-]+\.)+[\w-]{2,4}$/,
        message: 'Invalid email address.'
    },
    {
        field: 'accountType',
        rule: /^(TEACHER|STUDENT|SCHOOL)$/,
        message: 'Invalid Account Type.'
    }
]