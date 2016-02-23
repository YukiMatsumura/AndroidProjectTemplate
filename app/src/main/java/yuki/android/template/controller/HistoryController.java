package yuki.android.template.controller;

import yuki.android.template.crosscut.i18n.DateTimeUtils;
import yuki.android.template.model.HistoryEntity;
import yuki.android.template.model.HistoryModel;

public class HistoryController implements ActivityCallback {

    private HistoryModel historyModel;

    public HistoryController(HistoryModel historyModel) {
        if (historyModel == null) {
            throw new NullPointerException(
                    "HistoryRepo was injected null. DI is unstable.");
        }
        this.historyModel = historyModel;
    }

    public void showLatestHistory() {
        historyModel.findLatest();
    }

    public void getHistoryCount() {
        historyModel.countAll();
    }

    public void removeHistory(long rowId) {
        historyModel.deleteById(rowId);
    }

    public void addHistory(HistoryEntity entity) {
        historyModel.insert(new HistoryEntity(-1, DateTimeUtils.now(), entity.label));
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
}
