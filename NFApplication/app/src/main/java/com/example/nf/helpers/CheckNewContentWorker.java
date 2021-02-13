package com.example.nf.helpers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.nf.R;
import com.example.nf.data.Repository;
import com.example.nf.data.entity.Content;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class CheckNewContentWorker extends Worker {

    private static final int NEW_ITEMS_PAGE_SIZE = 5;

    private Context context;
    private CompositeDisposable disposable;

    public CheckNewContentWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
        disposable = new CompositeDisposable();
    }

    @NonNull
    @Override
    public Result doWork() {
        final Repository repository = new Repository();
        disposable.add(repository.getRemoteContent(1, NEW_ITEMS_PAGE_SIZE)
                .subscribe((contents, throwable) -> {
                    if (contents != null && contents.size() > 0) {
                        List<Content> result = ContentDiffHelper.checkIfNewItems(contents, PreferenceHelper.getLastContent(context));
                        if (result != null) {
                            NotificationHelper.sendNotification(context, context.getString(R.string.notification_title), null, R.drawable.ic_the_guardian,
                                    NavigationHelper.mainPageIntent(context));
                        }
                    }
                    disposable.dispose();
                }));

        return Result.success();
    }
}
