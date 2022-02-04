from .handlers import RootHandler, LoginHandler, LogoutHandler

urls = [
    (r"/?", RootHandler),
    (r"login", LoginHandler),
    (r"logout", LogoutHandler),
]