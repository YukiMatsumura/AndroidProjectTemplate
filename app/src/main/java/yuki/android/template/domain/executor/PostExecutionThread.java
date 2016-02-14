package yuki.android.template.domain.executor;

import rx.Scheduler;

public interface PostExecutionThread {

    Scheduler getScheduler();
}
