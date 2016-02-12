package yuki.android.ormasample.di.module;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import yuki.android.ormasample.di.scope.ActivityScope;
import yuki.android.ormasample.domain.executor.PostExecutionThread;
import yuki.android.ormasample.domain.executor.ThreadExecutor;
import yuki.android.ormasample.domain.repository.HistoryRepository;
import yuki.android.ormasample.domain.usecase.HistoryViewUseCase;
import yuki.android.ormasample.domain.usecase.HistoryViewUseCaseImpl;
import yuki.android.ormasample.presentation.controller.HistoryViewController;
import yuki.android.ormasample.presentation.presenter.HistoryViewPresenter;

@Module
public class ActivityModule {

    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    Activity provideActivity() {
        return this.activity;
    }

    @Provides
    @ActivityScope
    HistoryViewController provideHistoryViewController(
            HistoryViewPresenter presenter, HistoryViewUseCase useCase) {
        return new HistoryViewController(presenter, useCase);
    }

    @Provides
    @ActivityScope
    HistoryViewPresenter provideHistoryViewPresenter() {
        return new HistoryViewPresenter();
    }

    @Provides
    @ActivityScope
    HistoryViewUseCase provideHistoryViewUseCase(HistoryRepository repository,
            ThreadExecutor executor, PostExecutionThread postExecutionThread) {
        return new HistoryViewUseCaseImpl(repository, executor,
                postExecutionThread);
    }
}
