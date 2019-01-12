package trelico.ru.unitUral.main;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import ru.terrakok.cicerone.Navigator;
import toothpick.Toothpick;
import trelico.ru.unitUral.R;
import trelico.ru.unitUral.MyApplication;
import trelico.ru.unitUral.navigation.MainNavigator;
import trelico.ru.unitUral.navigation.Screens;
import trelico.ru.unitUral.repositories.ISettingsRepository;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.navHostFragment)
    FrameLayout navHostFragment;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.screen)
    ConstraintLayout screen;
    @Inject
    ISettingsRepository settingsRepository;
    public static String SCOPE_NAME = "mainActivityScope";

    private Navigator navigator = new MainNavigator(this, R.id.container);


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_news:

                        return true;
                    case R.id.navigation_projects:
                        MyApplication.INSTANCE.getRouter().navigateTo(new Screens.ProjectsScreen());
                        return true;
                    case R.id.navigation_settings:

                        return true;
                    case R.id.navigation_calendar:

                        return true;
                }
                return false;
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Toothpick.inject(this, MyApplication.INSTANCE.getScopeStorage().mainActivityScope);
        //TODO: add if(auth) open project screen else open join screen
        MyApplication.INSTANCE.getRouter().navigateTo(new Screens.ProjectsScreen());
    }


    @Override
    protected void onResume(){
        super.onResume();
        MyApplication.INSTANCE.getNavigatorHolder().setNavigator(navigator);
    }

    @Override
    protected void onPause(){
        super.onPause();
        MyApplication.INSTANCE.getNavigatorHolder().removeNavigator();
    }

}
