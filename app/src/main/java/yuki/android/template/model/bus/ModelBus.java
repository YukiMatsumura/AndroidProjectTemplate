package yuki.android.template.model.bus;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import yuki.android.template.model.HistoryEntity;
import yuki.android.template.model.bus.event.HistoryEvent;

public class ModelBus {

    public void register(Object obj) {
        EventBus.getDefault().register(obj);
    }

    public void unregister(Object obj) {
        EventBus.getDefault().unregister(obj);
    }

    public void postShowLatestHistoryEvent(List<HistoryEntity> histories) {
        EventBus.getDefault().post(new HistoryEvent.ShowLatest(histories));
    }

    public void postShowLatestHistoryErrorEvent(Throwable error) {
        EventBus.getDefault().post(new HistoryEvent.ShowLatestError(error));
    }

    public void postHistoryRemovedEvent(long rowId) {
        EventBus.getDefault().post(new HistoryEvent.Removed(rowId));
    }

    public void postHistoryRemovedErrorEvent(Throwable error, long rowId) {
        EventBus.getDefault().post(new HistoryEvent.RemovedError(error, rowId));
    }

    public void postHistoryAddedEvent(long rowId) {
        EventBus.getDefault().post(new HistoryEvent.Added(rowId));
    }

    public void postHistoryAddedErrorEvent(Throwable error, long rowId) {
        EventBus.getDefault().post(new HistoryEvent.AddedError(error, rowId));
    }

    public void postCountHistoryEvent(int count) {
        EventBus.getDefault().post(new HistoryEvent.Count(count));
    }

    public void postCountHistoryErrorEvent(Throwable error) {
        EventBus.getDefault().post(new HistoryEvent.CountError(error));
    }
}
