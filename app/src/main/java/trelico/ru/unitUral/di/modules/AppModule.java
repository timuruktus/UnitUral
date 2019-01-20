package trelico.ru.unitUral.di.modules;

import android.content.Context;

import androidx.room.Room;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import toothpick.config.Module;
import trelico.ru.unitUral.MyApplication;
import trelico.ru.unitUral.dataProviders.local.Settings;
import trelico.ru.unitUral.dataProviders.local.db.AppDatabase;
import trelico.ru.unitUral.dataProviders.web.BackendlessAPI;
import trelico.ru.unitUral.repositories.ISettingsRepository;
import trelico.ru.unitUral.repositories.PhoneInfoRepository;
import trelico.ru.unitUral.repositories.SettingsRepository;

import static trelico.ru.unitUral.dataProviders.web.BackendlessAPI.BASE_URL;

public class AppModule extends Module {

    private static Retrofit backendlessRetrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    public AppModule() {
        bind(Context.class).toInstance(MyApplication.INSTANCE);
        bind(BackendlessAPI.class).toInstance(backendlessRetrofit.create(BackendlessAPI.class));
        bind(ISettingsRepository.class).toInstance(new SettingsRepository());
        bind(Settings.class).toInstance(new Settings(MyApplication.INSTANCE));
        bind(PhoneInfoRepository.class).toInstance(new PhoneInfoRepository());
        bind(AppDatabase.class).toInstance(Room.databaseBuilder(MyApplication.INSTANCE,
                AppDatabase.class, "database").build());


    }
}
