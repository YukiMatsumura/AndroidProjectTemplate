package yuki.android.template.view.view;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import android.content.Context;
import android.support.annotation.Keep;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import yuki.android.template.controller.HistoryController;
import yuki.android.template.crosscut.di.HasActivityComponent;
import yuki.android.template.crosscut.di.component.ActivityComponent;
import yuki.android.template.model.bus.ModelBus;
import yuki.android.template.model.bus.event.HistoryEvent;
import yuki.android.template.view.adapter.HistoryViewAdapter;

public class HistoryListView extends RecyclerView {

    private HistoryController controller;

    private HistoryViewAdapter listAdapter;

    private ModelBus modelBus;

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
                vh.itemView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onHistoryItemClick(vh.getAdapterPosition());
                    }
                });
                return vh;
            }
        };

        // Swipe & Drag リスナーを設定
        new ItemTouchHelper(new HistoryTouchCallback()).attachToRecyclerView(this);

        this.setAdapter(listAdapter);
        this.setLayoutManager(new LinearLayoutManager(getContext()));

        controller = getComponent().historyController();
        modelBus = getComponent().modelBus();
        modelBus.register(this);

        controller.showLatestHistory();
        controller.getHistoryCount();
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
        modelBus.unregister(this);
        super.onDetachedFromWindow();
    }

    public void onHistoryItemClick(int position) {
        // TODO implements
    }

    public void onHistoryItemSwipe(int position) {
        controller.removeHistory(listAdapter.getItem(position).id);
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

    @Keep
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(HistoryEvent.Added event) {
        //listAdapter.addItem(findViewHolderForItemId(event.rowId).getAdapterPosition());
        controller.showLatestHistory();
    }

    @Keep
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(HistoryEvent.ShowLatest event) {
        listAdapter.setHistory(event.histories);
    }

    @Keep
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(HistoryEvent.Removed event) {
        listAdapter.removeItem(findViewHolderForItemId(event.rowId).getAdapterPosition());
    }
}
