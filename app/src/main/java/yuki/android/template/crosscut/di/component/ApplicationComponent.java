package yuki.android.template.crosscut.di.component;

import dagger.Component;
import yuki.android.template.controller.executor.ExecutionScheduler;
import yuki.android.template.crosscut.di.module.ApplicationModule;
import yuki.android.template.crosscut.di.scope.ApplicationScope;
import yuki.android.template.model.HistoryModel;
import yuki.android.template.model.OrmaDatabase;
import yuki.android.template.model.bus.ModelBus;

@ApplicationScope
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    OrmaDatabase ormaDatabase();

    ExecutionScheduler executionScheduler();

    ModelBus modelBus();

    HistoryModel historyModel();
}
