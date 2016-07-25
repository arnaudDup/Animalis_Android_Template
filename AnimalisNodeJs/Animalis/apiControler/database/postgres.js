var pg = require('pg');
var databaseConfig = require('../../setting/database.js');
var utils = require('../utils/Utils.js');

function BaseDeDonnee()
{
    this.mongoose = require ('mongoose');

    //Tentative de connexion à la base de donnée
    this.postgres = require('pg');

    this.ConnexionPostgres  = function ()
    {
        // Get a Postgres client from the connection pool
        this.postgres.connect(databaseConfig.PostGre.url, function(err, client, done) 
        {
            // Handle connection errors
            if(err) 
            {
              utils.logError("Postgres.Database() ,connect to database mongoDb fail ");
            }
            else
            {
              utils.logInfo("Postgres.Database() ,Connect to the mongodb database success");
            }
        });
    }; 

}

// create object sended
var BaseDeDonnee = new BaseDeDonnee();

// export object
module.exports = BaseDeDonnee;
