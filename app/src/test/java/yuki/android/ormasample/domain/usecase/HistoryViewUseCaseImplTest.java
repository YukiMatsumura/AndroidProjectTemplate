package yuki.android.ormasample.domain.usecase;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import yuki.android.ormasample.data.entity.History;
import yuki.android.ormasample.data.executor.JobExecutor;
import yuki.android.ormasample.data.executor.TestCurrentThreadExecutor;
import yuki.android.ormasample.data.repository.HistoryRepositoryImpl;
import yuki.android.ormasample.domain.exception.ErrorBundle;
import yuki.android.ormasample.domain.repository.HistoryRepository;

import static junit.framework.Assert.fail;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class HistoryViewUseCaseImplTest {

    @Test
    public void HistoryViewUseCaseの初期化() throws Exception {
        try {
            new HistoryViewUseCaseImpl(null, new JobExecutor());
            fail("HistoryViewUseCaseの初期化でRepositoryにnullを指定しても失敗しない");
        } catch (NullPointerException e) {
        }
        try {
            new HistoryViewUseCaseImpl(mock(HistoryRepositoryImpl.class), null);
            fail("HistoryViewUseCaseの初期化でThreadExecutorにnullを指定しても失敗しない");
        } catch (NullPointerException e) {
        }
    }

    @Test
    public void 最近のヒストリを取得() throws Exception {
        final List<History> it = Arrays.asList(
                new History().setId(1L).setLabel("Test1").setActiveDate(1234L),
                new History().setId(2L).setLabel("Test2").setActiveDate(6789L));
        HistoryRepository repo = mock(HistoryRepositoryImpl.class);
        when(repo.findLatestHistory(anyLong(), anyLong())).thenReturn(it);

        HistoryViewUseCase useCase = new HistoryViewUseCaseImpl(repo, new TestCurrentThreadExecutor());
        useCase.showLatestHistory(new HistoryViewUseCase.Callback() {
            @Override
            public void onLoad(List<History> histories) {
                assertThat("準備したクエリ結果とは異なるiteratorが返却された", histories, equalTo(it));
            }

            @Override
            public void onError(ErrorBundle error) {
                fail("最近のヒストリ取得に失敗した");
            }
        });
    }
}