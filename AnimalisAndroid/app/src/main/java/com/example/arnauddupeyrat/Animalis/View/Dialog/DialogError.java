package com.example.arnauddupeyrat.Animalis.View.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.example.arnauddupeyrat.Animalis.Controler.Controler ;
import com.example.arnauddupeyrat.Animalis.R;
import com.example.arnauddupeyrat.Animalis.Setting.SettingGloblal;

/**
 * Created by arnauddupeyrat on 19/06/16.
 */
public class DialogError extends DialogFragment {

        // TODO voir comment le faire plus generique avec passage des variables da le bundle.
        Controler controler = Controler.getContoler();

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            Bundle args = getArguments();
            int message = (int) args.get(SettingGloblal.MESSAGEERROR);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(message)
                    .setPositiveButton(R.string.MessageError, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // nothing to do.
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
}

