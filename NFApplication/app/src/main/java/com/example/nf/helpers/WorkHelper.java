package com.example.nf.helpers;

import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public final class WorkHelper {

    private static final int TASK_REPEAT_INTERVAL = 30;
    private static final String WORKER_NAME = "Check Data";

    private WorkHelper() {
    }

    public static void startCheckingNewItems() {
        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(CheckNewContentWorker.class, TASK_REPEAT_INTERVAL, TimeUnit.MINUTES)
                .setConstraints(new Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()).build();

        WorkManager.getInstance().enqueueUniquePeriodicWork(WORKER_NAME,  ExistingPeriodicWorkPolicy.KEEP, periodicWorkRequest);
    }

    public static void stopCheckingNewItems() {
        WorkManager.getInstance().cancelUniqueWork(WORKER_NAME);
    }
}
