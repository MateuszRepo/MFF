package com.mateusz.mff.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mateusz.mff.R;
import com.mateusz.mff.database.MoneyDao;

/**
 * Created by Mateusz on 09.03.2017.
 */
public class TestDataBase extends Fragment {
    TextView tvDBTest;
    MoneyDao moneyDao;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.testdatabase_layout,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvDBTest= (TextView) view.findViewById(R.id.tvTestDB);


    }
}
