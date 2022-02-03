import os

import tornado.ioloop
import tornado.web
from dotenv import load_dotenv

from admin_service.utils.settings import *

class MainHandler (tornado.web.RequestHandler) :
    def get (self):
        self.render('index.html')

def make_app ():
    settings = get_setting()
    
    return tornado.web.Application([
        (r"/", MainHandler)
    ], **settings)

def app () :
    load_dotenv()
    app = make_app()
    app.listen(8080)
    tornado.ioloop.IOLoop.current().start()