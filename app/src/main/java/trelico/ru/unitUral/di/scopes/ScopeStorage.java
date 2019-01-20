package trelico.ru.unitUral.di.scopes;

import toothpick.Scope;
import toothpick.Toothpick;
import trelico.ru.unitUral.MyApplication;
import trelico.ru.unitUral.destinations.main.MainActivity;
import trelico.ru.unitUral.destinations.projects.ProjectsFragment;
import trelico.ru.unitUral.di.modules.AppModule;
import trelico.ru.unitUral.di.modules.ProjectsModule;

public class ScopeStorage {

    public Scope applicationScope;
    public Scope mainActivityScope;
    public Scope projectsScope;

    public ScopeStorage() {
        applicationScope = Toothpick.openScope(MyApplication.SCOPE_NAME);
        applicationScope.installModules(new AppModule());

        mainActivityScope = Toothpick.openScopes(MyApplication.SCOPE_NAME, MainActivity.SCOPE_NAME);
        mainActivityScope.installModules();

        projectsScope = Toothpick.openScopes(MyApplication.SCOPE_NAME,
                MainActivity.SCOPE_NAME, ProjectsFragment.SCOPE_NAME);
        projectsScope.installModules(new ProjectsModule());
    }
}
