from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker

def make_session (db_url, echo = False) :
    db_engine = create_engine(db_url, echo=echo)
    Session = sessionmaker(bind=db_engine)
    return Session()