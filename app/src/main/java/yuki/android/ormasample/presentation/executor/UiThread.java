package yuki.android.ormasample.presentation.executor;

import javax.inject.Inject;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import yuki.android.ormasample.di.scope.ApplicationScope;
import yuki.android.ormasample.domain.executor.PostExecutionThread;

@ApplicationScope
public class UiThread implements PostExecutionThread {

    @Inject
    public UiThread() {
    }

    @Override
    public Scheduler getScheduler() {
        return AndroidSchedulers.mainThread();
    }
}