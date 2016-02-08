package yuki.android.ormasample.presentation.presenter;

import com.fernandocejas.frodo.annotation.RxLogSubscriber;

import java.util.List;

import javax.inject.Inject;

import yuki.android.ormasample.data.entity.History;
import yuki.android.ormasample.di.scope.ActivityScope;
import yuki.android.ormasample.domain.executor.DefaultSingleSubscriber;
import yuki.android.ormasample.domain.executor.DefaultSubscriber;
import yuki.android.ormasample.domain.usecase.HistoryViewUseCase;
import yuki.android.ormasample.presentation.view.activity.HistoryViewActivity;

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

    /**
     * ヒストリのリストを表示するイベントキッカー.
     * このAPIは間近数件のヒストリ情報とヒストリの件数を取得する作用を持つ.
     */
    public void showHistory() {
        historyViewUseCase.showLatestHistory(new HistoryListSubscriber());
        historyViewUseCase.getHistoryCount(new HistoryCountSubscriber());
    }

    public void removeHistoryItem(long itemId) {
        historyViewUseCase.removeHistory(itemId, new HistoryRemoveSubscriber(itemId));
    }

    public void addHistory() {
        historyViewUseCase.addHistory(new History().setLabel("Hoge"), new HistoryAddSubscriber());
    }

    /**
     * ヒストリのリスト更新を監視・通知するSubscriber.
     * このSubscriberは{@link rx.Subscriber}として作用し, リストが取得されるとpresenterにそれを伝播する.
     */
    @RxLogSubscriber
    private final class HistoryListSubscriber extends DefaultSubscriber<List<History>> {

        @Override
        public void onNext(List<History> histories) {
            viewer.showHistoryList(histories);
        }
    }

    /**
     * ヒストリの件数取得を監視・通知するSubscriber.
     * このSubscriberは{@link rx.SingleSubscriber}として作用し, 件数が取得されるとpresenterにそれを伝播する.
     */
    @RxLogSubscriber
    private final class HistoryCountSubscriber extends DefaultSingleSubscriber<Integer> {

        @Override
        public void onSuccess(Integer count) {
            if (count == null) {
                count = 0;
            }
            viewer.showHistoryCount(count);
        }
    }

    /**
     * ヒストリの削除を監視・通知するSubscriber.
     * このSubscriberは{@link rx.SingleSubscriber}として作用し, 件数が取得されるとpresenterにそれを伝播する.
     */
    @RxLogSubscriber
    private final class HistoryRemoveSubscriber extends DefaultSingleSubscriber<Integer> {

        private final long itemId;

        public HistoryRemoveSubscriber(long itemId) {
            this.itemId = itemId;
        }

        @Override
        public void onSuccess(Integer count) {
            if (0 < count) {
                viewer.onRemovedHistory(itemId);
            }
            // TODO: 削除が0件だった場合.
        }
    }

    private final class HistoryAddSubscriber extends DefaultSubscriber<Long> {

        @Override
        public void onCompleted() {
            showHistory();
        }
    }
}
