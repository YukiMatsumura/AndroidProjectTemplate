package yuki.android.template.domain.executor;

import rx.Scheduler;
import rx.schedulers.Schedulers;
import yuki.android.template.data.executor.TestCurrentThreadExecutor;

public class TestPostCurrentThread implements PostExecutionThread {

    @Override
    public Scheduler getScheduler() {
        return Schedulers.from(new TestCurrentThreadExecutor());
    }
}
