import enum
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.sql.sqltypes import *
from sqlalchemy.sql.schema import *

Base = declarative_base()

class AccountType (enum.Enum) :
    STUDENT = 'STUDENT'
    TEACHER = 'TEACHER'
    SCHOOL = 'SCHOOL'

class Account (Base) :
    __tablename__ = 'account'
    
    id = Column(String, name='id', primary_key = True)
    username = Column(String)
    email = Column(String)
    password = Column(String)
    is_admin = Column(Boolean, name='is_admin')
    is_active = Column(Boolean, name='is_active')
    created_date = Column(DateTime, name='created_date')
    account_type = Column(Enum(AccountType), name='acc_type')    