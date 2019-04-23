package com.mateusz.mff.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Mateusz on 23.02.2017.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME="mffdatabase.db";
    public static final int VERSION=4;
    public DBHelper(Context context) {
        super(context, DB_NAME,null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String table1=String.format("CREATE TABLE %s(_ID INTEGER PRIMARY KEY AUTOINCREMENT,%s INT," +
                "%s INT,%s INT," + "%s DOUBLE DEFAULT 0.0, %s DOUBLE DEFAULT 0.0, %s DOUBLE DEFAULT 0.0," +
                " %s DOUBLE DEFAULT 0.0,%s TEXT DEFAULT '')"
                ,MoneyDao.MONEY_TABLE,MoneyDao.DAY,
                MoneyDao.MONTH,MoneyDao.YEAR,MoneyDao.HOURS,MoneyDao.TIPS,MoneyDao.STOLEN,MoneyDao.OTHERS,MoneyDao.COMMENT);
        Log.d("TABLE",table1);
        db.execSQL(table1);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MoneyDao.MONEY_TABLE);
        onCreate(db);
    }
}
