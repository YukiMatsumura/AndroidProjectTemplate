package yuki.android.ormasample.data.repository;

import java.util.List;

import javax.inject.Inject;

import yuki.android.ormasample.data.entity.History;
import yuki.android.ormasample.data.entity.OrmaDatabase;
import yuki.android.ormasample.domain.repository.HistoryRepo;

public class HistoryRepoImpl implements HistoryRepo {

    @Inject
    OrmaDatabase orma;

    @Inject
    public HistoryRepoImpl(OrmaDatabase orma) {
        if (orma == null) {
            throw new NullPointerException("OrmaDatabase was injected null. DI is unstable.");
        }
        this.orma = orma;
    }

    public List<History> findAll() {
        return orma.selectFromHistory()
                .toList();
    }

    public long insertAt(History history) {
        return orma.insertIntoHistory(history);
    }

    public int updateAt(long id, History history) {
        return orma.updateHistory()
                .idEq(id)
                .date(history.date)
                .label(history.label)
                .execute();
    }

    public int deleteAt(long id) {
        return orma.deleteFromHistory()
                .idEq(id)
                .execute();
    }
}
