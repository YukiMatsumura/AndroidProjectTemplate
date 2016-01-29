package yuki.android.ormasample.domain.usecase;

import javax.inject.Inject;

import yuki.android.ormasample.domain.executor.ThreadExecutor;
import yuki.android.ormasample.domain.repository.HistoryRepo;

public class HistoryViewUseCaseImpl implements HistoryViewUseCase {

    private HistoryRepo repo;

    private ThreadExecutor executor;

    @Inject
    public HistoryViewUseCaseImpl(HistoryRepo repo, ThreadExecutor executor) {
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

    }

    @Override
    public void showLatestHistory() {
        executor.execute(this);
    }
}