package ir.parkban;

import android.app.Application;
import android.content.res.Configuration;

public class Parkban extends Application {
    private static Parkban mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            mInstance = this;
            // Required initialization logic here!
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Parkban getInstance() {
        return mInstance;
    }

    // Called by the system when the device configuration changes while your component is running.
    // Overriding this method is totally optional!
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    // This is called when the overall system is running low on memory,
    // and would like actively running processes to tighten their belts.
    // Overriding this method is totally optional!


    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

}
