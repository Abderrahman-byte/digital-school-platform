# Digital School Plateform Services :

## Setup backend service :

### Setup database :

- Install postgresql

- Create Database :

```shell
psql -U <username> -c 'CREATE DATABASE <database>' postgres
```

- Create tables :

```shell
psql -U <username> -a -f ../database/01-authentication.sql <database>
psql -U <username> -a -f ../database/02-profils.sql <database>
psql -U <username> -a -f ../database/03-connections.sql <database>
```

### Populate database with cities, state and countries :

```shell
cd ../worldcities
```

- Install python dependencies :

```shell
pip install -r requirements.txt
```

- Create db.json file :

```json
{
    "database":"<database>",
    "user": "<username>",
    "password": "<password>"
}
```

- Populate database with data :

```shell 
python ./populate.py
```

### Build and run backend services :

```shell
cd ../services
```

- Add envirement variables :

Create files :

___authentication-service/src/main/resources/env.properties___

___social-service/src/main/resources/env.properties___

___school-management/src/main/resources/env.properties___

```conf
api.version = v1

session.key = sid
jwt.secret = <some-secure-password>
cors.allowOrigins = <frontend-hosts>

jdbc.url = jdbc:postgresql://database/pfe
jdbc.driver = org.postgresql.Driver
jdbc.user = <db-username>
jdbc.password = <db-password>

mail.smtp.host = smtp.gmail.com
mail.smtp.port = 465
mail.smtp.user = <smtp-username>
mail.smtp.from = <smtp-username>
mail.smtp.password = <smtp-password>
```

- build and run with docker :

```shell
sudo ./start-service.sh
```

- check docker runing service :

output example :

```
CONTAINER ID   IMAGE               COMMAND                  CREATED             STATUS             PORTS                                         NAMES
ffb676b217ab   school-management   "java -jar school-ma…"   About an hour ago   Up About an hour   0.0.0.0:49155->8080/tcp, :::49155->8080/tcp   competent_jennings
1cfa89093405   social-service      "java -jar socialser…"   About an hour ago   Up About an hour   0.0.0.0:49154->8080/tcp, :::49154->8080/tcp   reverent_banach
9c7b5439e0b5   auth-service        "java -jar authservi…"   About an hour ago   Up About an hour   0.0.0.0:49153->8080/tcp, :::49153->8080/tcp   gracious_faraday
```

## Setup and run the front-end :

- Create file ./frontent/.env :

```conf
REACT_APP_API_PREFIX = '/api/v1'

REACT_APP_AUTH_SERVICE_HOST = 'http://<backend>:<auth-service-port>/'
REACT_APP_SOCIAL_SERVICE_HOST = 'http://<backend>:<social-service-port>/'
REACT_APP_SCHOOL_SERVICE_HOST = 'http://<backend>:<school-management-port>/'
```

- Run the front-end :

```shell
cd frontend
PORT=3000 npm start
```

___NOTE : Each time you build the docker services you should check there ports and update .env file and re-run the frontend app___
