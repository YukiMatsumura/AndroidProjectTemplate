package yuki.android.template.di.component;

import dagger.Component;
import yuki.android.template.repository.entity.OrmaDatabase;
import yuki.android.template.di.module.ApplicationModule;
import yuki.android.template.di.scope.ApplicationScope;
import yuki.android.template.domain.executor.PostExecutionThread;
import yuki.android.template.domain.executor.ThreadExecutor;
import yuki.android.template.domain.repository.HistoryRepository;

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
