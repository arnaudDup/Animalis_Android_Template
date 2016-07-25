package com.example.arnauddupeyrat.Animalis.View.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arnauddupeyrat.Animalis.R;

import java.net.URL;
import java.util.HashMap;

import com.example.arnauddupeyrat.Animalis.Controler.Controler ;
import com.example.arnauddupeyrat.Animalis.Setting.SettingGloblal;
import com.example.arnauddupeyrat.Animalis.Utils.BitmapDownloaderTask;
import com.example.arnauddupeyrat.Animalis.Utils.DateUtils;


/**
 * Created by arnauddupeyrat on 21/06/16.
 */
public class PictureFragment extends Fragment {
    private ImageView profilPicture;
    private ImageView backgroundPicture;
    private Controler controler = Controler.getContoler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View temp = inflater.inflate(R.layout.pictures, container, false);
        // we modify the tool bar.
        ImageButton buttonLeft = (ImageButton) temp.findViewById(R.id.leftButtonToolBar);
        ImageButton buttonRight = (ImageButton) temp.findViewById(R.id.rightButtonToolBar);
        Button LogOut = (Button) temp.findViewById(R.id.logOutApp);
        Button configuration = (Button) temp.findViewById(R.id.configuration);
        Button removeAccount = (Button) temp.findViewById(R.id.suppressAcount);
        // hide rigth button
        buttonRight.setVisibility(View.GONE);

        // add the possibility to change fragment on the left button.
        buttonLeft.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                HashMap<String, Object> event = new HashMap<String, Object>();
                event.put("Event", "ClickButtonToolBar");
                event.put("Action", SettingGloblal.LEFT_FRAGMENT);
                controler.considererEvent(event);
            }
        });

        buttonLeft.setImageResource(R.drawable.research_icon);

        // Update presentation of the fragment.
        TextView presentationText = (TextView) temp.findViewById(R.id.presentation);
        StringBuilder stringTemp = new StringBuilder();

        // TODO check if the date isn't null. -> in this cas nothing should be displayed.
        stringTemp.append(controler.getModele().getProfile().getFirstname())
                  .append(" ")
                  .append(controler.getModele().getProfile().getLastname())
                  .append("\n")
                  .append(controler.getModele().getProfile().getDescrition())
                  .append("\n")
                  .append(DateUtils.calculateAge(controler.getModele().getProfile().getBirthdate()))
                  .append(" years");

        presentationText.setText(stringTemp);


        // Log out the application
        LogOut.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                HashMap<String, Object> event = new HashMap<String, Object>();
                event.put("Event", "Deconnexion");
                controler.considererEvent(event);
            }
        });

        // Log out the application
        configuration.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                HashMap<String, Object> event = new HashMap<String, Object>();
                event.put("Event", "profileConfiguration");
                controler.considererEvent(event);
            }
        });

        // Log out the application
        removeAccount.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                HashMap<String, Object> event = new HashMap<String, Object>();
                event.put("Event", SettingGloblal.ASK_FOR_PERMISSION);
                controler.considererEvent(event);
            }
        });

        return temp;

    }


    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        DisplayMetrics metrics = getDimension();
        // initiate the two pictures.
        this.profilPicture = new ImageView(getActivity());
        this.backgroundPicture  = new ImageView(getActivity());
        BitmapDownloaderTask task = new BitmapDownloaderTask(R.id.profilePicture,getActivity(),metrics.widthPixels,metrics.heightPixels);
        BitmapDownloaderTask task1 = new BitmapDownloaderTask(R.id.backgroundPicture,getActivity(),metrics.widthPixels,(getResources().getConfiguration().orientation ==  getResources().getConfiguration().ORIENTATION_PORTRAIT) ? metrics.heightPixels/3:metrics.heightPixels/2);


        URL url1 = null;
        URL url2 = null;
        try {
            url1 = new URL(controler.getModele().getProfile().getProfilePicture()); //new URL(controler.getModele().getProfile().getProfilePictureUri(metrics.heightPixels,metrics.heightPixels).toString());
            url2 =  new URL(controler.getModele().getProfile().getBackgroundPicture());
        }catch(Exception e){
            e.printStackTrace();
        }

        // Mise a jour de la photo de profile.
        task.execute(url1);
        task1.execute(url2);

    }

    private DisplayMetrics getDimension(){
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics;

    }

}
