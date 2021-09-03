package krunal.com.example.workmanager;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.ListenableWorker;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.impl.utils.futures.SettableFuture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {

    private Button mOneTimeWork, mPeriodicWork,
            mChainableWork, mParallelWork,
            mCancelPeriodicWork, mWorkWithConstraints, mWorkWithData, mChainableListenableWork, temp, cancel;

    private UUID getId;

    private PeriodicWorkRequest mPeriodicWorkRequest;

    private TextView mTextView;

    public static Map<String, SettableFuture> futureMap = new HashMap<>();

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.textView);
        mOneTimeWork = findViewById(R.id.OneTimeWork);
        mPeriodicWork = findViewById(R.id.PeriodicWork);
        mChainableWork = findViewById(R.id.ChainableWork);
        mParallelWork = findViewById(R.id.parallelwork);
        mCancelPeriodicWork = findViewById(R.id.CancelPeriodicWork);
        mWorkWithConstraints = findViewById(R.id.workwithconstraints);
        mWorkWithData = findViewById(R.id.WorkWithData);
        mChainableListenableWork = findViewById(R.id.ChainableListenableWork);
        temp = findViewById(R.id.ChainableListenableTemp);
        cancel = findViewById(R.id.ChainableListenableCancel);


        mOneTimeWork.setOnClickListener(v -> {
            // This is One Time Work Request.
            OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(MyWork.class)
                    .build();

            WorkManager.getInstance().enqueue(oneTimeWorkRequest);

        });

        mPeriodicWork.setOnClickListener(v -> {
            // This is PeriodicWorkRequest it repeats every 10 Hours.
            mPeriodicWorkRequest = new PeriodicWorkRequest.Builder(MyPeriodicWork.class,
                    10, TimeUnit.HOURS)
                    .addTag("periodicWorkRequest")
                    .build();

            WorkManager.getInstance().enqueue(mPeriodicWorkRequest);

        });

        mChainableWork.setOnClickListener(v -> {
            // This is Chainable Work Request it Runs one after author in sequence.
            OneTimeWorkRequest MyWorkA = new OneTimeWorkRequest.Builder(MyWorkA.class)
                    .build();
            OneTimeWorkRequest MyWorkB = new OneTimeWorkRequest.Builder(MyWorkB.class)
                    .build();
            OneTimeWorkRequest MyWorkC = new OneTimeWorkRequest.Builder(MyWorkC.class)
                    .build();

            WorkManager.getInstance()
                    .beginWith(MyWorkA)
                    .then(MyWorkB)
                    .then(MyWorkC)
                    .enqueue();
        });

        mChainableListenableWork.setOnClickListener(v -> {
            // This is Chainable Work Request it Runs one after author in sequence.
            OneTimeWorkRequest MyListenableWorkA = new OneTimeWorkRequest.Builder(MyListenableWorkA.class).addTag("MyListenableWork")
                    .build();
            OneTimeWorkRequest MyListenableWorkB = new OneTimeWorkRequest.Builder(MyListenableWorkB.class).addTag("MyListenableWork")
                    .build();
            OneTimeWorkRequest MyListenableWorkC = new OneTimeWorkRequest.Builder(MyListenableWorkC.class).addTag("MyListenableWork")
                    .build();
            OneTimeWorkRequest MyListenableWorkD = new OneTimeWorkRequest.Builder(MyListenableWorkD.class).addTag("MyListenableWork")
                    .build();

            WorkManager.getInstance()
                    .beginUniqueWork("MyListenableWorkD", ExistingWorkPolicy.KEEP, MyListenableWorkD)
                    .then(MyListenableWorkB)
                    .then(MyListenableWorkC)
                    .enqueue();
        });

        temp.setOnClickListener(v -> {
            SettableFuture result = futureMap.get("MyListenableWorkA");
            result.set(ListenableWorker.Result.success());
        });

        cancel.setOnClickListener(v -> {
            WorkManager.getInstance().cancelAllWorkByTag("MyListenableWork");
        });

        mParallelWork.setOnClickListener(v -> {
            // Here MyWorkA and MyWorkB will run in Parallel and then MyWorkC will run.
            OneTimeWorkRequest MyWorkA = new OneTimeWorkRequest.Builder(MyWorkA.class)
                    .build();
            OneTimeWorkRequest MyWorkB = new OneTimeWorkRequest.Builder(MyWorkB.class)
                    .build();
            OneTimeWorkRequest MyWorkC = new OneTimeWorkRequest.Builder(MyWorkC.class)
                    .build();

            List<OneTimeWorkRequest> beginWith_A_and_B = new ArrayList<>();
            beginWith_A_and_B.add(MyWorkA);
            beginWith_A_and_B.add(MyWorkB);

            WorkManager.getInstance()
                    .beginWith(beginWith_A_and_B)
                    .then(MyWorkC)
                    .enqueue();


        });

        mCancelPeriodicWork.setOnClickListener(v -> {
            // We are Canceling PeriodicWork by Id.
            getId = mPeriodicWorkRequest.getId();
            WorkManager.getInstance().cancelWorkById(getId);
            Toast.makeText(this, "PeriodicWork Cancel By Id", Toast.LENGTH_LONG).show();
        });

        mWorkWithConstraints.setOnClickListener(v -> {
            // We are putting Constraints means than if the device is
            // CONNECTED to internet than only we Request to do Work.
            // And We are also Checking for the status if the Work Have completed or not.
            Constraints constraints = new Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build();

            OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(MyWork.class)
                    .setConstraints(constraints)
                    .build();

            WorkManager.getInstance().enqueue(oneTimeWorkRequest);

            WorkManager.getInstance().getWorkInfoByIdLiveData(oneTimeWorkRequest.getId())
                    .observe(this, new Observer<WorkInfo>() {
                        @Override
                        public void onChanged(@Nullable WorkInfo workInfo) {

                            if (workInfo != null) {
                                Toast.makeText(MainActivity.this, "oneTimeWorkRequest: " +
                                        String.valueOf(workInfo.getState().name()), Toast.LENGTH_LONG)
                                        .show();
                            }

                            if (workInfo != null && workInfo.getState().isFinished()) {
                                // ... do something with the result ...
                                Toast.makeText(MainActivity.this, "Work Finished",
                                        Toast.LENGTH_LONG).show();
                            }

                        }
                    });


        });

        mWorkWithData.setOnClickListener(v -> {
            // Here we are Passing Data to MyWorkWithData class.
            // We are also getting Data from MyWorkWithData class.
            // After getting Data from MyWorkWithData class we are setting to TextView.
            Data data = new Data.Builder()
                    .putString(MyWorkWithData.EXTRA_TITLE, "I came From Activity!")
                    .putString(MyWorkWithData.EXTRA_TEXT, "This is Message.")
                    .build();

            OneTimeWorkRequest oneTimeWorkRequest =
                    new OneTimeWorkRequest.Builder(MyWorkWithData.class)
                            .setInputData(data)
                            .build();

            WorkManager.getInstance().enqueue(oneTimeWorkRequest);


            WorkManager.getInstance().getWorkInfoByIdLiveData(oneTimeWorkRequest.getId())
                    .observe(this, new Observer<WorkInfo>() {
                        @Override
                        public void onChanged(@Nullable WorkInfo workInfo) {

                            if (workInfo != null && workInfo.getState().isFinished()) {
                                String message = workInfo.getOutputData().getString(MyWorkWithData.EXTRA_OUTPUT_MESSAGE);
                                mTextView.setText(message);
                            }

                        }
                    });

        });

    }
}
