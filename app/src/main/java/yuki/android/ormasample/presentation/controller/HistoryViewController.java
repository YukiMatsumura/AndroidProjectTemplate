package yuki.android.ormasample.presentation.controller;

import android.support.annotation.NonNull;

import java.util.List;

import yuki.android.ormasample.data.entity.History;
import yuki.android.ormasample.domain.executor.DefaultSingleSubscriber;
import yuki.android.ormasample.domain.executor.DefaultSubscriber;
import yuki.android.ormasample.domain.usecase.HistoryViewUseCase;
import yuki.android.ormasample.presentation.presenter.HistoryViewPresenter;

public class HistoryViewController implements Controller {

    private HistoryViewPresenter historyViewPresenter;

    private HistoryViewUseCase historyViewUseCase;

    public HistoryViewController(HistoryViewPresenter historyViewPresenter,
            HistoryViewUseCase historyViewUseCase) {
        this.historyViewPresenter = historyViewPresenter;
        this.historyViewUseCase = historyViewUseCase;
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

    /**
     * ヒストリのリストを表示するイベントキッカー.
     * このAPIは間近数件のヒストリ情報とヒストリの件数を取得する作用を持つ.
     */
    public void showHistory() {
        historyViewUseCase.showLatestHistory(new DefaultSubscriber<List<History>>() {
            @Override
            public void onNext(List<History> histories) {
                historyViewPresenter.onShowHistory(histories);
            }
        });
        historyViewUseCase.getHistoryCount(new DefaultSingleSubscriber<Integer>() {

            @Override
            public void onSuccess(Integer count) {
                // TODO: show history count
                // historyViewPresenter.showHistoryCount(count);
            }
        });
    }

    public void removeHistoryItem(@NonNull final History history) {
        historyViewUseCase.removeHistory(history.id, new DefaultSingleSubscriber<Integer>() {
            @Override
            public void onSuccess(Integer count) {
                historyViewPresenter.onRemovedHistory(history.id);
            }
        });
    }

    public void addHistory(@NonNull History history) {
        historyViewUseCase.addHistory(history, new DefaultSubscriber<Long>() {
            @Override
            public void onCompleted() {
                HistoryViewController.this.showHistory();
            }
        });
    }
}
