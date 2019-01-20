package trelico.ru.unitUral.destinations.main;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import butterknife.BindView;
import butterknife.ButterKnife;
import toothpick.Toothpick;
import trelico.ru.unitUral.MyApplication;
import trelico.ru.unitUral.R;
import trelico.ru.unitUral.repositories.ISettingsRepository;

public class MainActivity extends AppCompatActivity{

    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.screen)
    ConstraintLayout screen;
    @Inject
    ISettingsRepository settingsRepository;
    public static String SCOPE_NAME = "mainActivityScope";


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch(item.getItemId()){
            case R.id.navigation_news:
                MyApplication.INSTANCE.getNavigationHost().navigate(R.id.feedFragment);
                return true;
            case R.id.navigation_projects:
                MyApplication.INSTANCE.getNavigationHost().navigate(R.id.projectsFragment);
                return true;
            case R.id.navigation_settings:

                return true;
            case R.id.navigation_calendar:

                return true;
        }
        return false;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Toothpick.inject(this, MyApplication.INSTANCE.getScopeStorage().applicationScope);
        //TODO: add if(auth) open project screen else open join screen
        MyApplication.INSTANCE.setNavigationHost(Navigation.findNavController(this, R.id.navHostFragment));
//        MyApplication.INSTANCE.getNavigationHost().navigate(R.id.projectsFragment);
    }


    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

}
