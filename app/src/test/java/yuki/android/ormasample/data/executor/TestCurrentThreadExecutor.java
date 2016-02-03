package yuki.android.ormasample.data.executor;

import yuki.android.ormasample.domain.executor.ThreadExecutor;

/**
 * テスト用. このExecutorは別スレッドではなく, 呼び出し元と同じスレッドで{@link Runnable}を実行する.
 * このクラスはテスト時に非同期結果の検証用ユーティリティとして使用されることを想定している.
 *
 * @see JobExecutor
 */
public class TestCurrentThreadExecutor implements ThreadExecutor {

    @Override
    public void execute(Runnable runnable) {
        runnable.run();
    }
}