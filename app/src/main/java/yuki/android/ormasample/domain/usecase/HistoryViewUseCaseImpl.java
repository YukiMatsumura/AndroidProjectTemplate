package yuki.android.ormasample.domain.usecase;

import java.util.List;

import rx.SingleSubscriber;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import yuki.android.ormasample.crosscut.i18n.DateTime;
import yuki.android.ormasample.data.entity.History;
import yuki.android.ormasample.domain.executor.PostExecutionThread;
import yuki.android.ormasample.domain.executor.ThreadExecutor;
import yuki.android.ormasample.domain.repository.HistoryRepository;

public class HistoryViewUseCaseImpl implements HistoryViewUseCase {

    private HistoryRepository historyRepository;

    private ThreadExecutor executor;

    private PostExecutionThread postThread;

    public HistoryViewUseCaseImpl(HistoryRepository historyRepository,
            ThreadExecutor executor, PostExecutionThread postThread) {
        if (historyRepository == null) {
            throw new NullPointerException("HistoryRepo was injected null. DI is unstable.");
        }
        this.historyRepository = historyRepository;

        if (executor == null) {
            throw new NullPointerException("ThreadExecutor was injected null. DI is unstable.");
        }
        this.executor = executor;

        if (postThread == null) {
            throw new NullPointerException("PostExecutionThread was injected null. DI is unstable.");
        }
        this.postThread = postThread;
    }

    @Override
    public void showLatestHistory(Subscriber<List<History>> subscriber) {
        long twoDaysAgo = DateTime.minus(DateTime.now(), -10, DateTime.FIELD_DAY);
        historyRepository.findBetween(twoDaysAgo, DateTime.now())
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
    public void removeHistory(long rowId, SingleSubscriber<Integer> subscriber) {
        historyRepository.deleteById(rowId)
                .subscribeOn(Schedulers.from(executor))
                .observeOn(postThread.getScheduler())
                .subscribe(subscriber);
    }
}