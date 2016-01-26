package yuki.android.ormasample.domain.executor;

public interface ThreadExecutor {
  void execute(final Runnable runnable);
}
