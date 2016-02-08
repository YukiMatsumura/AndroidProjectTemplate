package yuki.android.ormasample.di.component;

import android.app.Activity;

import dagger.Component;
import yuki.android.ormasample.data.entity.OrmaDatabase;
import yuki.android.ormasample.di.module.ActivityModule;
import yuki.android.ormasample.di.scope.ActivityScope;
import yuki.android.ormasample.domain.usecase.HistoryViewUseCase;
import yuki.android.ormasample.presentation.presenter.HistoryViewPresenter;
import yuki.android.ormasample.presentation.view.activity.HistoryViewActivity;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    // Dagger2による@Injectによるインジェクションを有効にするには具象クラスを受け取る必要がある.
    void inject(HistoryViewActivity activity);

    void inject(HistoryViewPresenter presenter);

    Activity activity();


    HistoryViewUseCase historyViewUseCase();

    OrmaDatabase ormaDatabase();
}