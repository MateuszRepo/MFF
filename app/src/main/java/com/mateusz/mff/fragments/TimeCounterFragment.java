package com.mateusz.mff.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mateusz.mff.R;
import com.mateusz.mff.ativities.MainActivity;
import com.mateusz.mff.database.MoneyDao;

import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Mateusz on 04.03.2017.
 */
public class TimeCounterFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    public static final String KEY="time";
    private Button btnStartStop;
    private boolean ifStarted=false;
    private Calendar calendar;
    private TextView tvTime;
    private SharedPreferences sharedPreferences;
    private MoneyDao moneyDao;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        moneyDao=new MoneyDao(getActivity());
        return inflater.inflate(R.layout.timecounter_fragment_layout,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnStartStop= (Button) view.findViewById(R.id.btnStartStop);
        tvTime= (TextView) view.findViewById(R.id.tvTime);
        sharedPreferences=getActivity().getApplicationContext().getSharedPreferences(MainActivity.SP_NAME,Context.MODE_PRIVATE);
        long minutes=sharedPreferences.getLong(KEY,-1);

        if(minutes!=-1){
            btnStartStop.setText("STOP");
            btnStartStop.setBackgroundColor(Color.RED);
            ifStarted=true;
        }

        btnStartStop.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnStartStop){
            if(ifStarted){
                btnStartStop.setText("START");
                btnStartStop.setBackgroundColor(Color.GREEN);
                long minutesCurrent=getCurrentMinutes();
                long minutesSharedPreferences=sharedPreferences.getLong(KEY,-1);
                long diffrence=minutesCurrent-minutesSharedPreferences;
                tvTime.setText((int)(diffrence/60)+"h:"+(diffrence%60)+"min");
                int day=sharedPreferences.getInt(MoneyDao.DAY,1);
                int month=sharedPreferences.getInt(MoneyDao.MONTH,0);
                int year = sharedPreferences.getInt(MoneyDao.YEAR,1970);
                double stake=sharedPreferences.getFloat(MainActivity.STAKE,0);
                double earned=stake*diffrence;
                moneyDao.insertOrUpdateCash(day,month,year,earned,MoneyDao.HOURS);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.remove(MoneyDao.DAY);
                editor.remove(MoneyDao.MONTH);
                editor.remove(MoneyDao.YEAR);
                editor.remove(KEY);
                editor.apply();
                ifStarted=!ifStarted;
            }else{
                long minutes=getCurrentMinutes();
                int day=calendar.get(Calendar.DAY_OF_MONTH);
                int month=calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putInt(MoneyDao.DAY,day);
                editor.putInt(MoneyDao.MONTH,month);
                editor.putInt(MoneyDao.YEAR,year);
                editor.putLong(KEY,minutes);
                editor.apply();
                btnStartStop.setText("STOP");
                btnStartStop.setBackgroundColor(Color.RED);
                ifStarted=!ifStarted;
            }
        }

    }

    private long getCurrentMinutes(){
        calendar=Calendar.getInstance();
        long milis=calendar.getTimeInMillis();
        long seconds=TimeUnit.MILLISECONDS.toSeconds(milis);
        return TimeUnit.SECONDS.toMinutes(seconds);
    }

}
