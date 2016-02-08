package yuki.android.ormasample.presentation.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yuki.android.ormasample.App;
import yuki.android.ormasample.R;
import yuki.android.ormasample.data.entity.History;
import yuki.android.ormasample.di.component.ActivityComponent;
import yuki.android.ormasample.di.component.DaggerActivityComponent;
import yuki.android.ormasample.di.module.ActivityModule;
import yuki.android.ormasample.presentation.presenter.HistoryViewPresenter;
import yuki.android.ormasample.presentation.view.adapter.HistoryViewAdapter;

public class HistoryViewActivity extends AppCompatActivity
        implements HistoryViewAdapter.OnHistoryItemCallback {

    private ActivityComponent activityComponent;

    @Inject
    HistoryViewPresenter presenter;

    HistoryViewAdapter listAdapter;

    @Bind(R.id.view_activity_root)
    View rootView;

    @Bind(R.id.view_toolbar)
    Toolbar toolbar;

    @Bind(R.id.view_fab)
    FloatingActionButton fab;

    @Bind(R.id.view_history_list)
    RecyclerView recyclerView;

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

        listAdapter = new HistoryViewAdapter();
        listAdapter.setOnHistoryItemCallback(this);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        presenter.create(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.resume();
        presenter.showHistory();
    }

    @Override
    protected void onPause() {
        presenter.pause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        presenter.destroy();
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    public void showHistoryList(List<History> histories) {
        listAdapter.setHistory(histories);
    }

    public void showHistoryCount(Integer count) {
        Snackbar.make(rootView, "History total count is " + count, Snackbar.LENGTH_LONG).show();
    }

    public void onRemovedHistory(long itemId) {
        listAdapter.removeItem(
                recyclerView.findViewHolderForItemId(itemId).getAdapterPosition());
    }

    @Override
    public void onHistoryItemSwipe(History history) {
        presenter.removeHistoryItem(history.id);
    }

    @Override
    public void onHistoryItemClick(History history) {

    }

    @OnClick(R.id.view_fab)
    public void onFabClick(FloatingActionButton fab) {
        presenter.addHistory();
    }
}
