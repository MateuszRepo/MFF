package com.mateusz.mff.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.mateusz.mff.R;
import com.mateusz.mff.database.MoneyDao;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Created by Mateusz on 06.03.2017.
 */
public class DetailsFragment extends Fragment implements View.OnClickListener{

    private EditText etHours, etTips, etStolen, etOthers,
        etComment;
    private Button btnHours, btnTips, btnStolen, btnOthers,
        btnComment;
    private Boolean ifactive=false;
    private double[] earnings;
    private String comment;
    private MoneyDao moneyDao;
    private InputMethodManager inputMethodManager;
    private DecimalFormat decimalFormat;
    private Integer day;
    private Integer month;
    private Integer year;
    private String column;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        decimalFormat=new DecimalFormat("0.00",new DecimalFormatSymbols(Locale.US));
        inputMethodManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        moneyDao=new MoneyDao(getActivity().getApplicationContext());
        Bundle arguments=getArguments();
        day = arguments.getInt(MoneyDao.DAY);
        month = arguments.getInt(MoneyDao.MONTH);
        year = arguments.getInt(MoneyDao.YEAR);
        Cursor cursor=moneyDao.getDayCursor(day, month, year);
        Cursor cursor1=moneyDao.getDayCursor(day, month, year);
        earnings=moneyDao.getEarnings(cursor);
        comment=moneyDao.getComment(cursor1);
        return inflater.inflate(R.layout.detailfragment_layout,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etHours= (EditText) view.findViewById(R.id.etHours);
        etTips= (EditText) view.findViewById(R.id.etTips);
        etStolen= (EditText) view.findViewById(R.id.etStolen);
        etOthers= (EditText) view.findViewById(R.id.etOthers);
        etComment= (EditText) view.findViewById(R.id.etComment);

        btnHours= (Button) view.findViewById(R.id.btnHours);
        btnTips= (Button) view.findViewById(R.id.btnTips);
        btnStolen= (Button) view.findViewById(R.id.btnStolen);
        btnOthers= (Button) view.findViewById(R.id.btnOthers);
        btnComment= (Button) view.findViewById(R.id.btnComment);

        btnHours.setOnClickListener(this);
        btnTips.setOnClickListener(this);
        btnStolen.setOnClickListener(this);
        btnOthers.setOnClickListener(this);
        btnComment.setOnClickListener(this);

        etHours.setText(decimalFormat.format(earnings[0]));
        etTips.setText(decimalFormat.format(earnings[1]));
        etStolen.setText(decimalFormat.format(earnings[2]));
        etOthers.setText(decimalFormat.format(earnings[3]));
        etComment.setText(comment);
    }

    @Override
    public void onClick(View v) {
        Integer id=v.getId();

        if(id==R.id.btnHours){
            activeOrDisactiveEt(etHours);
            changeButtonText(btnHours);
            column=MoneyDao.HOURS;
        }else if(id==R.id.btnTips){
            activeOrDisactiveEt(etTips);
            changeButtonText(btnTips);
            column=MoneyDao.TIPS;
        }else if(id==R.id.btnStolen){
            activeOrDisactiveEt(etStolen);
            changeButtonText(btnStolen);
            column=MoneyDao.STOLEN;
        }else if(id==R.id.btnOthers){
            activeOrDisactiveEt(etOthers);
            changeButtonText(btnOthers);
            column=MoneyDao.OTHERS;
        }else if(id==R.id.btnComment){
            activeOrDisactiveEt(etComment);
            changeButtonText(btnComment);
            column=MoneyDao.COMMENT;
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
            if(editText.getId()==R.id.etComment){
                moneyDao.insertOrUpdateComment(day,month,year,text);
            }else{
                if(text.equals("")){
                    text="0";
                    editText.setText("0.00");
                }
                double cash=Double.parseDouble(text);
                moneyDao.insertOrUpdateCash(day,month,year,cash,column);
            }
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
