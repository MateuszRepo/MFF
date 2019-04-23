package com.mateusz.mff.ativities;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import com.mateusz.mff.R;
import com.mateusz.mff.fragments.GeneralSettingsFragment;

/**
 * Created by Mateusz on 16.03.2017.
 */
public class SettingsActivity extends AppCompatActivity {
    FrameLayout frameLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_layout);


        frameLayout= (FrameLayout) findViewById(R.id.fragment_container_settings);

        GeneralSettingsFragment settingsFragment=new GeneralSettingsFragment();
        FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_settings,settingsFragment);
        fragmentTransaction.commit();
    }


}
