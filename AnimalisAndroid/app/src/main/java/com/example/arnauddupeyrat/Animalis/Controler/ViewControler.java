package com.example.arnauddupeyrat.Animalis.Controler;

import android.widget.FrameLayout;

import com.example.arnauddupeyrat.Animalis.View.Activity.FragmentActivityControler;
import com.example.arnauddupeyrat.Animalis.View.Activity.MainActivity;
import com.example.arnauddupeyrat.Animalis.View.Fragment.ProfileConfigurationFragment;
import com.example.arnauddupeyrat.Animalis.R;

import java.lang.ref.WeakReference;

/**
 * Created by arnauddupeyrat on 18/06/16.
 */
public class ViewControler {

    protected WeakReference<MainActivity> mainActivity;
    protected WeakReference<FragmentActivityControler> FragmentActivity;


    public  ViewControler(){
        mainActivity = MainActivity.getActivity();
        FragmentActivity = FragmentActivityControler.getActivity();
    }
    public void updateView (){
        mainActivity = MainActivity.getActivity();
        FragmentActivity = FragmentActivityControler.getActivity();;
    }
    public void changeActivtyToPrimaryActicvity() {
        mainActivity.get().changeActivity();
    }

    public void changeActivtyToMainActivity() {
        mainActivity.get().logOutFace();
        FragmentActivity.get().logOut();
    }

    public void toProfileConfiguration(){
        ProfileConfigurationFragment fragment = new ProfileConfigurationFragment();
        FragmentActivity.get().findViewById(R.id.fragment_container).getLayoutParams().width = FrameLayout.LayoutParams.MATCH_PARENT ;
        FragmentActivity.get().findViewById(R.id.fragment_container).getLayoutParams().height = FrameLayout.LayoutParams.MATCH_PARENT ;
        FragmentActivity.get().toProfileConfiguration(fragment);
    }

    public void toViewPager(){
        FragmentActivity.get().toViewPager();
        FragmentActivity.get().findViewById(R.id.fragment_container).getLayoutParams().width = FrameLayout.LayoutParams.WRAP_CONTENT ;
        FragmentActivity.get().findViewById(R.id.fragment_container).getLayoutParams().height = FrameLayout.LayoutParams.WRAP_CONTENT ;
    }

    public void changeFragmentToRight(){
        FragmentActivity.get().changeFragmentToRigth();
    }
    public void changeFragmentToLeft() {
        FragmentActivity.get().changeFragmentToLeft();
    }

    public void displayDialogRemoveAccount(){
        FragmentActivity.get().logOut();
        mainActivity.get().logOutFace();
    }

    public void askForConfirmation(int message, String messageControler){
        FragmentActivity.get().displayDialog(message,messageControler);
    }

    public void updateProfil(){
        FragmentActivity.get().updateProfile();
    }

    public void repaintFragment() {
        FragmentActivity.get().repaintFragment();
    }

}
