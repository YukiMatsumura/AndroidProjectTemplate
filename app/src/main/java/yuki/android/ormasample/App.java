package yuki.android.ormasample;

import android.app.Application;
import android.support.annotation.NonNull;

import timber.log.Timber;
import yuki.android.ormasample.data.entity.History;
import yuki.android.ormasample.di.component.ApplicationComponent;
import yuki.android.ormasample.di.component.DaggerApplicationComponent;
import yuki.android.ormasample.di.module.ApplicationModule;

public class App extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        // TODO: debug release branch
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        component.ormaDatabase().insertIntoHistory(new History().setLabel("Test").setActiveDate(System.currentTimeMillis()));
    }

    @NonNull
    public ApplicationComponent getComponent() {
        return component;
    }
}
