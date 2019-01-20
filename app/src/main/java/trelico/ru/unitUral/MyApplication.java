package trelico.ru.unitUral;

import android.app.Activity;
import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import toothpick.Toothpick;
import trelico.ru.unitUral.di.scopes.ScopeStorage;

public class MyApplication extends Application{

    public static MyApplication INSTANCE;
    private ScopeStorage scopeStorage;
    public static String SCOPE_NAME = "appScope";
    public static String TAG = "custom_log";
    private View navigationHostView;
    private Activity navigationHostActivity;
    private NavHostFragment navigationHostFragment;
    private NavController navController;

    @Override
    public void onCreate(){
        super.onCreate();

        Log.d(TAG, "pnCreate in MyApplication");
        INSTANCE = this;
        scopeStorage = new ScopeStorage();
        Toothpick.inject(this, scopeStorage.applicationScope);
    }

    public ScopeStorage getScopeStorage(){
        return scopeStorage;
    }

    public void setNavigationHost(NavController navController){
        this.navController = navController;
    }

    public NavController getNavigationHost(){
        return navController;
    }



}
