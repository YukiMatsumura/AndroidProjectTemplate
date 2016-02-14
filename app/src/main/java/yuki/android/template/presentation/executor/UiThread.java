package yuki.android.template.presentation.executor;

import javax.inject.Inject;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import yuki.android.template.di.scope.ApplicationScope;
import yuki.android.template.domain.executor.PostExecutionThread;

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
