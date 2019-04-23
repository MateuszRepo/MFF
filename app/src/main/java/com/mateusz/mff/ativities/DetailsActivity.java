package com.mateusz.mff.ativities;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.mateusz.mff.R;
import com.mateusz.mff.fragments.DetailsFragment;


/**
 * Created by Mateusz on 13.03.2017.
 */
public class DetailsActivity extends AppCompatActivity {
    private FrameLayout fragmentContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailsactivity_layout);
        fragmentContainer= (FrameLayout) findViewById(R.id.fragment_container_details);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        DetailsFragment detailsFragment=new DetailsFragment();
        detailsFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_details,detailsFragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actionbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int idItem=item.getItemId();
        if(idItem==R.id.actionSettings){
            Intent intent=new Intent(getApplicationContext(),SettingsActivity.class);
            startActivity(intent);
        }
        return true;
    }
}
