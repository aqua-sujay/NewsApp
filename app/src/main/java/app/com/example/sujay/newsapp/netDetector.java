package app.com.example.sujay.newsapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by sujay on 10-01-2016.
 */
public class netDetector {
    Context context=null;
    public netDetector(Context context)
    {
        this.context=context;
    }
    public boolean isConnected()
    {
        ConnectivityManager conmanager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(conmanager!=null)
        {
            NetworkInfo info=conmanager.getActiveNetworkInfo(); //gets currently actively connected
          /*  if(info!=null && info.isConnected()) {
                return true;
            }
            */
            boolean isConnected = info != null && info.isConnectedOrConnecting();
            return isConnected;
        }
        return false;

    }
}
