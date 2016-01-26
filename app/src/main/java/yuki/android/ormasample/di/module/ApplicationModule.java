package yuki.android.ormasample.di.module;

import dagger.Module;
import yuki.android.ormasample.App;

@Module
public class ApplicationModule {

  private final App app;

  public ApplicationModule(App app) {
    this.app = app;
  }
}
