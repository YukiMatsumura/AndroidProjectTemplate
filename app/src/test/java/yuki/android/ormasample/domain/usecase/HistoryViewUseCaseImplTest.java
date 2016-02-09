package yuki.android.ormasample.domain.usecase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import yuki.android.ormasample.data.entity.History;
import yuki.android.ormasample.data.executor.TestCurrentThreadExecutor;
import yuki.android.ormasample.domain.executor.DefaultSubscriber;
import yuki.android.ormasample.domain.executor.PostExecutionThread;
import yuki.android.ormasample.domain.executor.ThreadExecutor;
import yuki.android.ormasample.domain.repository.HistoryRepository;
import yuki.android.ormasample.presentation.executor.TestExecutionThread;

import static junit.framework.Assert.fail;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class HistoryViewUseCaseImplTest {

    @Mock
    HistoryRepository historyRepository;

    @Mock
    ThreadExecutor threadExecutor;

    @Mock
    PostExecutionThread postExecutionThread;

    @InjectMocks
    HistoryViewUseCaseImpl historyViewUseCase;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = NullPointerException.class)
    public void UseCaseをnullで初期化() throws Exception {
        new HistoryViewUseCaseImpl(null, threadExecutor, postExecutionThread);
    }

    @Test(expected = NullPointerException.class)
    public void ThreadExecutorをnullで初期化() throws Exception {
        new HistoryViewUseCaseImpl(historyRepository, null, postExecutionThread);
    }

    @Test(expected = NullPointerException.class)
    public void PostExecutionThreadをnullで初期化() throws Exception {
        new HistoryViewUseCaseImpl(historyRepository, threadExecutor, null);
    }

    @Test
    public void 最近のヒストリを取得() throws Exception {
        final List<History> expect = Arrays.asList(
                new History().setId(1L).setLabel("Test1").setActiveDate(1234L),
                new History().setId(2L).setLabel("Test2").setActiveDate(6789L));
        when(historyRepository.findBetween(anyLong(), anyLong())).thenReturn(Observable.from(expect));

        HistoryViewUseCase useCase = new HistoryViewUseCaseImpl(
                historyRepository,
                new TestCurrentThreadExecutor(),
                new TestExecutionThread());
        useCase.showLatestHistory(new DefaultSubscriber<List<History>>() {
            @Override
            public void onNext(List<History> histories) {
                assertThat("準備したクエリ結果とは異なるiteratorが返却された", histories, equalTo(expect));
            }

            @Override
            public void onError(Throwable e) {
                fail("最近のヒストリ取得に失敗した");
            }
        });
    }
}