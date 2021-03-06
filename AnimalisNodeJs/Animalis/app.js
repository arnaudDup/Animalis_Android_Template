//----------------------------------------------------------------------------------
// Utils
var express = require('express');
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');
var modele  = require('./apiControler/model/response.js');
var utils = require('./apiControler/utils/Utils.js');


//----------------------------------------------------------------------------------
// app Configuration. 
var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');

// uncomment after placing your favicon in /public
//app.use(favicon(path.join(__dirname, 'public', 'favicon.ico')));
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

//----------------------------------------------------------------------------------
// database
var databasePostgres = require('./apiControler/database/postgres.js');
var databaseMongoDb = require('./apiControler/database/mongoDb.js');

// The connexion to the MongoDB database should generate an error.
databaseMongoDb.ConnexionMongoDb(); 
databasePostgres.ConnexionPostgres();

//----------------------------------------------------------------------------------
// routing
var routes               = require('./routes/index');
var RouteUtilisateur     = require('./apiControler/routing/users.js');

app.use('/', routes);
app.use('/api/Users',RouteUtilisateur);

//----------------------------------------------------------------------------------



utils.logInfo("app , SERVEUR OK : Ecoute sur le port 3000");
utils.logInfo("app ,acces au site sur http://localhost:3000");

module.exports = app;
