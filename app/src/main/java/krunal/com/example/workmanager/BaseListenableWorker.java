package krunal.com.example.workmanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.ListenableWorker;
import androidx.work.WorkerParameters;
import androidx.work.impl.utils.futures.SettableFuture;

import com.google.common.util.concurrent.ListenableFuture;

/**
 * Created by ZivChao on 2021/9/3
 *
 * @author ZivChao
 */
public abstract class BaseListenableWorker extends ListenableWorker {
    /**
     * @param appContext   The application {@link Context}
     * @param workerParams Parameters to setup the internal state of this worker
     */
    public BaseListenableWorker(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
    }

    @SuppressLint("RestrictedApi")
    @NonNull
    @Override
    public ListenableFuture<Result> startWork() {
        if (isAbortUndoneJob()) {
            Log.e("MyListenableWork", "isAbortUndoneJob = false");
            SettableFuture future = SettableFuture.create();
            future.set(Result.failure());
            return future;
        } else {
            Log.e("MyListenableWork", "isAbortUndoneJob = true");
            return startWorker();
        }
    }

    public abstract ListenableFuture<Result> startWorker();

    public abstract boolean isAbortUndoneJob();

}
