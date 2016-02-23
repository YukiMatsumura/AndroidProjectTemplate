package yuki.android.template.crosscut.di.module;

import dagger.Module;
import dagger.Provides;
import yuki.android.template.App;
import yuki.android.template.controller.executor.ExecutionScheduler;
import yuki.android.template.crosscut.di.scope.ApplicationScope;
import yuki.android.template.model.HistoryModel;
import yuki.android.template.model.OrmaDatabase;
import yuki.android.template.model.bus.ModelBus;

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
    ExecutionScheduler provideExecutionScheduler() {
        return new ExecutionScheduler();
    }

    @Provides
    @ApplicationScope
    public ModelBus provideModelBus() {
        return new ModelBus();
    }

    @Provides
    @ApplicationScope
    public HistoryModel provideHistoryModel(OrmaDatabase orma,
            ExecutionScheduler executionScheduler, ModelBus bus) {
        return new HistoryModel(orma, executionScheduler, bus);
    }

}
