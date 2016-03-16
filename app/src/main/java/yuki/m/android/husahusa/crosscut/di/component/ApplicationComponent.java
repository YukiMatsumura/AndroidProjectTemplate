package yuki.m.android.husahusa.crosscut.di.component;

import dagger.Component;
import yuki.m.android.husahusa.controller.executor.ExecutionScheduler;
import yuki.m.android.husahusa.crosscut.di.module.ApplicationModule;
import yuki.m.android.husahusa.crosscut.di.scope.ApplicationScope;
import yuki.m.android.husahusa.model.HistoryModel;
import yuki.m.android.husahusa.model.OrmaDatabase;
import yuki.m.android.husahusa.model.bus.ModelBus;

@ApplicationScope
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    OrmaDatabase ormaDatabase();

    ExecutionScheduler executionScheduler();

    ModelBus modelBus();

    HistoryModel historyModel();
}
