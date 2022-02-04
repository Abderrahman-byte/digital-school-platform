from random import randrange, choice
import string

def generate_random_str (len = 25) :
    allowed = string.ascii_letters + string.digits
     
    return ''.join([choice(allowed) for _ in range(len)])