package yuki.android.ormasample.di.component;

import dagger.Component;
import yuki.android.ormasample.data.entity.OrmaDatabase;
import yuki.android.ormasample.di.module.ApplicationModule;
import yuki.android.ormasample.di.scope.ApplicationScope;
import yuki.android.ormasample.domain.executor.PostExecutionThread;
import yuki.android.ormasample.domain.executor.ThreadExecutor;
import yuki.android.ormasample.domain.repository.HistoryRepository;

@ApplicationScope
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    //Exposed to sub-graphs.
    // Provideメソッドで引数をとる場合に必要
    OrmaDatabase ormaDatabase();

    ThreadExecutor threadExecutor();

    PostExecutionThread postExecutionThread();

    HistoryRepository historyRepository();
}
