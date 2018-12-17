package trelico.ru.unitUral.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import trelico.ru.unitUral.R;
import trelico.ru.unitUral.models.local.InternetConnection;

public class NetworkChangeListener extends BroadcastReceiver {

    MutableLiveData<InternetConnection> internetConnection;
    private Context appContext;

    public NetworkChangeListener(Context context) {
        internetConnection = new MutableLiveData<>();
        internetConnection.setValue(InternetConnection.WI_FI);
        this.appContext = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager conn =  (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conn.getActiveNetworkInfo();

        // Checks the user prefs and the network connection. Based on the result, decides whether
        // to refresh the display or keep the current display.
        // If the userpref is Wi-Fi only, checks to see if the device has a Wi-Fi connection.
        if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            internetConnection.setValue(InternetConnection.WI_FI);
        } else if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            internetConnection.setValue(InternetConnection.MOBILE);
        } else if( networkInfo == null) {
            internetConnection.setValue(InternetConnection.NOTHING);
        }
    }

    public LiveData<InternetConnection> getInternetConnection() {
        return internetConnection;
    }
}