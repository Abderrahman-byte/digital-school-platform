import os

import tornado.ioloop
import tornado.web
from dotenv import load_dotenv

from admin_service.utils.settings import *
from admin_service.accounts.urls import urls as accounts_urls
from admin_service.auth import urls as auth
from admin_service.utils.urls import refactor_urls_handlers

def make_app ():
    settings = get_setting()
    urlSpecs = refactor_urls_handlers([
        (r"/app", accounts_urls),
        (r"/auth", auth.urls)
    ])
    app = tornado.web.Application(urlSpecs, **settings)
        
    return app

def app () :
    load_dotenv()
    app = make_app()
    app.listen(8080)
    tornado.ioloop.IOLoop.current().start()