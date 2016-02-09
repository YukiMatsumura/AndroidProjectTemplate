package yuki.android.ormasample.presentation.presenter;

import java.util.List;

import yuki.android.ormasample.crosscut.bus.BusStream;
import yuki.android.ormasample.data.entity.History;

public class HistoryViewPresenter {

    private final BusStream STREAM = new BusStream();

    public BusStream stream() {
        return STREAM;
    }

    public static class ShowHistoryEvent {

        public final List<History> histories;

        ShowHistoryEvent(List<History> histories) {
            this.histories = histories;
        }
    }

    public void onShowHistory(List<History> histories) {
        STREAM.send(new ShowHistoryEvent(histories));
    }

    public static class RemovedHistoryEvent {

        public final long itemId;

        RemovedHistoryEvent(long itemId) {
            this.itemId = itemId;
        }
    }

    public void onRemovedHistory(long itemId) {
        STREAM.send(new RemovedHistoryEvent(itemId));
    }
}
