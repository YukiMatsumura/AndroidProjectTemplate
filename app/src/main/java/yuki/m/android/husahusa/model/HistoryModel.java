package yuki.m.android.husahusa.model;

import java.util.List;

import rx.Single;
import rx.SingleSubscriber;
import rx.functions.Action1;
import yuki.m.android.husahusa.controller.executor.ExecutionScheduler;
import yuki.m.android.husahusa.model.bus.ModelBus;

import static yuki.m.android.husahusa.crosscut.i18n.DateTimeUtils.*;

public class HistoryModel {

    private OrmaDatabase orma;

    private ExecutionScheduler executionScheduler;

    private ModelBus bus;

    public HistoryModel(OrmaDatabase orma,
            ExecutionScheduler executionScheduler, ModelBus bus) {
        if (orma == null) {
            throw new NullPointerException(
                    "OrmaDatabase was injected null. DI is unstable.");
        }
        this.orma = orma;

        if (executionScheduler == null) {
            throw new NullPointerException(
                    "ExecutionScheduler was injected null. DI is unstable.");
        }
        this.executionScheduler = executionScheduler;

        if (bus == null) {
            throw new NullPointerException(
                    "ModelBus was injected null. DI is unstable.");
        }
        this.bus = bus;
    }

    public Single<HistoryEntity> findById(long rowId) {
        return orma.selectFromHistoryEntity()
                .idEq(rowId)
                .executeAsObservable()
                .toSingle();
    }

    /**
     * 最近(2日前)からの履歴を取得する
     */
    public void findLatest() {
        Single.create(new Single.OnSubscribe<List<HistoryEntity>>() {
            @Override
            public void call(SingleSubscriber<? super List<HistoryEntity>> singleSubscriber) {
                singleSubscriber.onSuccess(
                        orma.selectFromHistoryEntity()
                                .activeDateGe(before(2, FIELD_DAY))
                                .activeDateLe(now())
                                .toList());
            }
        })
                .observeOn(executionScheduler.getScheduler())
                .subscribeOn(executionScheduler.getScheduler())
                .subscribe(
                        new Action1<List<HistoryEntity>>() {
                            @Override
                            public void call(List<HistoryEntity> histories) {
                                bus.postShowLatestHistoryEvent(histories);
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                bus.postShowLatestHistoryErrorEvent(throwable);
                            }
                        });
    }

    public void countAll() {
        Single.<Integer>create(new Single.OnSubscribe<Integer>() {
            @Override
            public void call(SingleSubscriber<? super Integer> singleSubscriber) {
                singleSubscriber.onSuccess(
                        orma.selectFromHistoryEntity().count());
            }
        })
                .observeOn(executionScheduler.getScheduler())
                .subscribeOn(executionScheduler.getScheduler())
                .subscribe(
                        new Action1<Integer>() {
                            @Override
                            public void call(Integer count) {
                                bus.postCountHistoryEvent(count);
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                bus.postCountHistoryErrorEvent(throwable);
                            }
                        });
    }

    public void insert(final HistoryEntity history) {
        Single.create(new Single.OnSubscribe<Long>() {
            @Override
            public void call(SingleSubscriber<? super Long> singleSubscriber) {
                singleSubscriber.onSuccess(
                        orma.insertIntoHistoryEntity(history));
            }
        })
                .observeOn(executionScheduler.getScheduler())
                .subscribeOn(executionScheduler.getScheduler())
                .subscribe(
                        new Action1<Long>() {
                            @Override
                            public void call(Long rowId) {
                                bus.postHistoryAddedEvent(rowId);
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                bus.postHistoryAddedErrorEvent(throwable, history.id);
                            }
                        });
    }

    public void deleteById(final long rowId) {
        Single.create(new Single.OnSubscribe<Integer>() {
            @Override
            public void call(SingleSubscriber<? super Integer> singleSubscriber) {
                int count = orma.deleteFromHistoryEntity().idEq(rowId).execute();
                if (0 < count) {
                    singleSubscriber.onSuccess(count);
                } else {
                    singleSubscriber.onError(null /* TODO: error object */);
                }
            }
        })
                .observeOn(executionScheduler.getScheduler())
                .subscribeOn(executionScheduler.getScheduler())
                .subscribe(
                        new Action1<Integer>() {
                            @Override
                            public void call(Integer integer) {
                                bus.postHistoryRemovedEvent(rowId);
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                bus.postHistoryRemovedErrorEvent(throwable, rowId);
                            }
                        });
    }

    public Single<Integer> deleteAll() {
        return orma.deleteFromHistoryEntity()
                .executeAsObservable();
    }
}
