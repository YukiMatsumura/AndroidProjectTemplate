package yuki.android.ormasample.presentation.presenter;

import com.fernandocejas.frodo.annotation.RxLogSubscriber;

import java.util.List;

import javax.inject.Inject;

import yuki.android.ormasample.data.entity.History;
import yuki.android.ormasample.di.scope.ActivityScope;
import yuki.android.ormasample.domain.executor.DefaultSubscriber;
import yuki.android.ormasample.domain.usecase.HistoryViewUseCase;
import yuki.android.ormasample.presentation.view.HistoryViewActivity;

@ActivityScope
public class HistoryViewPresenter implements Presenter<HistoryViewActivity> {

    private HistoryViewActivity viewer;

    @Inject
    HistoryViewUseCase historyViewUseCase;

    @Inject
    public HistoryViewPresenter() {
    }

    /**
     * 画面が生成された場合のライフサイクルコールバック.
     */
    @Override
    public void create(HistoryViewActivity activity) {
        this.viewer = activity;
        this.viewer.getComponent().inject(this);
    }

    /**
     * 画面がフォアグラウンドに遷移した場合のライフサイクルコールバック.
     */
    @Override
    public void resume() {

    }

    /**
     * 画面がバックグラウンドに遷移した場合のライフサイクルコールバック.
     */
    @Override
    public void pause() {

    }

    /**
     * 画面が破棄された場合のライフサイクルコールバック.
     */
    @Override
    public void destroy() {

    }

    public void showHistory() {
        historyViewUseCase.showLatestHistory(new HistoryListSubscriber());
        historyViewUseCase.getHistoryCount(new HistoryCountSubscriber());
    }

    private void updateHistoryView(List<History> histories) {
        viewer.onShowHistoryList(histories);
    }

    private void updateHistoryCount(Integer count) {
        if (count == null) {
            count = 0;
        }
        viewer.onShowHistoryCount(count);
    }

    @RxLogSubscriber
    private final class HistoryListSubscriber extends DefaultSubscriber<List<History>> {

        @Override
        public void onNext(List<History> histories) {
            HistoryViewPresenter.this.updateHistoryView(histories);
        }
    }

    @RxLogSubscriber
    private final class HistoryCountSubscriber extends DefaultSubscriber<Integer> {

        @Override
        public void onNext(Integer histories) {
            HistoryViewPresenter.this.updateHistoryCount(histories);
        }
    }
}
