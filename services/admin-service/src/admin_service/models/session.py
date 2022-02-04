from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.sql.schema import *
from sqlalchemy.sql.sqltypes import *

Base = declarative_base()

class Session (Base) :
    __tablename__ = 'session'
    
    sid = Column(String, primary_key = True)
    payload = Column(JSON)
    expires = Column(DateTime)