package yuki.android.ormasample.test;

import android.os.StrictMode;

import org.junit.rules.ExternalResource;
import org.robolectric.shadows.ShadowLog;

/**
 * Robolectricテスト用のRule. テスト実行前に下記の共通処理を実施する.
 * - ログ出力をSystem.outに繋ぐ
 * - StrictModeを無効にする
 */
public class DefaultRobolectricRule extends ExternalResource {
  private static final StrictMode.ThreadPolicy ROBOLECTRIC_THREAD_POLICY;

  static {
    ROBOLECTRIC_THREAD_POLICY = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    // VmPolicyは設定不要と思われる.
  }

  @Override protected void before() throws Throwable {
    ShadowLog.stream = System.out;
    StrictMode.setThreadPolicy(ROBOLECTRIC_THREAD_POLICY);
  }
}
