from tornado.web import authenticated

from admin_service.utils.security import AuthenticationBaseHandler, admin_only

class MainHandler (AuthenticationBaseHandler):  
    @authenticated
    @admin_only
    def get(self):
        self.write("HI THERE")

urls = [
    (r"/", MainHandler),
    (r"/skj", MainHandler)
]