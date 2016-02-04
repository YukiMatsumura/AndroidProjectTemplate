package yuki.android.ormasample.domain.repository;

import rx.Observable;
import yuki.android.ormasample.data.entity.History;

public interface HistoryRepository {

    Observable<History> findLatestHistory(long startDate, long endDate);

    Observable<Integer> countHistory();
}
