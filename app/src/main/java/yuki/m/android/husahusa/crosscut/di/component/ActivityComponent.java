package yuki.m.android.husahusa.crosscut.di.component;

import dagger.Component;
import yuki.m.android.husahusa.controller.HistoryController;
import yuki.m.android.husahusa.crosscut.di.module.ActivityModule;
import yuki.m.android.husahusa.crosscut.di.scope.ActivityScope;
import yuki.m.android.husahusa.model.bus.ModelBus;
import yuki.m.android.husahusa.view.activity.HistoryViewActivity;

@ActivityScope
@Component(dependencies = ApplicationComponent.class,
        modules = ActivityModule.class)
public interface ActivityComponent {

    // Dagger2による@Injectによるインジェクションを有効にするには具象クラスを受け取る必要がある.
    void inject(HistoryViewActivity activity);

    HistoryController historyController();

    ModelBus modelBus();
}
