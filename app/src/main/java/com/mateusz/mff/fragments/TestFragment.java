package com.mateusz.mff.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mateusz.mff.R;

import android.text.format.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Mateusz on 06.03.2017.
 */
public class TestFragment extends Fragment {
    TextView tvTest;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.testfragment_layout,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvTest= (TextView) view.findViewById(R.id.tvTest);
        Calendar calendar=Calendar.getInstance();

        Date date=new Date();
        int dateDay = calendar.get(Calendar.DAY_OF_MONTH);
        int dateMonth=calendar.get(Calendar.MONTH);
        int dateYear = calendar.get(Calendar.YEAR);
        tvTest.setText(dateDay+" "+dateMonth+" "+dateYear);
    }
}
