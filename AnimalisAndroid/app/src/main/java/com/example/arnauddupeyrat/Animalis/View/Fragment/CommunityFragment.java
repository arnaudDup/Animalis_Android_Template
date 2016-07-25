package com.example.arnauddupeyrat.Animalis.View.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.arnauddupeyrat.Animalis.R;

import java.util.HashMap;

import com.example.arnauddupeyrat.Animalis.Controler.Controler;
import com.example.arnauddupeyrat.Animalis.Setting.SettingGloblal;

/**
 * Created by arnauddupeyrat on 07/07/16.
 */
public class CommunityFragment extends Fragment {

    private Controler contoler = Controler.getContoler();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View temp = inflater.inflate(R.layout.common, container, false);
        // we modify the tool bar.
        ImageButton buttonRigth = (ImageButton) temp.findViewById(R.id.rightButtonToolBar);
        ImageButton buttonLeft = (ImageButton) temp.findViewById(R.id.leftButtonToolBar);
        // hide rigth button
        buttonLeft.setVisibility(View.GONE);

        // add the possibility to change fragment on the left button.
        buttonRigth.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                HashMap<String, Object> event = new HashMap<String, Object>();
                event.put("Event", "ClickButtonToolBar");
                event.put("Action", SettingGloblal.RIGHT_FRAGMENT);
                contoler.considererEvent(event);
            }
        });

        buttonRigth.setImageResource(R.drawable.research_icon);

        return temp;

    }


    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }
}
