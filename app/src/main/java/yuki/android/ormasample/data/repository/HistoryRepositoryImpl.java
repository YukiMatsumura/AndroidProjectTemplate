package yuki.android.ormasample.data.repository;

import rx.Observable;
import yuki.android.ormasample.data.entity.History;
import yuki.android.ormasample.data.entity.OrmaDatabase;
import yuki.android.ormasample.domain.repository.HistoryRepository;

public class HistoryRepositoryImpl implements HistoryRepository {

    private OrmaDatabase orma;

    public HistoryRepositoryImpl(OrmaDatabase orma) {
        if (orma == null) {
            throw new NullPointerException("OrmaDatabase was injected null. DI is unstable.");
        }
        this.orma = orma;
    }

    @Override
    public Observable<History> findLatestHistory(long startDate, long endDate) {
        return orma.selectFromHistory()
                .activeDateGe(startDate)
                .activeDateLe(endDate)
                .executeAsObservable();
    }

    @Override
    public Observable<Integer> countHistory() {
        return orma.selectFromHistory()
                .countAsObservable();
    }
}
