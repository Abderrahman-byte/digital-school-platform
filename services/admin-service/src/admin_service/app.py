import os

from dotenv import load_dotenv
import tornado.ioloop
import tornado.web

from admin_service.utils.settings import *
from admin_service.utils.db import make_session
from admin_service.accounts.urls import urls as accounts_urls
from admin_service.auth import urls as auth
from admin_service.utils.urls import refactor_urls_handlers
from admin_service.models.account import Account

def make_app ():
    settings = get_setting()
    session = make_session(os.getenv('DATABASE_URL'), settings.get('debug'))
    
    urlSpecs = refactor_urls_handlers([
        (r"", accounts_urls),
        (r"/auth", auth.urls)
    ], globals={ 'session': session })
    
    app = tornado.web.Application(urlSpecs, **settings)
        
    return app

def app () :
    load_dotenv()
    app = make_app()
    app.listen(os.getenv('PORT', 8080))
    tornado.ioloop.IOLoop.current().start()