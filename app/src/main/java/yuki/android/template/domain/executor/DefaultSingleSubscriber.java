package yuki.android.template.domain.executor;

import rx.SingleSubscriber;

public class DefaultSingleSubscriber<T> extends SingleSubscriber<T> {

    @Override
    public void onSuccess(T value) {
        // no-op by default.
    }

    @Override
    public void onError(Throwable error) {
        // no-op by default.
    }
}
