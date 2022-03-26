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

export const registerFiedds = [
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