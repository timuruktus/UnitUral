package trelico.ru.unitUral.repositories;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import trelico.ru.unitUral.dataSources.local.Settings;

public class SettingsRepository implements ISettingsRepository {

    private MutableLiveData<String> userToken;
    private MutableLiveData<Boolean> isUserLoggedIn;
    @Inject
    Settings settings;

    @Override
    public LiveData<Boolean> isUserLoggedIn() {
        if (isUserLoggedIn == null) {
            isUserLoggedIn = new MutableLiveData<>();
            isUserLoggedIn.postValue(settings.isUserLoggedIn());
        }
        return isUserLoggedIn;
    }

    @Override
    public LiveData<String> getUserToken() {
        if (userToken == null) {
            userToken = new MutableLiveData<>();
            userToken.postValue(settings.getUserToken());
        }
        return userToken;
    }

    @Override
    public void setUserToken(String token) {
        settings.setUserToken(token);
        userToken.postValue(token);
    }

    @Override
    public void userLogout() {
        settings.setUserToken("");
        userToken.postValue("");
        isUserLoggedIn.postValue(false);
    }
}
