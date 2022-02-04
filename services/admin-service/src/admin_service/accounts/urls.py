import tornado.web

class MainHandler (tornado.web.RequestHandler):
    def initialize (self, session) :
        self.session = session
        
    def get(self):
        self.render('index.html')

urls = [
    (r"/", MainHandler),
    (r"/skj", MainHandler)
]