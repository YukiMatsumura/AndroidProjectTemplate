package yuki.android.template.di.module;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import yuki.android.template.di.scope.ActivityScope;
import yuki.android.template.domain.executor.PostExecutionThread;
import yuki.android.template.domain.executor.ThreadExecutor;
import yuki.android.template.domain.repository.HistoryRepository;
import yuki.android.template.domain.usecase.HistoryViewUseCase;
import yuki.android.template.domain.usecase.HistoryViewUseCaseImpl;
import yuki.android.template.presentation.controller.HistoryViewController;
import yuki.android.template.presentation.presenter.HistoryViewPresenter;

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
