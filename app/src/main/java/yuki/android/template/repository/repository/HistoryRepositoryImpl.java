package yuki.android.template.repository.repository;

import java.util.List;

import hugo.weaving.DebugLog;
import rx.Observable;
import rx.Single;
import rx.functions.Action0;
import rx.functions.Action1;
import yuki.android.template.crosscut.bus.BroadcastBus;
import yuki.android.template.repository.entity.History;
import yuki.android.template.repository.entity.OrmaDatabase;
import yuki.android.template.domain.repository.HistoryRepository;

public class HistoryRepositoryImpl implements HistoryRepository {

    private OrmaDatabase orma;

    public HistoryRepositoryImpl(OrmaDatabase orma) {
        if (orma == null) {
            throw new NullPointerException(
                    "OrmaDatabase was injected null. DI is unstable.");
        }
        this.orma = orma;
    }

    public Single<History> findById(long rowId) {
        return orma.selectFromHistory()
                .idEq(rowId)
                .executeAsObservable()
                .toSingle();
    }

    @Override
    public Observable<History> findBetween(long startDate, long endDate) {
        return orma.selectFromHistory()
                .activeDateGe(startDate)
                .activeDateLe(endDate)
                .executeAsObservable();
    }

    @Override
    public Single<Integer> countAll() {
        return orma.selectFromHistory()
                .countAsObservable().toSingle();
    }

    @Override
    public Observable<Long> insert(List<History> histories) {
        return orma.prepareInsertIntoHistory()
                .executeAllAsObservable(histories)
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        // Historyレコードの変更をブロードキャスト
                        BroadcastBus.stream()
                                .send(new RepositoryChangedEvent.History());
                    }
                });
    }

    @DebugLog
    @Override
    public Single<Integer> deleteById(long rowId) {
        return orma.deleteFromHistory()
                .idEq(rowId)
                .executeAsObservable()
                .doOnSuccess(new Action1<Integer>() {
                    @Override
                    public void call(Integer deletedRows) {
                        if (0 < deletedRows) {
                            // 削除された場合はHistoryレコードの変更をブロードキャスト
                            BroadcastBus.stream()
                                    .send(new RepositoryChangedEvent.History());
                        }
                        // TODO: 削除0件だった場合
                    }
                });
    }

    @Override
    public Single<Integer> deleteAll() {
        return orma.deleteFromHistory()
                .executeAsObservable()
                .doOnSuccess(new Action1<Integer>() {
                    @Override
                    public void call(Integer deletedRows) {
                        if (0 < deletedRows) {
                            // 削除された行がある場合はHistoryレコードの変更をブロードキャスト
                            BroadcastBus.stream()
                                    .send(new RepositoryChangedEvent.History());
                        }
                        // TODO: 削除0件だった場合
                    }
                });
    }
}
