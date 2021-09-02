package krunal.com.example.workmanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.ListenableWorker;
import androidx.work.WorkerParameters;
import androidx.work.impl.utils.futures.SettableFuture;

import com.google.common.util.concurrent.ListenableFuture;

public class MyListenableWorkB extends ListenableWorker {

    private static final String TAB = MyListenableWorkB.class.getSimpleName();

    public MyListenableWorkB(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @SuppressLint("RestrictedApi")
    @NonNull
    @Override
    public ListenableFuture<Result> startWork() {
        Log.e(TAB, "MyListenableWorkB");
        SettableFuture future = SettableFuture.create();
        future.set(Result.failure());
        return future;
    }

}