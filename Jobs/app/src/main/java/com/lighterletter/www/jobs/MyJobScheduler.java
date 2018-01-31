package com.lighterletter.www.jobs;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;

import com.lighterletter.www.jobs.jobs.AsyncJob;
import com.lighterletter.www.jobs.jobs.ToastJob;

import static android.app.job.JobScheduler.RESULT_FAILURE;

/**
 * A Job Scheduler allows you to batch work processes based on system conditions.
 * It is the preferred option when working with background processes because it minimizes resource consumption.
 */

public class MyJobScheduler {

    private final static String TAG = MyJobScheduler.class.getSimpleName();
    public final static int TOAST_JOB_ID = 0;
    public final static int ASYNC_TASK_JOB_ID = 1;
    public final static int RETROFIT_JOB_ID = 2;

    public static void start(Context context) {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        buildToastJob(context, jobScheduler);
        buildAsyncJob(context, jobScheduler);
        buildRetrofitJob(context, jobScheduler);
    }

    /**
     * A simple Job that shows a toast.
     *
     * @param applicationContext
     * @param jobScheduler
     */
    private static void buildToastJob(Context applicationContext, JobScheduler jobScheduler) {
        JobInfo.Builder toastJob = new JobInfo
                .Builder(TOAST_JOB_ID, new ComponentName(applicationContext, ToastJob.class))
                .setMinimumLatency(1000); //Specify that this job should be delayed by the provided amount of time.
        jobScheduler.schedule(toastJob.build());
    }

    /**
     * Slightly more complex example using more parameters
     *
     * @param context
     * @param jobScheduler
     */
    private static void buildAsyncJob(Context context, JobScheduler jobScheduler) {
        Log.d(TAG, "start: entered Async Job");
        JobInfo.Builder asyncBuilder = new JobInfo
                .Builder(ASYNC_TASK_JOB_ID, new ComponentName(context.getPackageName(), AsyncJob.class.getName()))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setOverrideDeadline(10000)
                .setRequiresDeviceIdle(false)
                .setRequiresCharging(false);

        // .setPersisted(true); will the system whether your task should continue to exist after the device has been rebooted. Needs reboot permission.

        // These are not called unless the job actually terminates. If it's a recurring job we will see success at the beginning.
        // System logs will let you know if your job ended for any reason.
        if (jobScheduler.schedule(asyncBuilder.build()) <= RESULT_FAILURE) {
            Log.d(TAG, "start: ~~~~ Async Job Execution: Failure! ~~~~");
        } else {
            Log.d(TAG, "start: ~~~ Async Job Execution: Success!~~~~");
        }
    }


    private static void buildRetrofitJob(Context applicationContext, JobScheduler jobScheduler) {
        //TODO: schedule a retrofitJOB to the job scheduler.
    }

}
