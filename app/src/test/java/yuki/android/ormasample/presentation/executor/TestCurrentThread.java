package yuki.android.ormasample.presentation.executor;

import rx.Scheduler;
import rx.schedulers.Schedulers;
import yuki.android.ormasample.domain.executor.PostExecutionThread;

public class TestCurrentThread implements PostExecutionThread {

    @Override
    public Scheduler getScheduler() {
        return Schedulers.immediate();
    }
}