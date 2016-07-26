package com.example.arnauddupeyrat.Animalis.Controler;

import android.util.Log;

import com.example.arnauddupeyrat.Animalis.R;

import java.util.HashMap;
import java.util.Map;


import com.example.arnauddupeyrat.Animalis.Controler.Automate.Automate;
import com.example.arnauddupeyrat.Animalis.Controler.Automate.Etat;
import com.example.arnauddupeyrat.Animalis.Controler.Automate.IAction;
import com.example.arnauddupeyrat.Animalis.Controler.Automate.Transition;
import com.example.arnauddupeyrat.Animalis.Model.DTO.ResponseDto;
import com.example.arnauddupeyrat.Animalis.Model.Model;
import com.example.arnauddupeyrat.Animalis.Setting.SettingGloblal;
import com.example.arnauddupeyrat.Animalis.WebRequest.OnServerRequestComplete;
import com.example.arnauddupeyrat.Animalis.WebRequest.ServiceHandler;


public class Controler {

    private Automate automate;
    private Model model;
    private ViewControler viewControler;
    private static Controler controler = new Controler();
    private boolean isAlreadyinitiate = false;

    public static Controler getContoler() {
        return controler;
    }

    private Controler(){
        viewControler = new ViewControler();
        model = new Model();
    }

    /**
     * Allow to change state in automate.
     * @param event
     */
    public void considererEvent(HashMap<String, Object> event)
    {

        viewControler.updateView();
        String typeEvent = (String)event.get("Event");
        Log.i(Controler.class.getName(),"considererEvent(), traitement de l'evenement"+typeEvent);
        Log.i(Controler.class.getName(),"considererEvent(), curent state"+automate.getEtatCourant().toString());

        if(event == null) {
            Log.i(Controler.class.getName(),"considererEvent(),the name of the next transition is null");
            return;
        }
        else
            automate.consider(event);

    }

    public void initAutomate()
    {
        Log.i(Controler.class.getName(),"initAutomate(), Initiate the automaton");
        if(isAlreadyinitiate){
           return;
        }
        this.isAlreadyinitiate = true;
        automate = new Automate();


        Etat e1 = automate.ajouterEtat("PageAccueil");
        Etat e2 = automate.ajouterEtat("Profil");

        // Try to connect with facebook
        HashMap<String,Object> event1 = new HashMap<String,Object> ();
        event1.put("Event", "Connexion");
        final Transition t1 = new Transition(e2,event1);
        t1.setAction(new IAction() {

            private HashMap<String,Object> eventRecu;
            protected Void doInBackground(Void... params) {

                Log.i(Controler.class.getName(),"Controler Call(),change Activity go on core Activity");
                eventRecu = t1.getEventRecu();
                model.setAccesToken((String)eventRecu.get("AccessToken"));
                viewControler.changeActivtyToPrimaryActicvity();
                return null;
            }
        });

        // Deconnexion APP and return to the first Page
        HashMap<String,Object> event2 = new HashMap<String,Object> ();
        event2.put("Event", "Deconnexion");
        final Transition t2 = new Transition(e1,event2);
        t2.setAction(new IAction(){

            private HashMap<String,Object> eventRecu;
            protected Void doInBackground(Void... params) {

                Log.i(Controler.class.getName(),"Controler Call(),change Activity, return on connexion activity");
                eventRecu = t2.getEventRecu();
                viewControler.changeActivtyToMainActivity() ;
                return null;
            }
        });

        // shifting between fragment.
        HashMap<String,Object> event3 = new HashMap<String,Object> ();
        event3.put("Event", "ClickButtonToolBar");
        final Transition t3 = new Transition(e2,event3);
        t3.setAction(new IAction(){

            private HashMap<String,Object> eventRecu;
            protected Void doInBackground(Void... params) {

                Log.i(Controler.class.getName(),"Controler Call(), change fragment oon viewPager");
                eventRecu = t3.getEventRecu();
                if (eventRecu.get("Action").equals(SettingGloblal.LEFT_FRAGMENT)) {
                    viewControler.changeFragmentToLeft();
                }
                else{
                    viewControler.changeFragmentToRight();
                }
                return null;
            }
        });

        // shifting between fragment.
        HashMap<String,Object> event4 = new HashMap<String,Object> ();
        event4.put("Event", "profileConfiguration");
        final Transition t4 = new Transition(e2,event4);
        t4.setAction(new IAction(){

            private HashMap<String,Object> eventRecu;
            protected Void doInBackground(Void... params) {

                Log.i(Controler.class.getName(),"Controler Call(), change fragment to editing profile");
                eventRecu = t4.getEventRecu();
                viewControler.toProfileConfiguration();
                return null;
            }
        });

        // shifting between fragment.
        HashMap<String,Object> event5 = new HashMap<String,Object> ();
        event5.put("Event", "viewPager");
        final Transition t5 = new Transition(e2,event5);
        t5.setAction(new IAction(){
            private HashMap<String,Object> eventRecu = t5.getEventRecu();
            protected Void doInBackground(Void... params) {

                Log.i(Controler.class.getName(),"Controler Call(), try to update profile, after editing");

                ServiceHandler serviceHandler = new ServiceHandler();
                serviceHandler.setOnServerRequestCompleteListener(new OnServerRequestComplete() {
                    @Override
                    public void onSucess(Map<String,Object> mapper) {
                        viewControler.toViewPager();
                        viewControler.updateProfil();
                        Log.i(Controler.class.getName(),"Controler Call(), update profile success");

                    }

                    @Override
                    public void onFailed(int status_code, String message, String url) {
                        // TODO afficher la fenetre comme quoi le profil n'a pas pu se mettre a jour
                        Log.i(Controler.class.getName(),"Controler Call(), update profile fail");
                    }
                });

                // send a request in order to delete account in the database.
                serviceHandler.doServerRequest(getModele().getProfile(),SettingGloblal.URLSERVEREMUL + "/api/Users/" + getModele().getProfile().getIdApiConnection(), SettingGloblal.POST, ResponseDto.class);
                return null;
            }
        });


        HashMap<String,Object> event7 = new HashMap<String,Object> ();
        event7.put("Event", SettingGloblal.ASK_FOR_PERMISSION);
        final Transition t7 = new Transition(e2,event7);
        t7.setAction(new IAction(){

            protected Void doInBackground(Void... params) {
                Log.i(Controler.class.getName(),"Controler Call(), asking permission to remove account");
                viewControler.askForConfirmation(R.string.removeAccountMessage,SettingGloblal.REMOVE_ACCOUNT);
                return null;
            }
        });

        HashMap<String,Object> event6 = new HashMap<String,Object> ();
        event6.put("Event", SettingGloblal.REMOVE_ACCOUNT);
        final Transition t6 = new Transition(e1,event6);
        t6.setAction(new IAction(){


            // Send a request in order to delete user in database.
            protected Void doInBackground(Void... params) {

                    Log.i(Controler.class.getName(),"Controler Call(), remove account");
                    ServiceHandler serviceHandler = new ServiceHandler();
                    serviceHandler.setOnServerRequestCompleteListener(new OnServerRequestComplete() {
                        @Override
                        public void onSucess(Map<String,Object> mapper) {
                            viewControler.changeActivtyToMainActivity();
                            viewControler.displayDialogRemoveAccount();
                            Log.i("Controler Call","Account associated with " + getModele().getProfile().getIdApiConnection() + " is removed properly");

                        }

                        @Override
                        public void onFailed(int status_code, String message, String url) {
                            Log.i("Controler Call","Account associated with " + getModele().getProfile().getIdApiConnection() + " is not removed properly");
                        }
                    });

                    // send a request in order to delete account in the database.
                    serviceHandler.doServerRequest(new HashMap(),SettingGloblal.URLSERVEREMUL + "/api/Users/" + getModele().getProfile().getIdApiConnection(), SettingGloblal.DELETE, null);
                    viewControler.displayDialogRemoveAccount();
                return null;
            }
        });

        // shifting between fragment.
        HashMap<String,Object> event8 = new HashMap<String,Object> ();
        event8.put("Event", SettingGloblal.REPAINT_FRAGMENT);
        final Transition t8 = new Transition(e2,event8);
        t8.setAction(new IAction(){

            protected Void doInBackground(Void... params) {
                Log.i(Controler.class.getName(),"Controler Call(), update fragment Profile");
                viewControler.repaintFragment();
                return null;
            }
        });


        // Etat e1
        e1.ajouterTransition(t1);

        // Etat e2
        e2.ajouterTransition(t2);
        e2.ajouterTransition(t3);
        e2.ajouterTransition(t4);
        e2.ajouterTransition(t5);
        e2.ajouterTransition(t6);
        e2.ajouterTransition(t7);
        e2.ajouterTransition(t8);

        automate.setEtatInitial(e1);
        automate.demarrer();

        Log.i(Controler.class.getName(),"initAutomate(), List of all possible state "+automate.getEtats().toString());

    }

    public Model getModele() {
        return model;
    }

}
