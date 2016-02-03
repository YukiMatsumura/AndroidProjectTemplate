package yuki.android.ormasample.presentation.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import yuki.android.ormasample.data.entity.History;
import yuki.android.ormasample.presentation.view.HistoryItemView;

public class HistoryViewAdapter extends RecyclerView.Adapter<HistoryItemView> {

    @NonNull
    private List<History> histories = Collections.emptyList();

    public HistoryViewAdapter() {
        setHistory(null);
    }

    public void setHistory(List<History> histories) {
        this.histories =
                (histories != null) ? histories : Collections.<History>emptyList();
        this.notifyDataSetChanged();
    }

    @Override
    public HistoryItemView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (parent == null) {
            throw new NullPointerException("Parent view can not be null");
        }
        return new HistoryItemView(parent);
    }

    @Override
    public void onBindViewHolder(HistoryItemView itemView, int position) {
        History history = histories.get(position);
        itemView.attach(history);
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }
}
