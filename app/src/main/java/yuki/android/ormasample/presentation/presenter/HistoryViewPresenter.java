package yuki.android.ormasample.presentation.presenter;

import java.util.List;

import javax.inject.Inject;

import yuki.android.ormasample.data.entity.History;
import yuki.android.ormasample.di.scope.PerActivity;
import yuki.android.ormasample.domain.exception.ErrorBundle;
import yuki.android.ormasample.domain.usecase.HistoryViewUseCase;
import yuki.android.ormasample.presentation.view.HistoryViewActivity;

@PerActivity
public class HistoryViewPresenter implements Presenter<HistoryViewActivity> {

    private HistoryViewActivity viewerActivity;

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
        this.viewerActivity = activity;
        this.viewerActivity.getComponent().inject(this);
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
        historyViewUseCase.showLatestHistory(new HistoryViewUseCase.Callback() {
            @Override
            public void onLoad(final List<History> histories) {
                viewerActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        viewerActivity.onShowHistories(histories);
                    }
                });
            }

            @Override
            public void onError(ErrorBundle error) {

            }
        });
    }
}
