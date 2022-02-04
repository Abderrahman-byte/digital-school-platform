from tornado.web import RequestHandler
from tornado.httpclient import HTTPError

from admin_service.models.session import Session
from admin_service.models.account import Account

class BaseHandler (RequestHandler) :
    def initialize (self, session) :
        self.session = session
        
class AuthenticationBaseHandler (BaseHandler) :
    def initialize (self, *args, **kwargs) :
        super(AuthenticationBaseHandler, self).initialize(*args, **kwargs)
        self.authenticated = None
        self._current_user = None
    
    @property
    def current_user(self) :
        if self.authenticated is not None :
            return self._current_user
            
        sid = self.get_secure_cookie("sid")
        sess = self.session.query(Session).get(sid.decode('UTF-8')) if sid is not None else None

        if sess is None or sess.payload is None or 'account_id' not in sess.payload :
            self.authenticated = False
            return None
        
        account_id = sess.payload.get('account_id')
        account = self.session.query(Account).get(account_id)
        
        if account is None :
            self.authenticated = False
            return None
        
        self.authenticated = True
        self._current_user = account    
        
        return account
    
def admin_only (func) :
    def wrapper (self, *args, **kwargs) :
        if self.current_user is None :
            raise HTTPError(403)
        
        if not self.current_user.is_admin :
            raise HTTPError(403)
        
        return func(self, *args, *kwargs)
    
    return wrapper