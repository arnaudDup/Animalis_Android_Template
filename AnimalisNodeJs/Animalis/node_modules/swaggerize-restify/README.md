[![Build Status](https://travis-ci.org/bardzusny/swaggerize-restify.svg?branch=master)](https://travis-ci.org/bardzusny/swaggerize-restify)

swaggerize-restify
==================

`swaggerize-restify` is a fork of excellent [swaggerize-express](https://github.com/krakenjs/swaggerize-express), with some fixes to work with Restify.

### Quick Start

The quickest way to get up and running with `swaggerize-restify` is to follow [swaggerize-express](https://github.com/krakenjs/swaggerize-express) documentation: **Quick Start with a Generator**. After you have generated new `swaggerize-express` project, all you need to do is to install `swaggerize-restify`:

```bash
$ npm install swaggerize-restify
```

And use properly modified `server.js` file:

```javascript
var restify = require('restify');
var swaggerize = require('swaggerize-restify');
var path = require('path');

var server = restify.createServer();

server.use(restify.bodyParser());

swaggerize(server, {
    api: path.resolve('./config/petstore.json'),
    handlers: path.resolve('./handlers')
});

server.listen(8000, 'localhost', function () {
    server.swagger.api.host = server.address().address + ':' + server.address().port;
});
```
