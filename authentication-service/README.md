# The Authentication Service

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
