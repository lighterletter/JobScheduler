package com.lighterletter.www.jobs.jobs;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.lighterletter.www.jobs.MyJobScheduler;

/**
 * A Job that toasts the screen whenever it runs.
 */

public class ToastJob extends JobService {

    private static final String TAG = ToastJob.class.getSimpleName();

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            Toast.makeText(ToastJob.this, "Running TOAST Service Class", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "handleMessage: ~~~~~~running test job~~~~~~~");
            jobFinished((JobParameters) message.obj, true); // Used to queue up the task to run again at another point.
            // If this method is not called the job runs only once and it's killed by the os after some time.
            return true; // True if no further handling is needed.
        }
    });

    /**
     * Called by the system to begin execution of your job.
     */
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG, "onStartJob: on start called");
        handler.sendMessage(Message.obtain(handler, MyJobScheduler.TOAST_JOB_ID, jobParameters));
        return true;
        // True if your service needs to process the work (on a separate thread).
        // False if there's no more work to be done for this job.
    }

    /**
     * // Only called if start job returns true, called by the system if your job is cancelled before being finished,
     * Or if the conditions necessary to run the job are no longer being met.
     * <p>
     * Use for safety checks and cleanup.
     */
    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        handler.removeMessages(MyJobScheduler.TOAST_JOB_ID);
        Log.d(TAG, "onStopJob: ~~~~~~job terminated~~~~~~~");
        return true;
        // True to indicate to the JobManager whether you'd like to reschedule this job based on the retry criteria provided at job creation-time.
        // False to drop the job.
        // Regardless of the value returned, your job stops executing.
    }
}
