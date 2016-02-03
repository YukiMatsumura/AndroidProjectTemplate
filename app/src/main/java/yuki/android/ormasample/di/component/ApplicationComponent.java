package yuki.android.ormasample.di.component;

import javax.inject.Singleton;

import dagger.Component;
import yuki.android.ormasample.App;
import yuki.android.ormasample.data.entity.OrmaDatabase;
import yuki.android.ormasample.di.module.ApplicationModule;
import yuki.android.ormasample.domain.repository.HistoryRepository;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    void inject(App app);

    // Provideメソッドで引数をとる場合に必要
    OrmaDatabase ormaDatabase();
    HistoryRepository historyRepository();
}