FROM python:3.9-bullseye

WORKDIR /app

COPY requirements.txt /app
COPY worldcities.csv /app
COPY populate.py /app

RUN pip install -r requirements.txt

CMD ["python", "populate.py"]