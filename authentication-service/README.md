# The Authentication Service

## Endpoints

| Method   | Endpoint          | Request Data  | Response Data                                             | Description          |
| -------- | ----------------- | ------------- | --------------------------------------------------------- | -------------------- |
| POST     | /api/register     | AccountForm | { "ok": ${boolean},"errors"?: [${List of error messages}] } | Register new account |
| GET/POST | /api/verify-email | ?q=${token}   | { "ok": ${boolean},"errors"?: [${List of error messages]] } | Verify account email |
| GET      | /api/isLoggedIn   |               | { "isLoggedIn": true, data: ${AccountData} }               |                      |

```text
AccountForm {
    username : String,
    email: String,
    password: String,
    password2: String,
    accountType?: 'STUDENT' || 'TEACHER' || 'SCHOOL'
}
```

```text
AccountData {
    username : String,
    email: String,
    accountType: 'STUDENT' || 'TEACHER' || 'SCHOOL',
    isAdmin: Boolean,
    profileData: 'Data that differ in each account type'
}
```

## Required Config files

- src/main/resources/jdbc.properties

for database connection config

```conf
jdbc.url = <connection url>
jdbc.driver = <jdbc driver>
jdbc.user = <user>
jdbc.password = <password>
```

## To run as docker container

- build the image

```shell
docker build -t auth-service .
```

- run the container

```shell
docker run -dp <local-port>:8080 auth-service
```
