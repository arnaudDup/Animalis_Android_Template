package com.example.arnauddupeyrat.animalis;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import Controler.Controler;
import Model.DateUtils;


/**
 * Created by arnauddupeyrat on 14/07/16.
 */
public class ProfileConfigurationFragment extends Fragment {

        private Controler controler = Controler.getContoler();
        EditText firstname;
        EditText lastname;
        EditText description;
        Button birthdate;
        private static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View temp = inflater.inflate(R.layout.profile_configuration, container, false);

            Button buttonQuit = (Button) temp.findViewById(R.id.buttonQuit);

            // quit the page
            buttonQuit.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {

                    HashMap<String, Object> event = new HashMap<String, Object>();
                    event.put("Event", "viewPager");
                    controler.considererEvent(event);
                }
            });

            // change properties.
            firstname = (EditText) temp.findViewById(R.id.firstnameE);
            lastname = (EditText) temp.findViewById(R.id.lastnameE);
            description = (EditText) temp.findViewById(R.id.descritionE);
            birthdate = (Button) temp.findViewById(R.id.BirthdateB);

            firstname.setText(controler.getModele().getProfile().getFirstname());
            lastname.setText(controler.getModele().getProfile().getLastname());
            description.setText(controler.getModele().getProfile().getDescrition());

            birthdate.setText((controler.getModele().getProfile().getBirthdate() == null)?"Acune Date renseigné": DateUtils.toDisplayFormat(controler.getModele().getProfile().getBirthdate()));

            birthdate.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    displayCalendar(controler.getModele().getProfile().getBirthdate());
                }
            });



            return temp;

        }


        @Override
        public void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);

        }

        public void updateProfile(){
            controler.getModele().getProfile().setFirstname(firstname.getText().toString());
            controler.getModele().getProfile().setLastname(lastname.getText().toString());
            controler.getModele().getProfile().setDescrition(description.getText().toString());
        }

        public void displayCalendar (Date date){
            CaldroidFragment caldroidFragment = new CaldroidFragment();


            final CaldroidListener listener = new CaldroidListener() {

                @Override
                public void onSelectDate(Date date, View view) {
                    controler.getModele().getProfile().setBirthdate(date);
                    birthdate.setText( DateUtils.toDisplayFormat(date));
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    Fragment fragment = fm.findFragmentById(R.id.calendar);
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.remove(fragment);
                    ft.commit();
                }

            };

            caldroidFragment.setCaldroidListener(listener);

            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            caldroidFragment.setArguments(args);

            FragmentTransaction t = getActivity().getSupportFragmentManager().beginTransaction();
            t.replace(R.id.calendar, caldroidFragment);
            t.commit();
        }
}
