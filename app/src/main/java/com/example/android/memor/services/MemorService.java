package com.example.android.memor.services;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by Krzys on 05.02.2018.
 */
public class MemorService extends JobService {

    @Override
    public boolean onStartJob(final JobParameters job) {
        Worker worker = new Worker(getApplicationContext());
        worker.execute();
        jobFinished(job,false);

        return false;
    }


    @Override
    public boolean onStopJob(JobParameters job) {
            jobFinished(job,false);
        return true;
    }
}
