package yuki.android.ormasample.di.module;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import yuki.android.ormasample.data.executor.JobExecutor;
import yuki.android.ormasample.di.scope.PerActivity;
import yuki.android.ormasample.domain.executor.ThreadExecutor;
import yuki.android.ormasample.domain.repository.HistoryRepository;
import yuki.android.ormasample.domain.usecase.HistoryViewUseCase;
import yuki.android.ormasample.domain.usecase.HistoryViewUseCaseImpl;

@Module
public class ActivityModule {

    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @PerActivity
    @Provides
    Activity provideActivity() {
        return this.activity;
    }

    @PerActivity
    @Provides
    ThreadExecutor provideThreadExecutor() {
        return new JobExecutor();
    }

    @PerActivity
    @Provides
    HistoryViewUseCase provideHistoryViewUseCase(HistoryRepository repository, ThreadExecutor executor) {
        return new HistoryViewUseCaseImpl(repository, executor);
    }
}
