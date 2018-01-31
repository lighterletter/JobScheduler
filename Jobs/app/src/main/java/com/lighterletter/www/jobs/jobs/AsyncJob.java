package com.lighterletter.www.jobs.jobs;

import android.app.job.JobParameters;
import android.app.job.JobService;

public class AsyncJob extends JobService {

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

}
