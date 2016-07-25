package com.example.arnauddupeyrat.Animalis.View.Dialog;




import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public class SpinnerFragment extends Fragment {

        private static final String TAG = SpinnerFragment.class.getSimpleName();
        private static final int SPINNER_WIDTH = 100;
        private static final int SPINNER_HEIGHT = 100;

        @Override
        public View onCreateView( LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

            ProgressBar progressBar = new ProgressBar(container.getContext());
            if (container instanceof FrameLayout) {
                FrameLayout.LayoutParams layoutParams =
                        new FrameLayout.LayoutParams(SPINNER_WIDTH, SPINNER_HEIGHT, Gravity.CENTER);
                progressBar.setLayoutParams(layoutParams);
            }
            return progressBar;
        }
    }