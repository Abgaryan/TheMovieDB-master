package com.the_movie.comman;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Abgaryan on 3/21/18.
 */

public class Utils {




    public static boolean isWiFiConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        return networkInfo.isConnected();
    }











}
