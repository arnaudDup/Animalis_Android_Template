package com.example.arnauddupeyrat.animalis;





import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import Controler.Controler ;

// import facebook
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import Model.DTO.ProfileUserDto;
import Model.JacksonMapper;
import Setting.SettingGloblal;
import WebRequest.OnServerRequestComplete;
import WebRequest.ServiceHandler;

public class MainActivity extends AppCompatActivity {

    CallbackManager callbackManager;
    LoginButton loginButton;
    Controler controler;
    public static WeakReference<MainActivity> mainActivity;
    JacksonMapper mapper = JacksonMapper.getInstance();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    public MainActivity() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Envoi du post","YOLO");
        super.onCreate(savedInstanceState);
        mainActivity = new  WeakReference<MainActivity> (this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        controler = Controler.getContoler();
        controler.initAutomate();
        setContentView(R.layout.activity_main);

        if (Profile.getCurrentProfile() != null) {
            SpinnerFragment spinnerFragment = new SpinnerFragment();
            SpinnerWaitingOnCall(spinnerFragment);
            sendPost(AccessToken.getCurrentAccessToken().getToken(),Profile.getCurrentProfile().getId(),SettingGloblal.NOUVELADHERENTS);

        }
        // setting the login button.
        this.callbackManager = CallbackManager.Factory.create();
        this.loginButton = (LoginButton) findViewById(R.id.login_button);
        addPermissions();

        /*******************************    Callback facebook developer ***********************/

        this.loginButton.registerCallback(this.callbackManager, new FacebookCallback<LoginResult>() {

            private ProfileTracker mProfileTracker;
            String idFacebook = " ";

            @Override
            public void onSuccess(LoginResult loginResult) {
                SpinnerFragment spinnerFragment = new SpinnerFragment();
                SpinnerWaitingOnCall(spinnerFragment);
                if (Profile.getCurrentProfile() == null) {
                    mProfileTracker = new ProfileTracker() {


                        @Override
                        protected void onCurrentProfileChanged(Profile profile, Profile profile2) {
                            idFacebook = profile2.getId();
                            mProfileTracker.stopTracking();
                            sendPost(AccessToken.getCurrentAccessToken().getToken(), idFacebook, SettingGloblal.NOUVELADHERENTS);
                        }
                    };
                } else {
                    idFacebook = Profile.getCurrentProfile().getId();
                    sendPost(AccessToken.getCurrentAccessToken().getToken(), idFacebook,SettingGloblal.NOUVELADHERENTS);
                }
            }

            @Override
            public void onCancel() {
                // TODO afficher popup pour recommencer la connexion.
            }

            @Override
            public void onError(FacebookException exception) {
                // TODO affiche popu pareil que celle du dessus.
            }
        });
        /*******************************    Callback facebook developer ***********************/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Clickbutton", "Clickbutton");
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (callbackManager.onActivityResult(requestCode, resultCode, data)) {
            return;
        }
    }

    private void addPermissions() {
        List<String> permissions = new ArrayList<String>();
        permissions.add("public_profile");
        permissions.add("email");
        permissions.add("user_birthday");
        permissions.add("user_about_me");
        this.loginButton.setReadPermissions(permissions);
    }

    // Put the fragmlent on the dinamic way.
    public void SpinnerWaitingOnCall(Fragment fragment) {

        // begin the transaction
        FragmentTransaction ft =getSupportFragmentManager().beginTransaction();
        ft.add(R.id.SpinerWaiting, fragment, "Mise en place du nouveau spinner");
        ft.commit();
        Log.d("DEBUG ", "Mise en place du spinner d'attente");
    }

     public void removeSpinner() {
         android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
         Fragment fragment = fm.findFragmentById(R.id.SpinerWaiting);
         FragmentTransaction ft = fm.beginTransaction();
         ft.remove(fragment);
         ft.commit();
    }
    public void showDilaog(int StringToDisplay) {

        DialogError dialog = new DialogError();
        Bundle args = new Bundle();
        args.putInt(SettingGloblal.MESSAGEERROR,StringToDisplay);
        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(),"ErrorConnexion");
    }

    private void sendPost(String AccesToken, String idFacebook, int nouvelAdherent) {
        ServiceHandler serviceHandler = new ServiceHandler();

        // build the post params of the request.
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("action", ""+nouvelAdherent);
        params.put("id", idFacebook);
        params.put("accesToken", AccesToken);


        serviceHandler.setOnServerRequestCompleteListener(new OnServerRequestComplete() {

            @Override
            public void onSucess(Map<String,Object> mapper) {
                // TODO modifier le resultat de la requetes pour que celui-ci concondent avec les données.
                Log.d("sendPost()","get or create a new user of the application"+ mapper.get(SettingGloblal.RESPONSE));
                ProfileUserDto user = (ProfileUserDto) mapper.get(SettingGloblal.RESPONSE);
                Log.d("sendPost()","AFFICHAGE PROFILE"+user.toString());

                controler.getModele().setProfile(user);

                HashMap<String, Object> event = new HashMap<>();
                event.put("Event", "Connexion");
                event.put("Profile", Profile.getCurrentProfile());
                event.put("AccessToken", AccessToken.getCurrentAccessToken().getToken());
                controler.considererEvent(event);

                removeSpinner();
            }

            @Override
            public void onFailed(int status_code, String message, String url) {
                logOutFace();
                removeSpinner();
                showDilaog(R.string.error);

            }
        });
        // the interface is already initiate above
        serviceHandler.doServerRequest(params,SettingGloblal.URLSERVEREMUL + "/api/Users", SettingGloblal.POST,ProfileUserDto.class);
    }

    public void changeActivity() {

        Intent myIntent = new Intent(this, FragmentActivityControler.class);
        startActivity(myIntent);
    }

    private void debug(String tag, String string) {
        Log.d(tag, string);
    }

    public static WeakReference<MainActivity> getActivity() {
        return mainActivity;
    }

    public void logOutFace() {
        LoginManager.getInstance().logOut();
    }

    @Override
    public void onStart() {
        super.onStart();
    }
    @Override
    public void onStop() {
        super.onStop();
    }
}
