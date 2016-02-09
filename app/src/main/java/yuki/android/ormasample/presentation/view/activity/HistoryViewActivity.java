package yuki.android.ormasample.presentation.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yuki.android.ormasample.App;
import yuki.android.ormasample.R;
import yuki.android.ormasample.crosscut.i18n.DateTime;
import yuki.android.ormasample.data.entity.History;
import yuki.android.ormasample.di.HasActivityComponent;
import yuki.android.ormasample.di.component.ActivityComponent;
import yuki.android.ormasample.di.component.DaggerActivityComponent;
import yuki.android.ormasample.di.module.ActivityModule;
import yuki.android.ormasample.presentation.controller.HistoryViewController;
import yuki.android.ormasample.presentation.view.view.HistoryListView;

public class HistoryViewActivity extends AppCompatActivity
        implements HasActivityComponent {

    private ActivityComponent activityComponent;

    @Inject
    HistoryViewController presenter;

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
            throw new NullPointerException("Request ActivityComponent before initialized.");
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
                .applicationComponent(
                        ((App) getApplication()).getComponent())
                .activityModule(new ActivityModule(this))
                .build();
        activityComponent.inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    public void showHistoryCount(Integer count) {
        Snackbar.make(rootView, "History total count is " + count, Snackbar.LENGTH_LONG).show();
    }

    @OnClick(R.id.view_fab)
    public void onFabClick(FloatingActionButton fab) {
        presenter.addHistory(new History().setLabel("Test").setActiveDate(DateTime.now()));
    }
}
