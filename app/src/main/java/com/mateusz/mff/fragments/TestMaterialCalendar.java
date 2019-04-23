package com.mateusz.mff.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mateusz.mff.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

/**
 * Created by Mateusz on 06.03.2017.
 */
public class TestMaterialCalendar extends Fragment {
    MaterialCalendarView calendarView;
    TextView tvCalendarTest;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.testmaterialcalendar_layout,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        calendarView= (MaterialCalendarView) view.findViewById(R.id.testCalendarView);
        tvCalendarTest= (TextView) view.findViewById(R.id.tvCalendarTest);
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                Toast.makeText(getActivity(),date.getDay()+" "+date.getMonth()+" "+date.getYear(),Toast.LENGTH_SHORT).show();

            }
        });

        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                Toast.makeText(getActivity(),date.getMonth()+"",Toast.LENGTH_SHORT).show();
            }
        });
    }
}