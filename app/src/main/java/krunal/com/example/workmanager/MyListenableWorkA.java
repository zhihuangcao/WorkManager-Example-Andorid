package krunal.com.example.workmanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;
import androidx.work.impl.utils.futures.SettableFuture;

import com.google.common.util.concurrent.ListenableFuture;

public class MyListenableWorkA extends BaseListenableWorker {

    private static final String TAB = MyListenableWorkA.class.getSimpleName();

    public MyListenableWorkA(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @SuppressLint("RestrictedApi")
    @NonNull
    @Override
    public ListenableFuture<Result> startWorker() {
        Log.e(TAB, "MyListenableWorkA");
        SettableFuture future = SettableFuture.create();
        MainActivity.futureMap.put("MyListenableWorkA", future);
//        future.set(Result.success());
        return future;
    }

    @Override
    public boolean isAbortUndoneJob() {
        return MainActivity.futureMap.size() > 0;
    }

}
