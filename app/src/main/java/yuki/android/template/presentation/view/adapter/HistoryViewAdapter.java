package yuki.android.template.presentation.view.adapter;

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
import yuki.android.template.data.entity.History;

public class HistoryViewAdapter
        extends RecyclerView.Adapter<HistoryViewAdapter.HistoryViewHolder> {

    @NonNull
    private List<History> histories = Collections.emptyList();

    public HistoryViewAdapter() {
        setHistory(null);
        setHasStableIds(true);
    }

    public void setHistory(List<History> histories) {
        this.histories =
                (histories != null)
                        ? histories
                        : Collections.<History>emptyList();
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
        final History history = histories.get(position);
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

    public History getItem(int position) {
        return histories.get(position);
    }

    public void removeItem(int position) {
        histories.remove(position);
        notifyItemRemoved(position);
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
