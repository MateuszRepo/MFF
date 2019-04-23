package com.mateusz.mff.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * Created by Mateusz on 23.02.2017.
 */
public class MoneyDao {
    DBHelper dbHelper;
    public static final String MONEY_TABLE = "money";
        public static final String DAY = "day";
        public static final String MONTH = "month";
        public static final String YEAR = "year";
        public static final String HOURS = "hours";
        public static final String TIPS = "tips";
        public static final String STOLEN = "stolen";
        public static final String OTHERS = "others";
        public static final String COMMENT = "comment";

    public MoneyDao(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    public void updateComment(Integer day, Integer month, Integer year, String comment) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COMMENT, comment);
        dbHelper.getWritableDatabase().update(MONEY_TABLE, contentValues, DAY + "=? and " + MONTH + "=? and " +
                YEAR + "=?", intDateToString(day, month, year));
    }

    public void updateCash(Integer day, Integer month, Integer year, Double cash,String column){
        ContentValues contentValues = new ContentValues();
        contentValues.put(column, cash);
        dbHelper.getWritableDatabase().update(MONEY_TABLE, contentValues, DAY + "=? and " + MONTH + "=? and " +
                YEAR + "=?", intDateToString(day, month, year));
    }

    private String[] intDateToString(Integer day, Integer month, Integer year) {
        String[] date = new String[3];
        date[0] = day.toString();
        date[1] = month.toString();
        date[2] = year.toString();
        return date;
    }

    public Cursor getDayCursor(Integer day, Integer month, Integer year) {
        return dbHelper.getReadableDatabase().query(MONEY_TABLE, null, DAY + "=? AND " + MONTH + "=? AND " + YEAR + "=?",
                intDateToString(day, month, year), null, null, null, null);
    }

    public Cursor getMonthCursor(Integer month,Integer year){
        return dbHelper.getReadableDatabase().query(MONEY_TABLE, null,MONTH + "=? AND " + YEAR + "=?",
                new String[]{month.toString(),year.toString()}, null, null, null, null);
    }

    public double[] getEarnings(Cursor cursor){
        double[] earnings=new double[4];
        while(cursor.moveToNext()) {
            earnings[0] += cursor.getDouble(cursor.getColumnIndex(HOURS));
            earnings[1] += cursor.getDouble(cursor.getColumnIndex(TIPS));
            earnings[2] += cursor.getDouble(cursor.getColumnIndex(STOLEN));
            earnings[3] += cursor.getDouble(cursor.getColumnIndex(OTHERS));
        }
        return earnings;
    }

    public String getComment(Cursor cursor){
        if (cursor.moveToNext()){
            return cursor.getString(cursor.getColumnIndex(COMMENT));
        }
        return "";
    }
    public void insertOrUpdateCash(Integer day, Integer month, Integer year,double cash, String column){
        if(ifContainRow(day,month,year)){
            updateCash(day,month,year,cash,column);
        }else{
            insertRowCash(day,month,year,cash,column);
        }
    }
    public void insertOrUpdateComment(Integer day, Integer month, Integer year,String comment){
        if(ifContainRow(day,month,year)){
            updateComment(day,month,year,comment);
        }else{
            insertRowComment(day,month,year,comment);
        }
    }
    private boolean ifContainRow(Integer day, Integer month, Integer year){

        if(getDayCursor(day, month,year).getCount()>0){
            return true;
        }else{
            return false;
        }
    }
    public void insertRowCash(Integer day,Integer month,Integer year, double cash, String column){
        ContentValues contentValues=new ContentValues();
        contentValues.put(column,cash);
        contentValues.put(DAY,day);
        contentValues.put(MONTH,month);
        contentValues.put(YEAR,year);
        dbHelper.getWritableDatabase().insertOrThrow(MONEY_TABLE,null,contentValues);
    }

    public void insertRowComment(Integer day,Integer month,Integer year,String comment){
        ContentValues contentValues=new ContentValues();
        contentValues.put(COMMENT,comment);
        contentValues.put(DAY,day);
        contentValues.put(MONTH,month);
        contentValues.put(YEAR,year);
        dbHelper.getWritableDatabase().insertOrThrow(MONEY_TABLE,null,contentValues);
    }

}