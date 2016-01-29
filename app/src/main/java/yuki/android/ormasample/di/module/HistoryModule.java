package yuki.android.ormasample.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import yuki.android.ormasample.domain.repository.HistoryRepo;

@Module(includes = RepositoryModule.class)
public class HistoryModule {

    @Provides
    @Singleton
    public HistoryRepo provideHistoryRepo(HistoryRepo repo) {
        return repo;
    }
}
