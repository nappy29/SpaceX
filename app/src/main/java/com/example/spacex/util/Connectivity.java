package com.example.spacex.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;

public class Connectivity {

    public static boolean isInternetOn(Context context) {

        if (isMobileOrWifiConnectivityAvailable(context)) {
            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.connect();
                return (urlc.getResponseCode() == 200);
            } catch (Exception e) {
                Log.e("Internet Status!:" , e.toString());
            }
        } else {
           Log.i("Internet Status!", "No internet connectivity");
        }
        return false;
    }


    public static boolean isMobileOrWifiConnectivityAvailable(Context ctx) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;


        try {
            ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo[] netInfo = cm.getAllNetworkInfo();
            for (NetworkInfo ni : netInfo) {
                if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                    if (ni.isConnected()) {
                        haveConnectedWifi = true;

                        Log.i("Internet Status:" , "Wifi connection");
                    }
                if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                    if (ni.isConnected()) {
                        haveConnectedMobile = true;

                        Log.i("Internet Status:" , "Mobile data connection");
                    }
            }
        } catch (Exception e) {
            Log.e("InternetOn() Exception:" , e.toString());
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}
