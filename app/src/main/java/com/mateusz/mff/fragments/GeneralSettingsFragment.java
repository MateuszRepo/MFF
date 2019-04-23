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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mateusz.mff.R;
import com.mateusz.mff.ativities.MainActivity;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;


/**
 * Created by Mateusz on 16.03.2017.
 */
public class GeneralSettingsFragment extends Fragment implements View.OnClickListener {
    private EditText etStake;
    private Button btnStake;
    boolean ifactive=false;
    private InputMethodManager inputMethodManager;
    private SharedPreferences sharedPreferences;
    private DecimalFormat decimalFormat;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        decimalFormat=new DecimalFormat("0.00",new DecimalFormatSymbols(Locale.US));
        sharedPreferences=getActivity().getApplicationContext().getSharedPreferences(MainActivity.SP_NAME,Context.MODE_PRIVATE);
        inputMethodManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        return inflater.inflate(R.layout.general_settings_layout, container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etStake= (EditText) view.findViewById(R.id.etStake);
        btnStake= (Button) view.findViewById(R.id.btnStake);
        double stakeHour =(double)sharedPreferences.getFloat(MainActivity.STAKE_HOUR,0);
        etStake.setText(decimalFormat.format(stakeHour));
        btnStake.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.btnStake){
            activeOrDisactiveEt(etStake);
            changeButtonText(btnStake);
        }
    }

    void changeButtonText(Button button){
        if (ifactive){
            button.setText("OK");
        }else{
            button.setText("Edytuj");
        }
    }

    void activeOrDisactiveEt(EditText editText){
        if(ifactive) {
            editText.setFocusable(false);
            editText.setClickable(false);
            editText.setCursorVisible(false);
            editText.setFocusableInTouchMode(false);
            editText.setBackgroundColor(Color.BLACK);
            String text=editText.getText().toString();
            if(text.equals("")){
                text="0";
                editText.setText("0.00");
            }
            double stakeHour=Double.parseDouble(text);
            double stakeMinutes=stakeHour/60;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(MainActivity.STAKE_HOUR);
            editor.remove(MainActivity.STAKE);

            editor.putFloat(MainActivity.STAKE_HOUR,(float)stakeHour);
            editor.putFloat(MainActivity.STAKE, (float) stakeMinutes);
            editor.apply();

            inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
            ifactive=!ifactive;
        } else {
            editText.setFocusable(true);
            editText.setClickable(true);
            editText.setCursorVisible(true);
            editText.setFocusableInTouchMode(true);
            editText.requestFocus();
            editText.setBackgroundColor(Color.GRAY);
            editText.setSelection(editText.getText().length());
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            ifactive = !ifactive;
        }

    }
}
