package ir.parkban.general.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import ir.parkban.Parkban;

/**
 * Created by Payam on 7/27/2017.
 */

public class InternetConnectionChecker {

    public static boolean internetConnect() {
        ConnectivityManager cv = (ConnectivityManager) Parkban.getInstance().
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean checkNet = false;

        NetworkInfo netInfo = cv.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnected()) {

            checkNet = true;
        }
        return checkNet;


    }


    public static boolean internetConnectWifi() {
        ConnectivityManager cv = (ConnectivityManager)
                Parkban.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean checkNet = false;

        NetworkInfo netInfo = cv.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnected()) {
            if (netInfo.getType() == ConnectivityManager.TYPE_WIFI)
                checkNet = true;
        }
        return checkNet;
    }

    public static boolean internetConnectMobileData() {
        ConnectivityManager cv = (ConnectivityManager) Parkban.getInstance().getApplicationContext().
                getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean checkNet = false;

        NetworkInfo netInfo = cv.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnected()) {
            if (netInfo.getType() == ConnectivityManager.TYPE_MOBILE)
                checkNet = true;
        }
        return checkNet;
    }

}
