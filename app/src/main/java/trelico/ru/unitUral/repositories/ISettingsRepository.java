package trelico.ru.unitUral.repositories;

import androidx.lifecycle.LiveData;

public interface ISettingsRepository {


    LiveData<Boolean> isUserLoggedIn();
    LiveData<String> getUserToken();



    void setUserToken(String token);
    void userLogout();
}
