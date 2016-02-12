package yuki.android.ormasample.presentation.presenter;

import java.util.List;

import yuki.android.ormasample.crosscut.bus.BusStream;
import yuki.android.ormasample.data.entity.History;

public class HistoryViewPresenter {

    private final BusStream stream = new BusStream();

    public BusStream stream() {
        return stream;
    }

    public static class ShowHistoryEvent {

        private final List<History> histories;

        ShowHistoryEvent(List<History> histories) {
            this.histories = histories;
        }

        public List<History> getHistories() {
            return histories;
        }
    }

    public void onShowHistory(List<History> histories) {
        stream.send(new ShowHistoryEvent(histories));
    }

    public static class RemovedHistoryEvent {

        private final long itemId;

        RemovedHistoryEvent(long itemId) {
            this.itemId = itemId;
        }

        public long getItemId() {
            return itemId;
        }
    }

    public void onRemovedHistory(long itemId) {
        stream.send(new RemovedHistoryEvent(itemId));
    }
}
