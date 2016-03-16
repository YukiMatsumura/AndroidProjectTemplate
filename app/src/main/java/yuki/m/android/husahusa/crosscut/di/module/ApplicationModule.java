package yuki.m.android.husahusa.crosscut.di.module;

import dagger.Module;
import dagger.Provides;
import yuki.m.android.husahusa.App;
import yuki.m.android.husahusa.controller.executor.ExecutionScheduler;
import yuki.m.android.husahusa.crosscut.di.scope.ApplicationScope;
import yuki.m.android.husahusa.model.HistoryModel;
import yuki.m.android.husahusa.model.OrmaDatabase;
import yuki.m.android.husahusa.model.bus.ModelBus;

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
