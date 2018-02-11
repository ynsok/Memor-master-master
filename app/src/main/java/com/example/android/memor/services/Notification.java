package com.example.android.memor.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import com.example.android.memor.MainActivity;
import com.example.android.memor.R;
import com.example.android.memor.data.Contract;

import java.util.Random;

/**
 * Created by Krzys on 06.02.2018.
 */

public class Notification {
    private static final int ID = 100;


    public static void ShowNotification(Context context, Cursor cursor) {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String name = null;
        String word = null;
        if (cursor != null) {
            int count = cursor.getCount();
            Random random = new Random();
            int positon = random.nextInt(count);
            cursor.moveToPosition(positon);
            name = cursor.getString(cursor.getColumnIndex(Contract.WordsContainer._TRANSLATED));
            word = cursor.getString(cursor.getColumnIndex(Contract.WordsContainer._WORD));
        }
        Intent intentM = new Intent(context, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, 100, intentM, 0);
        android.app.Notification noti = new android.app.Notification.Builder(context)
                .setTicker("TickerTitle")
                .setContentTitle("Do you remember this words?")
                .setContentText(word)
                .setSubText(name)
                .setSmallIcon(R.drawable.kartka)
                .setAutoCancel(true)
                .setContentIntent(pIntent).getNotification();
        noti.flags = android.app.Notification.FLAG_AUTO_CANCEL;
        if (nm != null) {
            nm.notify(ID, noti);
        }

    }
}
