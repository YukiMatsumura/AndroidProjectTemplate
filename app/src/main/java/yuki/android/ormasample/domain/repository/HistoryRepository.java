package yuki.android.ormasample.domain.repository;

import java.util.List;

import rx.Observable;
import rx.Single;
import yuki.android.ormasample.data.entity.History;

public interface HistoryRepository {

    Observable<History> findBetween(long startDate, long endDate);

    Single<Integer> countAll();

    Observable<Long> insert(List<History> histories);

    Single<Integer> deleteById(long rowId);

    Single<Integer> deleteAll();
}
