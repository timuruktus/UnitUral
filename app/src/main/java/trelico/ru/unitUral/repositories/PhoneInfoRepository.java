package trelico.ru.unitUral.repositories;

import android.content.Context;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import trelico.ru.unitUral.models.local.InternetConnection;
import trelico.ru.unitUral.utils.NetworkChangeListener;

public class PhoneInfoRepository {

    @Inject
    Context context;
    NetworkChangeListener networkChangeListener;
    MutableLiveData<InternetConnection> internetConnection;


    public PhoneInfoRepository() {
        internetConnection = new MutableLiveData<>();
        networkChangeListener = new NetworkChangeListener(context);
    }

    public LiveData<InternetConnection> getInternetConnectionLiveData(){
        return networkChangeListener.getInternetConnection();
    }
}
