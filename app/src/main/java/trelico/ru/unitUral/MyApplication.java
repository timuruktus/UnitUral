package trelico.ru.unitUral;

import android.app.Application;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;
import toothpick.Toothpick;
import trelico.ru.unitUral.di.scopes.ScopeStorage;

public class MyApplication extends Application {

    public static MyApplication INSTANCE;
    private Cicerone<Router> cicerone;
    private ScopeStorage scopeStorage;
    public static String SCOPE_NAME = "appScope";
    public static String LOG_TAG = "custom_log";

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        cicerone = Cicerone.create();
        scopeStorage = new ScopeStorage();
        Toothpick.inject(this, scopeStorage.applicationScope);
    }

    public ScopeStorage getScopeStorage() {
        return scopeStorage;
    }

    public NavigatorHolder getNavigatorHolder() {
        return cicerone.getNavigatorHolder();
    }

    public Router getRouter() {
        return cicerone.getRouter();
    }

}
