package yuki.android.ormasample.di.component;

import javax.inject.Singleton;

import dagger.Component;
import yuki.android.ormasample.App;
import yuki.android.ormasample.di.module.ApplicationModule;
import yuki.android.ormasample.di.module.RepositoryModule;

@Singleton
@Component(modules = {ApplicationModule.class, RepositoryModule.class})
public interface ApplicationComponent {

  void inject(App app);
}