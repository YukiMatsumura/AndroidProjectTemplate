package yuki.android.ormasample.presentation.view.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import yuki.android.ormasample.R;
import yuki.android.ormasample.data.entity.History;

public class HistoryViewAdapter extends RecyclerView.Adapter<HistoryViewAdapter.HistoryViewHolder> {

    @NonNull
    private List<History> histories = Collections.emptyList();

    private ItemTouchHelper itemTouchHelper;

    @Nullable
    private OnHistoryItemCallback callback;

    public interface OnHistoryItemCallback {

        void onHistoryItemSwipe(History history);

        void onHistoryItemClick(History history);
    }

    public HistoryViewAdapter() {
        setHistory(null);
        setHasStableIds(true);
    }

    public void setHistory(List<History> histories) {
        this.histories =
                (histories != null) ? histories : Collections.<History>emptyList();
        this.notifyDataSetChanged();
    }

    public void setOnHistoryItemCallback(OnHistoryItemCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        itemTouchHelper = new ItemTouchHelper(new HistoryTouchCallback(this));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_history, parent, false);
        return new HistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        final History history = histories.get(position);
        holder.textView.setText(history.label);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClicked(history);
            }
        });
    }

    private void onItemClicked(History history) {
        if (callback != null) {
            callback.onHistoryItemClick(history);
        }
    }

    private void onItemSwiped(History history) {
        if (callback != null) {
            callback.onHistoryItemSwipe(history);
        }
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }

    @Override
    public long getItemId(int position) {
        return histories.get(position).id;
    }

    public void removeItem(int position) {
        histories.remove(position);
        notifyItemRemoved(position);
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.view_history_list_item)
        TextView textView;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class HistoryTouchCallback extends ItemTouchHelper.SimpleCallback {

        @NonNull
        private final HistoryViewAdapter adapter;

        public HistoryTouchCallback(@NonNull HistoryViewAdapter adapter) {
            super(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT);

            // noinspection ConstantConditions
            if (adapter == null) {
                throw new NullPointerException("HistoryViewAdapter can not be null");
            }
            this.adapter = adapter;
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            adapter.onItemSwiped(adapter.histories.get(viewHolder.getAdapterPosition()));
        }
    }
}
