package com.example.android.memor.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Krzys on 06.01.2018.
 */

/*In these claas it's creating DataBase*/

public class SQLhelper extends SQLiteOpenHelper {
    private static final int VERSION_DB = 1;
    private static final String NAME_DB = "dataBaseTranslator.db";
    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + Contract.WordsContainer.TABLE_NAME + "(" +
                    Contract.WordsContainer._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + Contract.WordsContainer._WORD + " TEXT NOT NULL,"
                    + Contract.WordsContainer._TRANSLATED + " TEXT NOT NULL " + ");";
    private static final String SQL_DELATED_TABLE = "DROP TABLE IF EXIST " + Contract.WordsContainer.TABLE_NAME;

    public SQLhelper(Context context) {
        super(context, NAME_DB, null, VERSION_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
//        sqLiteDatabase.execSQL("INSERT INTO TABLE_NAME VALUES ('1','CAT','KOT')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELATED_TABLE);
        onCreate(sqLiteDatabase);

    }
}
