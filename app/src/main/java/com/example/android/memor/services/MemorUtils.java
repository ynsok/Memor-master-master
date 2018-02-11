package com.example.android.memor.services;

import android.content.Context;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;


/**
 * Created by Krzys on 05.02.2018.
 */

public class MemorUtils {

    private static final String TAG ="FIREBASE";


    public MemorUtils() {

    }



    public  static void scheduleFetchData(Context context,int number){


        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher firebaseJobDispatcher = new FirebaseJobDispatcher(driver);

        Job job = firebaseJobDispatcher.newJobBuilder()
                .setTag(TAG)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setService(MemorService.class)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setReplaceCurrent(true)
                .setTrigger(Trigger.executionWindow(number,number+500))
                .build();

        firebaseJobDispatcher.schedule(job);

    }
    public static void stopJob(Context context)
    {
        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher firebaseJobDispatcher = new FirebaseJobDispatcher(driver);
        firebaseJobDispatcher.cancel(TAG);
    }


}
