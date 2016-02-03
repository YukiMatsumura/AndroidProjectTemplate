package yuki.android.ormasample.presentation.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;
import yuki.android.ormasample.R;
import yuki.android.ormasample.data.entity.History;

public class HistoryItemView extends RecyclerView.ViewHolder {

    @Bind(R.id.view_history_list_item)
    TextView textView;

    public HistoryItemView(@NonNull ViewGroup parent) {
        // ViewHolderコンストラクタによって itemView にリスト項目Viewが保持される.
        super(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_history, parent, false));
        ButterKnife.bind(this, this.itemView);
    }

    public void attach(History history) {
        if (history == null) {
            Timber.w("History item view was attached null");
            return;
        }
        textView.setText(history.label);
    }
}
