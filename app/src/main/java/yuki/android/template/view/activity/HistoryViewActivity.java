package yuki.android.template.view.activity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yuki.android.template.App;
import yuki.android.template.R;
import yuki.android.template.controller.HistoryController;
import yuki.android.template.crosscut.di.HasActivityComponent;
import yuki.android.template.crosscut.di.component.ActivityComponent;
import yuki.android.template.crosscut.di.component.DaggerActivityComponent;
import yuki.android.template.crosscut.di.module.ActivityModule;
import yuki.android.template.crosscut.i18n.DateTimeUtils;
import yuki.android.template.model.HistoryEntity;
import yuki.android.template.model.bus.ModelBus;
import yuki.android.template.model.bus.event.HistoryEvent;
import yuki.android.template.view.view.HistoryListView;

public class HistoryViewActivity extends AppCompatActivity
        implements HasActivityComponent {

    private ActivityComponent activityComponent;

    private HistoryController controller;

    private ModelBus bus;

    @Bind(R.id.view_activity_root)
    View rootView;

    @Bind(R.id.view_toolbar)
    Toolbar toolbar;

    @Bind(R.id.view_fab)
    FloatingActionButton fab;

    @Bind(R.id.view_history_list)
    HistoryListView historyListView;

    @NonNull
    public ActivityComponent getComponent() {
        if (activityComponent == null) {
            throw new NullPointerException(
                    "Request ActivityComponent before initialized.");
        }
        return activityComponent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(((App) getApplication()).getComponent())
                .activityModule(new ActivityModule(this))
                .build();
        controller = activityComponent.historyController();
        bus = activityComponent.modelBus();
    }

    @Override
    protected void onResume() {
        super.onResume();
        controller.resume();
        bus.register(this);
    }

    @Override
    protected void onPause() {
        bus.unregister(this);
        controller.pause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        controller.destroy();
        super.onDestroy();
    }

    @Keep
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(HistoryEvent.Count event) {
        Snackbar.make(rootView, "History total count is " + event.count,
                Snackbar.LENGTH_LONG).show();
    }

    @OnClick(R.id.view_fab)
    public void onFabClick(FloatingActionButton fab) {
        controller.addHistory(new HistoryEntity(-1, DateTimeUtils.now(), "Test"));
    }
}
