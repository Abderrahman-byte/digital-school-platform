import tornado.web

class MainHandler (tornado.web.RequestHandler):
    def get(self):
        self.render('index.html', author=self.author)

urls = [
    (r"", MainHandler)
]