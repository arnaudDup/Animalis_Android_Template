
var databaseConfig = require('../../setting/database.js');
var utils = require('../utils/Utils.js');


function Database()
{
  this.mongoose = require ('mongoose');

  this.ConnexionMongoDb = function ()
  {
    //Tentative de connexion à la base de donnée
    this.mongoose.connect(databaseConfig.url);

    this.mongoose.connection.on("error", function ()
    {
      utils.logError("MogoDb.Database() ,connect to database mongoDb fail ");
    });

    this.mongoose.connection.on("open", function ()
    {
      utils.logInfo("MogoDb.Database() ,Connect to the mongodb database success");
    });
  };
}

// Création d'un objet BaseDeDonnee pour exportation
var database = new Database();

//Exportation de l'objet
module.exports = database;