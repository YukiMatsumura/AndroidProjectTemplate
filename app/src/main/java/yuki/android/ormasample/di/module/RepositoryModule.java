package yuki.android.ormasample.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import yuki.android.ormasample.App;
import yuki.android.ormasample.data.entity.OrmaDatabase;

@Module
public class RepositoryModule {

    @Singleton
    @Provides
    public OrmaDatabase provideOrmaDatabase(App app) {
        return OrmaDatabase.builder(app.getApplicationContext()).build();
    }
}
