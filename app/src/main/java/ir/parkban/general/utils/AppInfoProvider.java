package ir.parkban.general.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import androidx.core.app.ActivityCompat;
import android.telephony.TelephonyManager;

import ir.parkban.Parkban;
import ir.parkban.R;

/**
 * Created by Payam on 12/28/2017.
 */

public class AppInfoProvider {

    public static String getBaseURL() {
        return Constants.BASE_URL;
    }

    public static String getAppKey() {
        return Constants.APP_KEY;
    }


    public static String getURLSecPart() {
        return Constants.URL_SEC_PART;
    }

    public static int getAndroidApiVersion() {
        return Build.VERSION.SDK_INT;
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    public static int getAppVersion(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getAppVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }


    public static String getAndroidID(Context context) {
        String androidID;

        try {
            androidID = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            androidID = "";
            e.printStackTrace();
        }
        return androidID;
    }

    public static String getIMEI(Context context) {
        String Imei;

        try {
            TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService
                    (context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return "";
            }
            Imei = TelephonyMgr.getDeviceId();
        } catch (Exception e) {
            Imei = "";
            e.printStackTrace();
        }
        return Imei;
    }

    public static String getAcceptLanguage(){
        return Parkban.getInstance().getApplicationContext().getString(R.string.accept_language);
    }

    public  static String getClientPlatformType(){
        return Parkban.getInstance().getApplicationContext().getString(R.string.android);
    }
}
