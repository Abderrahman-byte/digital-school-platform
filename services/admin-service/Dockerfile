FROM python:3.9

WORKDIR /app

COPY . .

RUN python -m pip install pipenv

RUN pipenv install

CMD pipenv shell 'python -m admin_service ; exit'