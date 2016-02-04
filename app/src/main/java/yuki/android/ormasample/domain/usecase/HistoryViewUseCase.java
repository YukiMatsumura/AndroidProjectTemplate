package yuki.android.ormasample.domain.usecase;

import java.util.List;

import rx.Subscriber;
import yuki.android.ormasample.data.entity.History;

public interface HistoryViewUseCase extends UseCase {

    void showLatestHistory(Subscriber<List<History>> subscriber);

    void getHistoryCount(Subscriber<Integer> subscriber);
}
