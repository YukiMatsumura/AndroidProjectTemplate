package yuki.android.template.presentation.controller;

public interface Controller {

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
