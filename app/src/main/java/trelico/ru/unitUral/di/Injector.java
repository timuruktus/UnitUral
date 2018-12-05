package trelico.ru.unitUral.di;

import toothpick.Scope;
import toothpick.Toothpick;
import trelico.ru.unitUral.MyApplication;
import trelico.ru.unitUral.di.modules.AppModule;
import trelico.ru.unitUral.di.modules.ProjectsModule;
import trelico.ru.unitUral.main.MainActivity;
import trelico.ru.unitUral.projects.ProjectsFragment;
import trelico.ru.unitUral.projects.ProjectsViewModel;

public class Injector {

    private Scope applicationScope;
    private Scope mainActivityScope;
    private Scope projectsScope;

    public Injector() {
        applicationScope = Toothpick.openScope(MyApplication.SCOPE_NAME);
        applicationScope.installModules(new AppModule());

        mainActivityScope = Toothpick.openScope(new Object[]{MyApplication.SCOPE_NAME, MainActivity.SCOPE_NAME});
        mainActivityScope.installModules();

        projectsScope = Toothpick.openScope(new Object[]{MyApplication.SCOPE_NAME,
                MainActivity.SCOPE_NAME, ProjectsFragment.SCOPE_NAME});
    }

    public void inject(Object object){
        if(object instanceof MainActivity){
            Toothpick.inject(object, mainActivityScope);
        }else if(object instanceof MyApplication){
            Toothpick.inject(object, applicationScope);
        }else if(object instanceof ProjectsViewModel){
            Toothpick.inject(object, projectsScope);
        }else if(object instanceof ProjectsFragment){
            Toothpick.inject(object, projectsScope);
            toothpick.Injector
        }
    }

}
