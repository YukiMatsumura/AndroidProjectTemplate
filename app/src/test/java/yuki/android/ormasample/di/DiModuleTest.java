package yuki.android.ormasample.di;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import android.test.suitebuilder.annotation.MediumTest;

import yuki.android.ormasample.App;
import yuki.android.ormasample.di.component.ActivityComponent;
import yuki.android.ormasample.di.component.ApplicationComponent;
import yuki.android.ormasample.di.component.DaggerActivityComponent;
import yuki.android.ormasample.di.component.DaggerApplicationComponent;
import yuki.android.ormasample.di.module.ActivityModule;
import yuki.android.ormasample.di.module.ApplicationModule;
import yuki.android.ormasample.presentation.view.activity.HistoryViewActivity;
import yuki.android.ormasample.test.CustomRobolectricTestRunner;
import yuki.android.ormasample.test.DefaultRobolectricRule;
import yuki.android.ormasample.test.RobolectricConfig;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@RunWith(CustomRobolectricTestRunner.class)
@Config(sdk = RobolectricConfig.RUNNING_SDK_VERSION)
@MediumTest
public class DiModuleTest {

    @Rule
    public TestRule rule = new DefaultRobolectricRule();

    @Test
    public void ApplicationScopeのテスト() throws Exception {
        ApplicationComponent appComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(
                        (App) RuntimeEnvironment.application))
                .build();

        assertThat("OrmaDatabaseが複数回インスタンス化されてApplicationScopeが適用されていない",
                appComponent.ormaDatabase(), equalTo(appComponent.ormaDatabase()));

        assertThat("HistoryRepositoryが複数回インスタンス化されてApplicationScopeが適用されていない",
                appComponent.historyRepository(), equalTo(appComponent.historyRepository()));

        assertThat("ThreadExecutorが複数回インスタンス化されてApplicationScopeが適用されていない",
                appComponent.threadExecutor(), equalTo(appComponent.threadExecutor()));

        assertThat("PostExecutionThreadが複数回インスタンス化されてApplicationScopeが適用されていない",
                appComponent.postExecutionThread(), equalTo(appComponent.postExecutionThread()));
    }

    @Test
    public void ActivityScopeのテスト() throws Exception {
        ApplicationComponent appComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(
                        (App) RuntimeEnvironment.application))
                .build();
        HistoryViewActivity historyViewActivity = Robolectric.buildActivity(HistoryViewActivity.class).create().get();
        ActivityComponent activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(appComponent)
                .activityModule(new ActivityModule(historyViewActivity))
                .build();

        assertThat("Activityが複数回インスタンス化されてActivityScopeが適用されていない",
                activityComponent.activity(), equalTo(activityComponent.activity()));

        assertThat("HistoryViewUseCaseが複数回インスタンス化されてActivityScopeが適用されていない",
                activityComponent.historyViewUseCase(), equalTo(activityComponent.historyViewUseCase()));

        HistoryViewActivity historyViewActivity2 = Robolectric.buildActivity(HistoryViewActivity.class).create().get();
        ActivityComponent activityComponent2 = DaggerActivityComponent.builder()
                .applicationComponent(appComponent)
                .activityModule(new ActivityModule(historyViewActivity2))
                .build();

        assertThat("ActivityがActivityScopeの範囲を超えて生存している",
                activityComponent2.activity(), not(equalTo(activityComponent.activity())));

        assertThat("HistoryViewUseCaseがActivityScopeの範囲を超えて生存している",
                activityComponent2.historyViewUseCase(), not(equalTo(activityComponent.historyViewUseCase())));
    }
}
