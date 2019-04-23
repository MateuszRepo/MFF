package com.mateusz.mff.ativities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.mateusz.mff.R;
import com.mateusz.mff.adapters.ViewPagerAdapter;
import com.mateusz.mff.database.MoneyDao;
import com.mateusz.mff.fragments.OverviewFragment;
import com.mateusz.mff.fragments.TimeCounterFragment;

public class MainActivity extends AppCompatActivity implements OverviewFragment.OnDateSelected {
    private EditText etStake;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    public static final String STAKE="stake";
    public static final String STAKE_HOUR="stake hours";
    public static final String SP_NAME="sharedpreferences";
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout= (TabLayout) findViewById(R.id.tabs);
        viewPager= (ViewPager) findViewById(R.id.viewpager);
        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        sharedPreferences=getApplicationContext().getSharedPreferences(SP_NAME,MODE_PRIVATE);
        double stake =sharedPreferences.getFloat(STAKE,-1);
        if(stake==-1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_stake_layout, null);
            builder.setView(dialogView);
            etStake = (EditText) dialogView.findViewById(R.id.etStake);
            builder.setCancelable(false);
            builder.setPositiveButton("Zatwierdz", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String stake=etStake.getText().toString();
                    if(stake.equals("")){
                        stake="0";
                    }
                    double stakeHour = Double.parseDouble(stake);
                    double stakeminutes = stakeHour / 60;
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putFloat(STAKE, (float) stakeminutes);
                    editor.putFloat(STAKE_HOUR,(float)stakeHour);
                    editor.apply();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }



    }

    @Override
    public void onDateSelected(Integer day, Integer month, Integer year) {
        Bundle bundle=new Bundle();
        bundle.putInt(MoneyDao.DAY,day);
        bundle.putInt(MoneyDao.MONTH,month);
        bundle.putInt(MoneyDao.YEAR,year);
        Intent intent=new Intent(this,DetailsActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void setUpViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new TimeCounterFragment(),"Timer");
        adapter.addFrag(new OverviewFragment(),"Przegląd");
        viewPager.setAdapter(adapter);
    }
}
