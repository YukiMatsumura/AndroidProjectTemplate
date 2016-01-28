package yuki.android.ormasample.di.component;

import android.app.Activity;

import dagger.Component;
import yuki.android.ormasample.di.module.ActivityModule;
import yuki.android.ormasample.di.scope.PerActivity;

@PerActivity
@Component(dependencies = ApplicationComponent.class,
        modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(Activity activity);
}