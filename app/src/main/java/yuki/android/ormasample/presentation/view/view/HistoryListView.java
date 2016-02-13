package yuki.android.ormasample.presentation.view.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;
import yuki.android.ormasample.di.HasActivityComponent;
import yuki.android.ormasample.di.component.ActivityComponent;
import yuki.android.ormasample.presentation.controller.HistoryViewController;
import yuki.android.ormasample.presentation.view.adapter.HistoryViewAdapter;

import static yuki.android.ormasample.presentation.presenter.HistoryViewPresenter.*;

public class HistoryListView extends RecyclerView {

    private HistoryViewController historyViewController;

    private CompositeSubscription presenterSubscriber
            = new CompositeSubscription();

    // TODO generics filter presentation event?
    private Action1<Object> presentationEventAction = new Action1<Object>() {
        @Override
        public void call(Object o) {
            if (o instanceof ShowHistoryEvent) {
                ShowHistoryEvent e = (ShowHistoryEvent) o;
                listAdapter.setHistory(e.getHistories());

            } else if (o instanceof RemovedHistoryEvent) {
                RemovedHistoryEvent e = (RemovedHistoryEvent) o;
                int position = HistoryListView.this
                        .findViewHolderForItemId(e.getItemId())
                        .getAdapterPosition();
                listAdapter.removeItem(position);
            }
        }
    };

    private HistoryViewAdapter listAdapter;

    public HistoryListView(Context context) {
        this(context, null, 0);
    }

    public HistoryListView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HistoryListView(Context context, @Nullable AttributeSet attrs,
            int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        // Click リスナーを設定
        listAdapter = new HistoryViewAdapter() {
            // OnClickリスナーの設定をAdapterから排除するための実装
            //    http://qiita.com/takahirom/items/2fd51170ddc5f0e80a36
            @Override
            public HistoryViewHolder onCreateViewHolder(ViewGroup parent,
                    int viewType) {
                final HistoryViewHolder vh = super
                        .onCreateViewHolder(parent, viewType);
                vh.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onHistoryItemClick(vh.getAdapterPosition());
                    }
                });
                return vh;
            }
        };

        // Swipe & Drag リスナーを設定
        new ItemTouchHelper(new HistoryTouchCallback())
                .attachToRecyclerView(this);

        this.setAdapter(listAdapter);
        this.setLayoutManager(new LinearLayoutManager(getContext()));

        presenterSubscriber.add(getComponent()
                .historyViewPresenter()
                .stream()
                .toObservable()
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(presentationEventAction));

        historyViewController = getComponent().historyViewController();
        historyViewController.resume();
        historyViewController.showHistory();
    }

    private ActivityComponent getComponent() {
        Context context = getContext();
        if (!(context instanceof HasActivityComponent)) {
            throw new IllegalArgumentException("HistoryListView context has not ActivityComponent");
        }
        return ((HasActivityComponent) context).getComponent();
    }

    @Override
    protected void onDetachedFromWindow() {
        historyViewController.pause();
        super.onDetachedFromWindow();
    }

    public void onHistoryItemClick(int position) {
        // TODO implements
    }

    public void onHistoryItemSwipe(int position) {
        historyViewController.removeHistoryItem(listAdapter.getItem(position));
    }

    private class HistoryTouchCallback extends ItemTouchHelper.SimpleCallback {

        HistoryTouchCallback() {
            super(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView,
                RecyclerView.ViewHolder viewHolder,
                RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder,
                int direction) {
            onHistoryItemSwipe(viewHolder.getAdapterPosition());
        }
    }
}
