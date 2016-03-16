package yuki.m.android.husahusa.test;

import org.junit.rules.ExternalResource;
import org.robolectric.shadows.ShadowLog;

import android.os.StrictMode;

/**
 * Robolectricテスト用のRule. テスト実行前に下記の共通処理を実施する.
 * - ログ出力をSystem.outに繋ぐ
 * - StrictModeを無効にする
 */
public class DefaultRobolectricRule extends ExternalResource {

    private static final StrictMode.ThreadPolicy ROBOLECTRIC_THREAD_POLICY;

    static {
        // UnitTestではUI Threadなどのポリシーは適用外であるため下記を設定.
        ROBOLECTRIC_THREAD_POLICY = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
    }

    @Override
    protected void before() throws Throwable {
        ShadowLog.stream = System.out;
        StrictMode.setThreadPolicy(ROBOLECTRIC_THREAD_POLICY);
    }
}
