package yuki.android.ormasample.di.component;

import dagger.Component;
import yuki.android.ormasample.di.module.ActivityModule;
import yuki.android.ormasample.di.scope.PerActivity;
import yuki.android.ormasample.presentation.presenter.HistoryViewPresenter;
import yuki.android.ormasample.presentation.view.HistoryViewActivity;

@PerActivity
@Component(dependencies = ApplicationComponent.class,
        modules = ActivityModule.class)
public interface ActivityComponent {

    // Dagger2による@Injectによるインジェクションを有効にするには具象クラスを受け取る必要がある.
    void inject(HistoryViewActivity activity);
    void inject(HistoryViewPresenter presenter);
}