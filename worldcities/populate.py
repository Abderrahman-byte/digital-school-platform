#!/bin/env python

import psycopg2 as pg
import csv, os

TABLES_DEFINITION = '''
CREATE TABLE IF NOT EXISTS country (
    id SERIAL PRIMARY KEY,
    name VARCHAR (100) NOT NULL UNIQUE,
    code VARCHAR (5) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS "state" (
    id SERIAL PRIMARY KEY,
    name VARCHAR (100) NOT NULL,
    country_id INTEGER NOT NULL REFERENCES country (id),
    UNIQUE (country_id, name)
);

CREATE TABLE IF NOT EXISTS city (
    id SERIAL PRIMARY KEY,
    name VARCHAR (100) NOT NULL,
    state_id INTEGER NOT NULL REFERENCES "state" (id),
    UNIQUE (state_id, name)
); 
'''

countries = []
states = []

def searchForCountry (name, code) :
    for country in countries :
        if len(country) >= 3 and country[1] == name and country[2] == code :
            return country
        
    return None

def searchForState (name, countryId) :
    for state in states :
        if len(state) >= 3 and state[1] == name and state[2] == countryId :
            return state
        
    return None

def insertCountry (connection, cursor, name, code):
    query = 'INSERT INTO country (name, code) VALUES (%s, %s) ON CONFLICT DO NOTHING RETURNING id'
    
    country = searchForCountry(name, code)
    
    if country is not None :
        return country[0]
    
    cursor.execute(query, (name, code))
    connection.commit()
    country = cursor.fetchone()
    
    if country is not None :
        countries.append((country[0], name, code))
        return country[0]
    
    cursor.execute('SELECT id FROM country WHERE name = %s AND code = %s', (name, code))
    country = cursor.fetchone()
    
    if country is not None :
        countries.append((country[0], name, code))
        return country[0]
    
def insertState (connection, cursor, name, countryId) :
    query = 'INSERT INTO "state" (name, country_id) VALUES (%s, %s) ON CONFLICT DO NOTHING RETURNING id'
    
    state = searchForState(name, countryId)
    
    if state is not None :
        return state[0]
    
    cursor.execute(query, (name, countryId))
    connection.commit()
    state = cursor.fetchone()
    
    if state is not None :
        states.append((state[0], name, countryId))
        return state[0]
    
    cursor.execute('SELECT id FROM "state" WHERE name = %s AND country_id = %s', (name, countryId))
    state = cursor.fetchone()
    
    if state is not None :
        states.append((state[0], name, countryId))
        return state[0]

def insertCity (connection, cursor, name, stateId) :
    query = 'INSERT INTO city (name, state_id) VALUES (%s, %s) ON CONFLICT DO NOTHING RETURNING id'
    
    cursor.execute(query, (name, stateId))
    connection.commit()
    
    city = cursor.fetchone()
    
    if city is not None : return city[0]
    
    return None

def fromCsvToDatabase (csvFilename, connection) : 
    cursor = connection.cursor()
    
    with open(csvFilename, 'r', newline="") as csvFile:
        reader = csv.DictReader(csvFile)
        
        for row in reader :
            countryId = insertCountry(connection, cursor, row['country'], row['iso2'])
            stateId = insertState(connection, cursor, row['admin_name'], countryId)
            cityId = insertCity(connection, cursor, row['city'], stateId)
            
            if cityId is None :
                print('[DUPLICATE] :', row['city'], '->', row['admin_name'], '->', row['country'])
    
    cursor.close()
            
def prepareDatabase (connection) :
    queries = filter(lambda query: len(query) > 0, map(lambda query: query.strip(), TABLES_DEFINITION.split(';')))
    cursor = connection.cursor()
    
    for query in queries :
        cursor.execute(query)
        connection.commit()
    
    cursor.close()

def getDbConfig () :    
    return {
        'database': os.getenv('PG_DB', 'postgres'),
        'user': os.getenv('PG_USER', 'postgres'),
        'password': os.getenv('PG_PASSWORD', ''),
        'host': os.getenv('PG_HOST', 'localhost'),
        'port': int(os.getenv('PG_PORT', '5432')),
    }

def main () :
    dbConf = getDbConfig()
    dbConnection = pg.connect(**dbConf)
    
    prepareDatabase(dbConnection)
    fromCsvToDatabase('./worldcities.csv', dbConnection)
    
    dbConnection.close()
    
if __name__ == '__main__' :
    main()
