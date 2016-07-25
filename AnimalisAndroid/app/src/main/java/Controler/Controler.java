package Controler;

import android.os.Bundle;
import android.util.Log;

import com.example.arnauddupeyrat.animalis.R;
import com.example.arnauddupeyrat.animalis.ViewControler;

import java.util.HashMap;
import java.util.Map;

import Controler.Automate.Automate;
import Controler.Automate.Etat;
import Controler.Automate.IAction;
import Controler.Automate.Transition;
import Model.DTO.ResponseDto;
import Model.Model;
import Setting.SettingGloblal;
import WebRequest.OnServerRequestComplete;
import WebRequest.ServiceHandler;

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
        System.out.println("Consideration de :"+typeEvent);

        if(event == null) {
            System.out.println("Evenement null");
            return;
        }
        else
            automate.consider(event);

    }

    public void initAutomate()
    {
        if(isAlreadyinitiate){
           return;
        }
        this.isAlreadyinitiate = true;
        System.out.print("Initialisation de l'automate.");
        automate = new Automate();

        // On dÔøΩfini des etats
        Etat e1 = automate.ajouterEtat("PageAccueil");
        Etat e2 = automate.ajouterEtat("Profil");


        // Try to connect with facebook
        HashMap<String,Object> event1 = new HashMap<String,Object> ();
        event1.put("Event", "Connexion");
        final Transition t1 = new Transition(e2,event1);
        t1.setAction(new IAction() {

            private HashMap<String,Object> eventRecu;
            protected Void doInBackground(Void... params) {

                Log.d("DEBUG", "appel du controleur Pour changer d'activites.");
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

                Log.d("DEBUG", "appel du controleur Pour changer d'activites.");
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

                Log.d("DEBUG", "appel du controleur Pour changer de fragments.");
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

                Log.d("DEBUG", "appel du controleur Pour changer de fragments.");
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

                Log.d("DEBUG", "appel du controleur Pour changer de fragments.");

                ServiceHandler serviceHandler = new ServiceHandler();
                serviceHandler.setOnServerRequestCompleteListener(new OnServerRequestComplete() {
                    @Override
                    public void onSucess(Map<String,Object> mapper) {
                        viewControler.toViewPager();
                        viewControler.updateProfil();
                        Log.i("Controler Call","Account associated with " + getModele().getProfile().getIdApiConnection() + " is updated properly");

                    }

                    @Override
                    public void onFailed(int status_code, String message, String url) {
                        // TODO afficher la fenetre comme quoi le profil n'a pas pu se mettre a jour
                        Log.i("Controler Call","Account associated with " + getModele().getProfile().getIdApiConnection() + " is not updated properly");
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
                    Log.i("Controler Call","Try to remove account " + getModele().getProfile().getIdApiConnection());

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

        // Etat e1
        e1.ajouterTransition(t1);

        // Etat e2
        e2.ajouterTransition(t2);
        e2.ajouterTransition(t3);
        e2.ajouterTransition(t4);
        e2.ajouterTransition(t5);
        e2.ajouterTransition(t6);
        e2.ajouterTransition(t7);

        automate.setEtatInitial(e1);
        automate.demarrer();

    }

    public Model getModele() {
        return model;
    }

}
