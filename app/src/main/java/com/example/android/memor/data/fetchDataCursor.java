package com.example.android.memor.data;

import android.database.Cursor;

/**
 * Created by Krzys on 14.01.2018.
 */

public class fetchDataCursor {


    public fetchDataCursor() {
    }


    public String getWord(int position, Cursor cursor) {
        String word = null;
        if (!cursor.moveToPosition(position)) {
            return word;
        }
        word = cursor.getString(cursor.getColumnIndex(Contract.WordsContainer._WORD));
        return word;
    }

    public int getID(int position, Cursor cursor) {
        int id = 0;
        if (!cursor.moveToPosition(position)) {
            return id;
        }
        id = cursor.getInt(cursor.getColumnIndex(Contract.WordsContainer._ID));
        return id;
    }

    public String getTranslated(int position, Cursor cursor) {
        String translated = null;
        if (!cursor.moveToPosition(position)) return translated;
        translated = cursor.getString(cursor.getColumnIndex(Contract.WordsContainer._TRANSLATED));
        return translated;
    }

  /*  public static Cursor swapCursor(Cursor c){
        if(cursor == c )
        {
            return null;
        }
        Cursor temp =c;
        cursor = c;
        if(c != null)
        {
            context.notifyAll();
        }
        return temp;
    }*/

}
