FROM python:3.12-rc-slim

WORKDIR /app

COPY . .

RUN python -m pip install pipenv

RUN pipenv install

CMD pipenv shell 'python -m admin_service ; exit'