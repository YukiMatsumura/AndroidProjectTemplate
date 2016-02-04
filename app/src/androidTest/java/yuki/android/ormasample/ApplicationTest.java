package yuki.android.ormasample;

import com.fernandocejas.frodo.annotation.RxLogObservable;

import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Application;
import android.support.test.runner.AndroidJUnit4;
import android.test.ApplicationTestCase;

import rx.Observable;


@RunWith(AndroidJUnit4.class)
public class ApplicationTest extends ApplicationTestCase<Application> {

    public ApplicationTest() {
        super(Application.class);
    }

    @Test
    public void testHioge() {
        hoge().subscribe();
    }

    @RxLogObservable
    public Observable<Integer> hoge() {
        return Observable.just(1);
    }
}