package common.Injection.component;

import com.the_movie.dagger.ApplicationComponent;

import javax.inject.Singleton;

import common.Injection.module.ApplicationTestModule;
import common.Injection.module.PresenterModule;
import dagger.Component;

/**
 * Created by Abgaryan on 3/21/18.
 */

@Singleton
@Component(modules = {ApplicationTestModule.class, PresenterModule.class})
public interface TestComponent extends ApplicationComponent {

}