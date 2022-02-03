import tornado.web

class LoginHandler (tornado.web.RequestHandler)  :
    def get (self) :
        self.render('login.html')
        
class LogoutHandler (tornado.web.RequestHandler)  :
    def get (self) :
        self.write("This logout handler")
        
class RootHandler (tornado.web.RequestHandler)  :
    def get (self) :
        self.write("This root handler")