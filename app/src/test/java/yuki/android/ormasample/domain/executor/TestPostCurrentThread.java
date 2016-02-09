package yuki.android.ormasample.domain.executor;

import rx.Scheduler;
import rx.schedulers.Schedulers;
import yuki.android.ormasample.data.executor.TestCurrentThreadExecutor;

public class TestPostCurrentThread implements PostExecutionThread {

    @Override
    public Scheduler getScheduler() {
        return Schedulers.from(new TestCurrentThreadExecutor());
    }
}
