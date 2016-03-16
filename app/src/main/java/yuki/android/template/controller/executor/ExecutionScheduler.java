package yuki.android.template.controller.executor;

import rx.Scheduler;
import rx.schedulers.Schedulers;

public class ExecutionScheduler {

    public Scheduler getScheduler() {
        return Schedulers.from(new JobExecutor());
    }
}
