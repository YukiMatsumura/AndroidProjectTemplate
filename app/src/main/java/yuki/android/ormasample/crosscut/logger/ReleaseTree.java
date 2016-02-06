package yuki.android.ormasample.crosscut.logger;

import android.util.Log;

import timber.log.Timber;

/**
 * リリースビルド用のTimber Tree.
 * このTreeは{@link Log#WARN}以上のログのみをLogcat出力する{@link Timber.DebugTree}として作用する.
 */
public class ReleaseTree extends Timber.DebugTree {

    private static final int MAX_LOG_PRIORITY = Log.INFO;

    protected boolean isLoggable(int priority) {
        return (priority > MAX_LOG_PRIORITY);
    }
}