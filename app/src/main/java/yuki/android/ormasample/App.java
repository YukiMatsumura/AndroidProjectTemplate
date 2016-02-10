package yuki.android.ormasample;

import android.app.Application;
import android.support.annotation.NonNull;

import timber.log.Timber;
import yuki.android.ormasample.crosscut.logger.ReleaseTree;
import yuki.android.ormasample.di.component.ApplicationComponent;
import yuki.android.ormasample.di.component.DaggerApplicationComponent;
import yuki.android.ormasample.di.module.ApplicationModule;

public class App extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(
                BuildConfig.DEBUG ? new Timber.DebugTree() : new ReleaseTree());

        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        // TODO: Stetho setup
        // Stetho.initializeWithDefaults(this);
    }

    @NonNull
    public ApplicationComponent getComponent() {
        return component;
    }
}
