package yuki.android.template.crosscut.di.component;

import dagger.Component;
import yuki.android.template.controller.HistoryController;
import yuki.android.template.crosscut.di.module.ActivityModule;
import yuki.android.template.crosscut.di.scope.ActivityScope;
import yuki.android.template.model.bus.ModelBus;
import yuki.android.template.view.activity.HistoryViewActivity;

@ActivityScope
@Component(dependencies = ApplicationComponent.class,
        modules = ActivityModule.class)
public interface ActivityComponent {

    // Dagger2による@Injectによるインジェクションを有効にするには具象クラスを受け取る必要がある.
    void inject(HistoryViewActivity activity);

    HistoryController historyController();

    ModelBus modelBus();
}
