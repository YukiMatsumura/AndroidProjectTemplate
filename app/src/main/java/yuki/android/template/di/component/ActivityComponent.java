package yuki.android.template.di.component;

import android.app.Activity;

import dagger.Component;
import yuki.android.template.data.entity.OrmaDatabase;
import yuki.android.template.di.module.ActivityModule;
import yuki.android.template.di.scope.ActivityScope;
import yuki.android.template.domain.usecase.HistoryViewUseCase;
import yuki.android.template.presentation.controller.HistoryViewController;
import yuki.android.template.presentation.presenter.HistoryViewPresenter;
import yuki.android.template.presentation.view.activity.HistoryViewActivity;

@ActivityScope
@Component(dependencies = ApplicationComponent.class,
        modules = ActivityModule.class)
public interface ActivityComponent {

    // Dagger2による@Injectによるインジェクションを有効にするには具象クラスを受け取る必要がある.
    void inject(HistoryViewActivity activity);

    void inject(HistoryViewController presenter);

    Activity activity();

    HistoryViewPresenter historyViewPresenter();

    HistoryViewController historyViewController();

    HistoryViewUseCase historyViewUseCase();

    OrmaDatabase ormaDatabase();
}
