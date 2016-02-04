package yuki.android.ormasample.di.module;

import dagger.Module;
import dagger.Provides;
import yuki.android.ormasample.App;
import yuki.android.ormasample.data.entity.OrmaDatabase;
import yuki.android.ormasample.data.executor.JobExecutor;
import yuki.android.ormasample.data.repository.HistoryRepositoryImpl;
import yuki.android.ormasample.di.scope.ApplicationScope;
import yuki.android.ormasample.domain.executor.PostExecutionThread;
import yuki.android.ormasample.domain.executor.ThreadExecutor;
import yuki.android.ormasample.domain.repository.HistoryRepository;
import yuki.android.ormasample.presentation.executor.UiThread;

@Module
public class ApplicationModule {

    private final App app;

    public ApplicationModule(App app) {
        this.app = app;
    }

    @Provides
    @ApplicationScope
    public OrmaDatabase provideOrmaDatabase() {
        return OrmaDatabase.builder(app.getApplicationContext()).build();
    }

    @Provides
    @ApplicationScope
    ThreadExecutor provideThreadExecutor() {
        return new JobExecutor();
    }

    @Provides
    @ApplicationScope
    PostExecutionThread providePostExecutionThread() {
        return new UiThread();
    }

    @Provides
    @ApplicationScope
    public HistoryRepository provideHistoryRepository(OrmaDatabase orma) {
        return new HistoryRepositoryImpl(orma);
    }
}
