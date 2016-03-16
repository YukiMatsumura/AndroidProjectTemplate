package yuki.android.template.crosscut.di.module;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import yuki.android.template.controller.HistoryController;
import yuki.android.template.crosscut.di.scope.ActivityScope;
import yuki.android.template.model.HistoryModel;

@Module
public class ActivityModule {

    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    Activity provideActivity() {
        return this.activity;
    }

    @Provides
    @ActivityScope
    HistoryController provideHistoryController(HistoryModel model) {
        return new HistoryController(model);
    }
}
