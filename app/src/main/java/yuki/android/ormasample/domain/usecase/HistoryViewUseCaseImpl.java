package yuki.android.ormasample.domain.usecase;

import java.util.List;

import javax.inject.Inject;

import yuki.android.ormasample.crosscut.i18n.DateTime;
import yuki.android.ormasample.data.entity.History;
import yuki.android.ormasample.di.scope.PerActivity;
import yuki.android.ormasample.domain.executor.ThreadExecutor;
import yuki.android.ormasample.domain.repository.HistoryRepository;

@PerActivity
public class HistoryViewUseCaseImpl implements HistoryViewUseCase {

    private HistoryRepository repo;

    private ThreadExecutor executor;

    private Callback callback;

    @Inject
    public HistoryViewUseCaseImpl(HistoryRepository repo, ThreadExecutor executor) {
        if (repo == null) {
            throw new NullPointerException("HistoryRepo was injected null. DI is unstable.");
        }
        this.repo = repo;

        if (executor == null) {
            throw new NullPointerException("JobExecutor was injected null. DI is unstable.");
        }
        this.executor = executor;
    }

    @Override
    public void run() {
        long twoWeekAgo = DateTime.minus(DateTime.now(), DateTime.FIELD_DAY, 2);
        List<History> latestHistory = repo.findLatestHistory(DateTime.now(), twoWeekAgo);
        callback.onLoad(latestHistory);
    }

    @Override
    public void showLatestHistory(Callback callback) {
        this.callback =
                (callback != null) ? callback : Callback.EMPTY_CALLBACK;
        executor.execute(this);
    }
}