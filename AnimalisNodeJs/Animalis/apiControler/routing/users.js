var express 			         = require('express');
var router 				         = express.Router();
var path 				           = require('path');
var modele  = require('../model/response.js');
var userControler = require('../model/userModel.js');
var error = require('../../setting/error.js');
var utils = require('../utils/Utils.js');

// Route Simple :
router.route("/")

    // Create Users.
    .post(function(req,res)
    {
        utils.logInfo("routing.user(), post to get or create a user");
        utils.logDebug("routing.user()"+JSON.stringify(req.body));
	    if(req.body != undefined){
    		userControler.addUser(req.body,function(reponse)
  			{
  				res.json(reponse);
  			});
    	}
        else{
           var response = modele.InitizializeBadAnwser(error.operationFailled);
           res.json(response);
        }
    })


// Route ID :
router.route("/:id")
    .get(function(req,res)
    {
        utils.logInfo("routing.user(), get user");
        if(typeof(req.params.id) != 'undefined')
        {
            userControler.getUsers(req.params.id,function(reponse){
                res.json(reponse);
            });
        }
    })
    .delete(function(req,res)
    {
        utils.logInfo("routing.user(), delete user");
        if(typeof(req.params.id) != 'undefined')
        {
            userControler.removeUser(req.params.id,function(reponse){
                res.json(reponse);
            });
        }
    }).post(function(req,res)
    {
        utils.logInfo("routing.user(), post to update user");
        utils.logDebug("routing.user()"+JSON.stringify(req.body));
        if(req.body != undefined){
            userControler.updateUser(req.body,function(reponse)
            {
                res.json(reponse);
            });
        }
        else{
           var response = modele.InitizializeBadAnwser(error.operationFailled);
           res.json(response);
        }
    });


module.exports = router;
