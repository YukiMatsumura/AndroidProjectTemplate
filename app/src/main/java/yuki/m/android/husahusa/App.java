package yuki.m.android.husahusa;

import android.app.Application;
import android.support.annotation.NonNull;

import timber.log.Timber;
import yuki.m.android.husahusa.crosscut.di.component.ApplicationComponent;
import yuki.m.android.husahusa.crosscut.di.component.DaggerApplicationComponent;
import yuki.m.android.husahusa.crosscut.di.module.ApplicationModule;
import yuki.m.android.husahusa.crosscut.logger.ReleaseTree;

public class App extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(BuildConfig.DEBUG
                ? new Timber.DebugTree()
                : new ReleaseTree());

        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();
//
//        // TODO: Stetho setup
//        // Stetho.initializeWithDefaults(this);
    }

    @NonNull
    public ApplicationComponent getComponent() {
        return component;
    }
}
