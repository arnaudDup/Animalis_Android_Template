// utilisateurController.js
// But : Controleur permettant de répondre à des requêtes de routage en récupérant des données provenant de la BDD
// La BDD possède ses propres fonctions, le controleur les appels simplement
// Le controleur retourne tous les utilisateurs en format JSON !
// Etat : En cours de travail
//

// Récupération du Modèle
var modeleUtilisateur = require('../api_modele/modeleUtilisateur.js');
var modeleSport = require('../api_modele/modeleSport');
var util = require('util');

// Définition de l'objet controllerUtilisateur
function controllerUtilisateur()
{
    /* ////////////////////////// */
    /* Fonctions Retour Evenement */
    /* ////////////////////////// */
    // Méthode permettant de récupérer l'ensemble des utilisateurs.
    this.AfficherUtilisateurs = function (callback)
    {
    	modeleUtilisateur.find({},function(err,data)
    	{
        if(err) {
            reponse = {"error" : true,"message" : "Error fetching data"};
        } else {
            reponse = {"error" : false,"message" : data};
        }
        callback(reponse);
    	});
    };

    // Méthode permettant de récupérer le nombre d'utilisateurs dans la bdd.
    this.CompterUtilisateurs = function(callback)
    {
    	modeleUtilisateur.find().count(function(err,count)
    	{
        if(err) {
            reponse = {"error" : true,"message" : "Error fetching data"};
        } else {
            reponse = {"error" : false,"message" : "Le nombre d'utilisateur dans la base de données sont : " + count};
        }
    	});
      callback(reponse);
    };

    // Méthode permettant de récupérer un utiliateur à partir de son ID
    this.AfficherUtilisateur = function (UtilisateurID,callback)
    {
	    modeleUtilisateur.findById(UtilisateurID,function(err, data)
	    {
        if(err) {
            reponse = {"error" : true,"message" : "Utilisateur Introuvable"};
        } else {
            reponse = {"error" : false,"message" : data};
        }
        callback(reponse);
	  	});
	  };

    /* //////////////////////////// */
    /* Fonctions Retour Utilisateur */
    /* //////////////////////////// */


    /* //////////////////////////// */
    /* Fonctions Gestion Utilisateur  */
    /* //////////////////////////// */
    // Toutes les fonctions retournent des fichiers JSON pour
    // que le Front sait si celle-ci s'est bien éxécuté
    // permet d'ajouter un nouvelle utilisateur a notre base de données
  	this.AjouterUtilisateur = function(ObjectUtilisateur,callback)
    {
      // création d'un nouvel utilisateur que l'on vas insérer dans la base de données.
  		var UtilisateurTemp = new modeleUtilisateur({
  			_id           	: ObjectUtilisateur.id,
  			last_name       : ObjectUtilisateur.last_name,
  			first_name     	: ObjectUtilisateur.first_name,
  			name 			      : ObjectUtilisateur.name,
  			gender 				  : ObjectUtilisateur.gender,
  			email       		: ObjectUtilisateur.email,
  			birthday		    : ObjectUtilisateur.birthday,
  			picture 		    : ObjectUtilisateur.picture,
  			link		        : ObjectUtilisateur.link,
  			cover           : ObjectUtilisateur.cover,
  			likes				    : ObjectUtilisateur.likes,
        friends         : ObjectUtilisateur.friends,
        sport           : [],
        coordonnes       : null
  		});
  		//puis onsauvegarde l'objet dans notre base de données.
  		UtilisateurTemp.save(function(err,Utilisateur){
  		// permet d'avoir une fonction de callback à l'insertion d'un nouvel utilisateur dans notre base de données.
  			  if(err)
  			  {
  			  	// génére une érreur si l'utilisateur est deja présent mais ne l'insére pas dans la base de données
  			  	if (err.message == 'E11000 duplicate key error')
  			  	{
  			  	 	reponse = {"error" : true, "message" : "utilisateur deja présent"};
  			  	}
  			  	else
  			  	{
  			  		reponse = {"error" : true, "message" : err.toString()};
  			  	}
  			  }
  			  else
  			  {
  		  		reponse = {"error" : false, "message" : "utilisateur ajouté à la bdd."};
  			  }
          
          callback(reponse);
  		});
    };

    // Méthode permettant de mettre à jour la liste d'amis d'un utilisateur.
  	this.UpdateFriends = function (objectId,ObjectfriendUsers,callback)
  	{
  			modeleUtilisateur.findByIdAndUpdate(objectId,{friends : ObjectfriendUsers }, function(err,data)
  			{
          console.log('Status: ' + util.inspect(data))
          if(err) {
              reponse = {"error" : true,"message" : "Utilisateur Introuvable"};
          } else {
              reponse = {"error" : false,"message" : "Mise a jour des amis de l'utilisateur : " + objectId};
          }
          callback(reponse);
  			});
  	};

    // Update location data.
    this.UpdateCoordonne = function (IdUtilisateur,CordonneObject,callback)
    {
        modeleUtilisateur.findByIdAndUpdate(IdUtilisateur,{coordonnes  : CordonneObject.coordonnes }, function(err,data)
        {
          console.log('Status: ' + util.inspect(data))
          console.log('Status: ' + util.inspect(data))
          if(err) {
              reponse = {"error" : true,"message" : "Utilisateur Introuvable"};
          } else {
              reponse = {"error" : false,"message" : "Mise a jour des coordonnees de l'utilisateur : " + IdUtilisateur};
          }
          callback(reponse);
        });
    };

    // Méthode permettant de mettre à jour un utilisateur.
  	this.UpdateUtilisateur = function (ObjectUtilisateur,callback)
  	{
        modeleUtilisateur.findById(ObjectUtilisateur._id||ObjectUtilisateur.id, function (err, utilisateur){
          console.log('Status: ' + util.inspect(utilisateur))
           if (err) {
              reponse = {"error" : true, "message" : 'Update failed'};
           } else {
              //update fields
              for (var field in modeleUtilisateur.schema.paths) {
                 if (field !== '_id') {
                    if (ObjectUtilisateur[field] !== undefined) {
                       utilisateur[field] = ObjectUtilisateur[field];
                    }
                 }
              }
              utilisateur.save();
              reponse = {"error" : false,"message" : "Mise a jour de l'utilisateur : " + utilisateur._id};
           }
           callback(reponse);
        });
  	};

    // Suppression d'un Utilisateur à partir de son ID
    this.SupprimerUtilisateur = function(UtilisateurID,callback)
    {
        modeleUtilisateur.remove({'_id':UtilisateurID},function(err,data)
        {
            if(err) {
                reponse = {"error" : true,"message" : "Utilisateur Introuvable"};
            } else {
                reponse = {"error" : false,"message" : data};
            }
            callback(reponse);
        });
    };

    // Suppression entiére de la base de données.
  	this.SupprimerUtilisateurs = function (callback)
  	{
  		modeleUtilisateur.remove({},function(err, data)
  		{
        if(err) {
            reponse = {"error" : true,"message" : "Evènement Introuvable"};
        } else {
            reponse = {"error" : false,"message" : data};
        }
        callback(reponse);
  		});
  	};
    /* //////////////////////////// */
    /* Fonctions Gestion Evenement  */
    /* //////////////////////////// */

    //Add sport in sport list of a user
    this.AjouterSport = function(idUser,valueSport,callback)
    {
        modeleUtilisateur.findById(idUser,function(err,data)
        {
              var reponse = {}; 
              if(err)
              {
                  reponse = {"error" : true ,"message" : "Utilisateur non présent"};
                  callback(reponse);
                  return;
              }
              else
              {
                  modeleSport.find({value : valueSport},function(err2,data2)
                  {
                      if(err2)
                      {
                        reponse = {"error" : true ,"message" : "Sport non présent"};
                        callback(reponse);
                        return;
                      }
                      else
                      {
                          var result = modeleUtilisateur.update({"_id" : idUser},{$push: { sport : {value : valueSport}}},function(err3,data3){
                              if(err3)
                              {
                                  reponse = {"error" : true ,"message" : "Aucun Utilisateur mis à jour"};
                              }
                              else
                              {
                                  reponse = {"error" : false,"message" : "Modified"};
                              }
                              callback(reponse);
                          });
                      }
                  });
              }
        });
    };

    //delete sport in sport list of user
    this.SupprimerSport = function(idUser,valueSport,callback)
    {
        modeleUtilisateur.update( { _id: idUser },{ $pull: { sport : { value : valueSport} } }, function(err,data)
        {
            var reponse = {}; 
            if(err) {
                reponse = {"error" : true,"message" : "Le sport renseigné ne fait pas partie des sports de l'utilisateur"};
            } else {
                reponse = {"error" : false,"message" : "sport supprimé"};
            }
            callback(reponse);
        });
    };

    this.TrouverSports = function(idUser,callback)
    {
      modeleUtilisateur.find({_id : idUser},{sport : 1}, function(err,data)
      {
          var reponse = {}; 
          if(err) {
                reponse = {"error" : true,"message" : "Aucun utilisateur de cet id "};
            } else {
                reponse = {"error" : false,"message" : data};
            }
            callback(reponse);
      });
    }
}

module.exports = new controllerUtilisateur();
