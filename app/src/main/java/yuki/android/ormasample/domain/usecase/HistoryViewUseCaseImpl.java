package yuki.android.ormasample.domain.usecase;

import java.util.List;

import rx.Subscriber;
import rx.schedulers.Schedulers;
import yuki.android.ormasample.crosscut.i18n.DateTime;
import yuki.android.ormasample.data.entity.History;
import yuki.android.ormasample.domain.executor.PostExecutionThread;
import yuki.android.ormasample.domain.executor.ThreadExecutor;
import yuki.android.ormasample.domain.repository.HistoryRepository;

public class HistoryViewUseCaseImpl implements HistoryViewUseCase {

    private HistoryRepository repo;

    private ThreadExecutor executor;

    private PostExecutionThread postThread;

    public HistoryViewUseCaseImpl(HistoryRepository repo,
            ThreadExecutor executor, PostExecutionThread postThread) {
        if (repo == null) {
            throw new NullPointerException("HistoryRepo was injected null. DI is unstable.");
        }
        this.repo = repo;

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
        long twoDaysAgo = DateTime.minus(DateTime.now(), -2, DateTime.FIELD_DAY);
        repo.findLatestHistory(twoDaysAgo, DateTime.now())
                .toList()
                .subscribeOn(Schedulers.from(executor))
                .observeOn(postThread.getScheduler())
                .subscribe(subscriber);
    }

    @Override
    public void getHistoryCount(Subscriber<Integer> subscriber) {
        repo.countHistory()
                .subscribeOn(Schedulers.from(executor))
                .observeOn(postThread.getScheduler())
                .subscribe(subscriber);
    }
}