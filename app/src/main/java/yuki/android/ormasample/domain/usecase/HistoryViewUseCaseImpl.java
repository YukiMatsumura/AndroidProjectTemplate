package yuki.android.ormasample.domain.usecase;

import java.util.List;

import rx.SingleSubscriber;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import yuki.android.ormasample.crosscut.i18n.DateTimeUtils;
import yuki.android.ormasample.data.entity.History;
import yuki.android.ormasample.domain.executor.PostExecutionThread;
import yuki.android.ormasample.domain.executor.ThreadExecutor;
import yuki.android.ormasample.domain.repository.HistoryRepository;

public class HistoryViewUseCaseImpl implements HistoryViewUseCase {

    private HistoryRepository historyRepository;

    private ThreadExecutor executor;

    private PostExecutionThread postThread;

    private final int searchPeriod = -10;

    @DateTimeUtils.DateTimeField
    private final int searchUnit = DateTimeUtils.FIELD_DAY;

    public HistoryViewUseCaseImpl(HistoryRepository historyRepository,
            ThreadExecutor executor, PostExecutionThread postThread) {
        if (historyRepository == null) {
            throw new NullPointerException(
                    "HistoryRepo was injected null. DI is unstable.");
        }
        this.historyRepository = historyRepository;

        if (executor == null) {
            throw new NullPointerException(
                    "ThreadExecutor was injected null. DI is unstable.");
        }
        this.executor = executor;

        if (postThread == null) {
            throw new NullPointerException(
                    "PostExecutionThread was injected null. DI is unstable.");
        }
        this.postThread = postThread;
    }

    @Override
    public void showLatestHistory(Subscriber<List<History>> subscriber) {
        long twoDaysAgo = DateTimeUtils
                .minus(DateTimeUtils.now(), searchPeriod, searchUnit);
        historyRepository.findBetween(twoDaysAgo, DateTimeUtils.now())
                .toList()
                .subscribeOn(Schedulers.from(executor))
                .observeOn(postThread.getScheduler())
                .subscribe(subscriber);
    }

    @Override
    public void getHistoryCount(SingleSubscriber<Integer> subscriber) {
        historyRepository.countAll()
                .subscribeOn(Schedulers.from(executor))
                .observeOn(postThread.getScheduler())
                .subscribe(subscriber);
    }

    @Override
    public void removeHistory(long rowId,
            SingleSubscriber<Integer> subscriber) {
        historyRepository.deleteById(rowId)
                .subscribeOn(Schedulers.from(executor))
                .observeOn(postThread.getScheduler())
                .subscribe(subscriber);
    }

    @Override
    public void addHistory(History history, Subscriber<Long> subscriber) {
        // TODO: リファクタリングの続き. Domain層にmodelとmapperをかませる
//        List<History> histories = Collections.singletonList(
//                new History()
//                        .setLabel(history.label)
//                        .setActiveDate(DateTimeUtils.now()));
//        historyRepository.insert(histories)
//                .subscribeOn(Schedulers.from(executor))
//                .observeOn(postThread.getScheduler())
//                .subscribe(subscriber);
    }
}
