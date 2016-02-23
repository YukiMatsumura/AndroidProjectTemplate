package yuki.android.template.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import yuki.android.template.R;
import yuki.android.template.model.HistoryEntity;

public class HistoryViewAdapter
        extends RecyclerView.Adapter<HistoryViewAdapter.HistoryViewHolder> {

    @NonNull
    private List<HistoryEntity> histories = Collections.emptyList();

    public HistoryViewAdapter() {
        setHistory(null);
        setHasStableIds(true);
    }

    public void setHistory(List<HistoryEntity> histories) {
        this.histories = (histories != null)
                ? histories
                : Collections.<HistoryEntity>emptyList();
        this.notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent,
            int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_history, parent, false);
        return new HistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        final HistoryEntity history = histories.get(position);
        holder.textView.setText(history.label);
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }

    @Override
    public long getItemId(int position) {
        return histories.get(position).id;
    }

    public HistoryEntity getItem(int position) {
        return histories.get(position);
    }

    public void removeItem(int position) {
        histories.remove(position);
        notifyItemRemoved(position);
    }

    public void addItem(int position) {
        notifyItemInserted(position);
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.view_history_list_item)
        TextView textView;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
