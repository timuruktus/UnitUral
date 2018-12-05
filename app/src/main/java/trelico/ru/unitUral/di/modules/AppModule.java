package trelico.ru.unitUral.di.modules;

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import toothpick.config.Module;
import trelico.ru.unitUral.MyApplication;
import trelico.ru.unitUral.dataSources.local.Settings;
import trelico.ru.unitUral.dataSources.web.BackendlessAPI;
import trelico.ru.unitUral.repositories.ISettingsRepository;
import trelico.ru.unitUral.repositories.SettingsRepository;

import static trelico.ru.unitUral.dataSources.web.BackendlessAPI.BASE_URL;

public class AppModule extends Module {

    private Retrofit backendlessRetrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();;


    public AppModule() {
        bind(Context.class).toInstance(MyApplication.INSTANCE);
        bind(BackendlessAPI.class).toInstance(backendlessRetrofit.create(BackendlessAPI.class));
        bind(ISettingsRepository.class).toInstance(new SettingsRepository());
        bind(Settings.class).toInstance(new Settings());


    }
}