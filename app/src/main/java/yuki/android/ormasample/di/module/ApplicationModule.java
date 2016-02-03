package yuki.android.ormasample.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import yuki.android.ormasample.App;
import yuki.android.ormasample.data.entity.OrmaDatabase;
import yuki.android.ormasample.data.repository.HistoryRepositoryImpl;
import yuki.android.ormasample.domain.repository.HistoryRepository;

@Module
public class ApplicationModule {

    private final App app;

    public ApplicationModule(App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    public OrmaDatabase provideOrmaDatabase() {
        return OrmaDatabase.builder(app.getApplicationContext()).build();
    }

    @Provides
    @Singleton
    public HistoryRepository provideHistoryRepository(OrmaDatabase orma) {
        return new HistoryRepositoryImpl(orma);
    }
}
