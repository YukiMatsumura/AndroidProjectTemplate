package yuki.android.ormasample;

import com.fernandocejas.frodo.annotation.RxLogObservable;

import org.junit.Test;

import rx.Observable;
import rx.Subscriber;


public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() throws Exception {
        hoge().subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                System.out.println(e.getMessage());
            }

            @Override
            public void onNext(Integer integer) {

            }
        });
    }

    @RxLogObservable
    private Observable<Integer> hoge() {
        return Observable.range(0, 3).single();
    }
}