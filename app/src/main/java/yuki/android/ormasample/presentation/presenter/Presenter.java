package yuki.android.ormasample.presentation.presenter;

import android.app.Activity;

public interface Presenter<T extends Activity> {

    /**
     * 画面が生成された場合のライフサイクルコールバック.
     */
    void create(T activity);

    /**
     * 画面がフォアグラウンドに遷移した場合のライフサイクルコールバック.
     */
    void resume();

    /**
     * 画面がバックグラウンドに遷移した場合のライフサイクルコールバック.
     */
    void pause();

    /**
     * 画面が破棄された場合のライフサイクルコールバック.
     */
    void destroy();
}
