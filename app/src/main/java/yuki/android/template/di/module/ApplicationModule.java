package yuki.android.template.di.module;

import dagger.Module;
import dagger.Provides;
import yuki.android.template.App;
import yuki.android.template.data.entity.OrmaDatabase;
import yuki.android.template.data.executor.JobExecutor;
import yuki.android.template.data.repository.HistoryRepositoryImpl;
import yuki.android.template.di.scope.ApplicationScope;
import yuki.android.template.domain.executor.PostExecutionThread;
import yuki.android.template.domain.executor.ThreadExecutor;
import yuki.android.template.domain.repository.HistoryRepository;
import yuki.android.template.presentation.executor.UiThread;

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
