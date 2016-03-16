package yuki.m.android.husahusa.model.bus.event;

import java.util.List;

import yuki.m.android.husahusa.model.HistoryEntity;

public abstract class HistoryEvent {

    public static class ShowLatest {

        public final List<HistoryEntity> histories;

        public ShowLatest(List<HistoryEntity> histories) {
            this.histories = histories;
        }
    }

    public static class ShowLatestError {

        public final Throwable error;

        public ShowLatestError(Throwable error) {
            this.error = error;
        }
    }

    public static class Removed {

        public final long rowId;

        public Removed(long rowId) {
            this.rowId = rowId;
        }
    }

    public static class RemovedError {

        public final Throwable error;

        public final long id;

        public RemovedError(Throwable error, long removedId) {
            this.error = error;
            this.id = removedId;
        }
    }

    public static class Added {

        public final long rowId;

        public Added(long rowId) {
            this.rowId = rowId;
        }
    }

    public static class AddedError {

        public final Throwable error;

        public final long rowId;

        public AddedError(Throwable error, long rowId) {
            this.error = error;
            this.rowId = rowId;
        }
    }

    public static class Count {

        public final int count;

        public Count(int count) {
            this.count = count;
        }
    }

    public static class CountError {

        public final Throwable error;

        public CountError(Throwable error) {
            this.error = error;
        }
    }
}
