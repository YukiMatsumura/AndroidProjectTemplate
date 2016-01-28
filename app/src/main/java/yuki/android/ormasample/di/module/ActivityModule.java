package yuki.android.ormasample.di.module;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import yuki.android.ormasample.di.scope.PerActivity;

@Module
public class ActivityModule {

    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    Activity provideActivity() {
        return this.activity;
    }
}
