package com.example.arnauddupeyrat.animalis;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;

import Setting.SettingGloblal;

/**
 * Created by arnauddupeyrat on 28/06/16.
 */
public class FragmentActivityControler extends FragmentActivity {


    static final int NUM_ITEMS = 3;
    private MyAdapter mAdapter;
    private ViewPager mPager;
    private static WeakReference<FragmentActivityControler> fragmentControlerActivity;
    boolean mShowingBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frament_pager);

        fragmentControlerActivity = new  WeakReference<FragmentActivityControler> (this);

        mAdapter = new MyAdapter(getSupportFragmentManager());
        mAdapter.setPrimaryItem(null, 1, new Fragment());

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

    }

    public static WeakReference<FragmentActivityControler> getActivity() {
        return fragmentControlerActivity;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public void logOut(){
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
    }
    public void displayDialog (int message,String messageControleur){
        if(messageControleur == null) {
            DialogError dialog = new DialogError();
            Bundle args = new Bundle();
            args.putInt(SettingGloblal.MESSAGEERROR, message);
            dialog.setArguments(args);
            dialog.show(getSupportFragmentManager(), "ErrorConnexion");
        }
        else{
            DialogConfirmation dialog = new DialogConfirmation();
            Bundle args = new Bundle();
            args.putInt(SettingGloblal.MESSAGEERROR, message);
            args.putString(SettingGloblal.MESSAGE_CONTROLER,messageControleur);
            dialog.setArguments(args);
            dialog.show(getSupportFragmentManager(), "ErrorConnexion");
        }
    }
    @Override
    public void onBackPressed() {
        displayDialog(R.string.pressedBackButton,null);
    }

    public void changeFragmentToLeft(){
        Log.d("Debug","Appel du controleur pour changer la frame");
        mPager.setCurrentItem(mPager.getCurrentItem()-1,true);
    }

    public void changeFragmentToRigth(){
        Log.d("Debug","Appel du controleur pour changer la frame");
        mPager.setCurrentItem(mPager.getCurrentItem()+1,true);
    }

    /**
     * Create Fragment
     * @param fragment
     */
    // Put the fragmlent on the dinamic way.
    public void toProfileConfiguration (Fragment fragment) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(
                R.anim.card_flip_right_in,
                R.anim.card_flip_right_out,
                R.anim.card_flip_left_in,
                R.anim.card_flip_left_out);

        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void updateProfile(){
        ProfileConfigurationFragment currentFragment = (ProfileConfigurationFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        currentFragment.updateProfile();
    }



    /**
     * Remove Fragment
     */
    public void toViewPager() {

        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        FragmentTransaction ft = fm.beginTransaction();
        ft.remove(fragment);
        ft.commit();
    }

    public static class MyAdapter extends FragmentStatePagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }


        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }


        @Override
        public Fragment getItem(int position) {

            Log.d("position", "" + position);
            switch (position) {
                case 0:
                    return new CommunityFragment();
                case 1:
                    return new ResearchFragment();
                case 2:
                    return new PictureFragment();
            }
            return null;
        }
    }
}
