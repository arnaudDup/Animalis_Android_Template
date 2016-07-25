Welcome on board !
===================

### Sysnopsis

Hey everyBody :)
The main objectives of the application are to use new technologies, to create a " big " application alone (Database - Server - Android App), to enjoy produce project.

The application own two blanck fragment in order to add any content for your futur application, this is a king a template for an android application.
Moreoever this is an RestFull application using SpringAndroid for http request.

![Schema Application](https://lh3.googleusercontent.com/-zqL5zImswRo/V5Za10XAEhI/AAAAAAAAAI8/6Ti2QKP1AYkWFwwMKtz-HiiMLBsOgDcEQCLcB/s0/Yolo.png "SchemaApplication.png")



### Environnement to run application

> **Used Tools:**

> - Android Sudio, to compile and launch the application.
> - Available connected database. (Postgres and MongdoDb)
> - NodJs environnement in order to run th server side.

#### <i class="icon-upload"></i> Install nmp and Node
Follow the indication on https://nodejs.org/en/download/package-manager/.

#### <i class="icon-upload"></i> Install Android Studio
Follow the indication on  https://developer.android.com/studio/index.html

#### <i class="icon-upload"></i> install database availlable environnement

-  For MongoDb :  https://www.mongodb.com/
-  For Postgres : https://www.postgresql.org

---------

### How to use it

- Go to repository "NodeJs"(see the name):
 - Modify apiControler/database/postgres.js file with your own Url connection
 - Modify apiControler/database/mongoDb.js file with your own Url connection

- run server with 'npm start' on the root of the folder (If the server can connect to the database, continue the instruction below ).

- Launch Andoidapplication with android studio and enjoy ! :)
- fell free to modify and just code to fit with your application.



### Contributing

Please free to contribute and add modifications but, don't touch the main branch.

- Fork the repo on GitHub
- Clone the project to your own machine
- Commit changes to your own branch
- Push your work back up to your fork
- Submit a Pull request so that we can review your changes
NOTE: Be sure to merge the latest from "upstream" before making a pull request!


### I currently work on:

- Handle exception in better way.
- Use logger properly.
- refresh fragment when the profil configuration change.
- Use put method (SpringAndroid modify the put method to receive object).
- Script liquibase in order to create table in database.
- Use Swagger in order to have a documentation of services.

#### Licence

- Spring Android: https://github.com/spring-projects/spring-android/blob/master/LICENSE
- Profile Picture : https://github.com/siyamed/android-shape-imageview
- Jackson : http://www.apache.org/licenses/LICENSE-2.0.txt
- Caldron : https://github.com/roomorama/Caldroid/blob/master/LICENSE.md
- FacebookAPI : https://github.com/facebook/facebook-android-sdk/blob/master/LICENSE.txt

<HR>
PS : I'am aware that my english isn't good enough, I curently work on it... If there are any mistakes don't hesitate to correct it but tell me where i'm wrong in order to improve faster.
