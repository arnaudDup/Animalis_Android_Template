package com.example.arnauddupeyrat.Animalis.View.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.HashMap;

import com.example.arnauddupeyrat.Animalis.Controler.Controler;
import com.example.arnauddupeyrat.Animalis.Setting.SettingGloblal;

/**
 * Created by arnauddupeyrat on 17/07/16.
 */
public class DialogConfirmation  extends DialogFragment {

        // TODO voir comment le faire plus generique avec passage des variables da le bundle.
        Controler controler = Controler.getContoler();
        HashMap<String, Object> event = new HashMap();

        @Override
         public void onDestroyView(){
            super.onDestroyView();
            if(event.size() != 0){
                controler.considererEvent(event);
            }
        }
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            super.onCreateDialog(savedInstanceState);
            Bundle args = getArguments();
            int message = (int) args.get(SettingGloblal.MESSAGEERROR);
            final String messageControleur = (String) args.get(SettingGloblal.MESSAGE_CONTROLER);

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(message)
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            event = new HashMap<String, Object>();
                            event.put("Event", messageControleur);
                           //controler.considererEvent(event);

                        }
                    }).setNegativeButton("No it's an error", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // Nothing to do.
                }
             });
            // Create the AlertDialog object and return it
            return builder.create();
        }
}
