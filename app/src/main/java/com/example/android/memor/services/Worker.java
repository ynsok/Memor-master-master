package com.example.android.memor.services;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.example.android.memor.data.Contract;
import com.example.android.memor.data.SQLhelper;

/**
 * Created by Krzys on 06.02.2018.
 */

public class Worker extends AsyncTask<Void,Void,Cursor> {
    private SQLhelper sqLhelper;
    private SQLiteDatabase sqLiteDatabase;
    private Context context;

    public Worker(Context context){
        this.context = context;
    }

    @Override
    protected Cursor doInBackground(Void... voids) {

        sqLhelper = new SQLhelper(context);
        sqLiteDatabase = sqLhelper.getWritableDatabase();

        return sqLiteDatabase.query(Contract.WordsContainer.TABLE_NAME, null, null, null, null, null, null, null);

    }

    @Override
    protected void onPostExecute(Cursor cursor) {
        super.onPostExecute(cursor);
        if(cursor !=null) {
            Notification.ShowNotification(context, cursor);
        }

    }
}
