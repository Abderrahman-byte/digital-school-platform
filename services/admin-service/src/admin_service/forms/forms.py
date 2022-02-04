class Form:
    def __init__(self, request) :
        self.request_handler = request
        self.errors = []
        
    def validate (self):
        allowed = []
        
        if hasattr(self, 'rules') :
            pass
        
        if hasattr(self, 'required_fields') :
            allowed.extend(self.required_fields)
            
            for field in self.required_fields :
                value = self.request_handler.get_argument(field, None)
                
                if value is None or value == '' :
                    self.errors.append(f'The field {field} is required')
                    
        if hasattr(self, 'allowed_fields') :
            allowed.extend(self.allowed_fields)
            
        for field in self.request_handler.request.body_arguments.keys() :
            if field not in allowed :
                self.errors.append(f'The field {field} is not allowed')
        
        return len(self.errors) <= 0
    
class LoginForm (Form) :
    rules = []
    required_fields = ['username', 'password']
    allowed_fields = ['next_url']