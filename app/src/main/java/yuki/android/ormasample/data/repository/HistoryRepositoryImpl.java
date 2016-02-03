package yuki.android.ormasample.data.repository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import yuki.android.ormasample.data.entity.History;
import yuki.android.ormasample.data.entity.OrmaDatabase;
import yuki.android.ormasample.domain.repository.HistoryRepository;

@Singleton
public class HistoryRepositoryImpl implements HistoryRepository {

    private OrmaDatabase orma;

    @Inject
    public HistoryRepositoryImpl(OrmaDatabase orma) {
        if (orma == null) {
            throw new NullPointerException("OrmaDatabase was injected null. DI is unstable.");
        }
        this.orma = orma;
    }

    public List<History> findLatestHistory(long startDate, long endDate) {
        return orma.selectFromHistory()
                .activeDateLe(startDate)
                .activeDateGe(endDate)
                .toList();
    }
}
