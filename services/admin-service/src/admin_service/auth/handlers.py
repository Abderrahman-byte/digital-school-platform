import json
import tornado.web
from bcrypt import checkpw
from datetime import datetime, timedelta

from admin_service.forms import LoginForm
from admin_service.models.account import Account, AccountType
from admin_service.models.session import Session
from admin_service.utils import generate_random_str

class LoginHandler (tornado.web.RequestHandler)  :
    def initialize (self, session) :
        self.session = session

    def get (self) :
        next_url = self.get_argument('next', '/')
        self.render('login.html', username='', password='', next_url = next_url)
        
    def post (self) :
        form = LoginForm(self)
        username = self.get_argument('username', None)
        password = self.get_argument('password', None)
        next_url = self.get_argument('next', '/')
        errors = []
        
        if not form.validate() :
            errors.extend(form.errors)
            return self.render('login.html', username=username, password=password, next_url = next_url, errors=errors)
            
        account = self.session.query(Account).filter(username == username).one()
        
        if account is None or not checkpw(password.encode('UTF-8'), account.password.encode('UTF-8')) :
            errors.append('Password or username is wrong')
            return self.render('login.html', username=username, password=password, next_url = next_url, errors=errors)
                
        expires = datetime.today() + timedelta(days=31)
        sess = Session(sid = generate_random_str(25), payload = {'account_id': account.id }, expires=expires)        
        self.session.add(sess)
        self.session.commit()        
        self.set_secure_cookie('sid', sess.sid, expires_days=31)
        
        self.redirect(next_url)
        
class LogoutHandler (tornado.web.RequestHandler)  :
    def initialize (self, session) :
        self.session = session

    def get (self) :
        sid = self.get_secure_cookie('sid')
        sid = sid.decode('UTF-8') if sid is not None else None
        
        if sid is not None :
            self.session.query(Session).filter_by(sid = sid).delete()
            self.session.commit()
            self.clear_cookie('sid')
        
        self.redirect(self.get_login_url())
        
class RootHandler (tornado.web.RequestHandler)  :
    def initialize (self, session) :
        self.session = session

    def get (self) :
        self.redirect(self.get_login_url(), True)