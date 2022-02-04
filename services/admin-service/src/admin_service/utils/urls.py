import os


def refactor_urls_handlers(handler, prefix='/', globals = {}):
    new_handlers = []

    for spec in handler:
        if len(spec) == 2 :
            (url, handler) = spec
            args = {}
        elif len(spec) == 3:
            (url, handler, args) = spec

        prefix = prefix if prefix != '' else '/'                    
        new_url = os.path.join(prefix, url.lstrip('/')).rstrip('/')
        new_url = new_url + '/' if url.endswith('/') else new_url

        if type(handler) == list:
            new_handlers.extend(refactor_urls_handlers(handler, new_url, {**globals, **args}))
        else:
            new_handlers.append((new_url, handler, {**globals, **args}))

    return new_handlers
