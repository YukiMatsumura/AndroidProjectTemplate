package yuki.android.template.presentation.executor;

import rx.Scheduler;
import rx.schedulers.Schedulers;
import yuki.android.template.domain.executor.PostExecutionThread;

public class TestExecutionThread implements PostExecutionThread {

    @Override
    public Scheduler getScheduler() {
        return Schedulers.immediate();
    }
}