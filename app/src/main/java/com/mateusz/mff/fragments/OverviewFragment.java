package com.mateusz.mff.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mateusz.mff.R;
import com.mateusz.mff.database.MoneyDao;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Created by Mateusz on 23.02.2017.
 */
public class OverviewFragment extends android.support.v4.app.Fragment {
    MaterialCalendarView materialCalendarView;
    TextView tvHours, tvTips, tvStolen, tvOthers,tvTotal;
    MoneyDao moneyDao;
    OnDateSelected mCallback;
    DecimalFormat decimalFormat;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        decimalFormat=new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US));
        moneyDao=new MoneyDao(getActivity().getApplicationContext());
        return inflater.inflate(R.layout.overview_fragment_layout,container,false);
    }

    @Override
    public void onResume() {
        super.onResume();
        CalendarDay date=materialCalendarView.getCurrentDate();
        setEditTexts(date);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        materialCalendarView= (MaterialCalendarView) view.findViewById(R.id.calendarView);

        tvHours= (TextView) view.findViewById(R.id.tvHours);
        tvTips= (TextView) view.findViewById(R.id.tvTips);
        tvStolen= (TextView) view.findViewById(R.id.tvStolen);
        tvOthers= (TextView) view.findViewById(R.id.tvOthers);
        tvTotal= (TextView) view.findViewById(R.id.tvTotal);

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                mCallback.onDateSelected(date.getDay(),date.getMonth(),date.getYear());

            }
        });

        materialCalendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                setEditTexts(date);
            }
        });
    }

    private void setEditTexts(CalendarDay date) {
        double[] earnings;
        earnings=moneyDao.getEarnings(moneyDao.getMonthCursor(date.getMonth(),date.getYear()));
        tvHours.setText(decimalFormat.format(earnings[0]));
        tvTips.setText(decimalFormat.format(earnings[1]));
        tvStolen.setText(decimalFormat.format(earnings[2]));
        tvOthers.setText(decimalFormat.format(earnings[3]));
        tvTotal.setText(decimalFormat.format(sumElements(earnings)));
    }

    public interface OnDateSelected{
        void onDateSelected(Integer day, Integer month, Integer year);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback= (OnDateSelected) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString()+"doesnt implement");
        }
    }
    private double sumElements(double [] earnings){
        double total=0.0;
        for (int i=0;i<4;i++){
            total+=earnings[i];
        }
        return total;
    }

}