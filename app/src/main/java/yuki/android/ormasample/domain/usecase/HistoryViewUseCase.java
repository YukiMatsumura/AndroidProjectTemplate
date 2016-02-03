package yuki.android.ormasample.domain.usecase;

import java.util.List;

import yuki.android.ormasample.data.entity.History;
import yuki.android.ormasample.domain.exception.ErrorBundle;

public interface HistoryViewUseCase extends UseCase {

    interface Callback {

        Callback EMPTY_CALLBACK = new Callback() {

            @Override
            public void onLoad(List<History> histories) {
                // 空のコールバック
            }

            @Override
            public void onError(ErrorBundle error) {
                // 空のコールバック
            }
        };

        void onLoad(List<History> histories);

        void onError(ErrorBundle error);
    }

    void showLatestHistory(Callback callback);
}
