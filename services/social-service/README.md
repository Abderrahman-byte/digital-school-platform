# Social Service

## Api Endpoint

| Method | Endpoint | Request Data | Response Data | Description |
|---|---|---|---|---|
| POST | /api/profile | StudentForm\|TeacherForm\|SchoolForm | { "ok": ${boolean},"errors"?: [List of error messages] | Create account profile |
| GET | /api/search/city | ?query=${query} | { "ok": true,"data": [Array of results] } | Search for city |
| GET | /api/search/school | ?query=${query} | { "ok": true,"data": [Array of results] } | Search for school |
| DELETE | /api/teacher/leave-school | ?id=${schoolId}| { "ok": ${boolean}} | For teacher to leave school |
| POST | /api/teacher/join-school | {id, title} | { "ok" : ${boolean} }  | For teacher to join school |

```text
SchoolForm {
    name: String,
    subtitle?: String,
    overview?: String,
    cityId: int
}
```

```text
TeacherForm {
    firstName: String,
    lastName: String,
    title: String,
    cityId: int,
    bio?: String
}
```

```text
TeacherForm {
    firstName: String,
    lastName: String,
    dayOfBirth: String "yyyy-mm-dd",
    cityId: int
}
```
