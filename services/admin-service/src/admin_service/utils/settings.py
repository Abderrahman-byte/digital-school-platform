import os

def get_setting () :
    settings = dict()
    settings['debug'] = True if os.getenv('debug') == 'true' else False
    settings['cookie_secret'] = os.getenv('SECRET_KEY')
    settings['login_url'] = '/auth/login'
    settings['template_path'] = os.path.realpath(os.path.join(os.path.dirname(__file__), '../../templates'))
    
    return settings