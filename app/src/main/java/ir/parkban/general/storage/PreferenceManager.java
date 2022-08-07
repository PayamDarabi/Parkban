package ir.parkban.general.storage;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Payam on 7/27/2017.
 */

public class PreferenceManager {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    static PreferenceManager preferenceManager;


    public static PreferenceManager getInstance(Context conext) {
        if (preferenceManager != null) {
            return preferenceManager;
        } else {
            return new PreferenceManager(conext);
        }
    }

    private PreferenceManager(Context conext) {
        preferences = conext.getSharedPreferences("data", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void setUsername(String username) {
        editor.putString("username", username).apply();
    }

    public String getUsername() {
        return preferences.getString("username", "");
    }


    public void setToken(String token) {
        editor.putString("token", token).apply();
    }

    public String getToken() {
        return preferences.getString("token", "");
    }

    public void setIsSignedIn(Boolean isSignedIn) {
        editor.putBoolean("isSignedIn", isSignedIn).apply();
    }

    public Boolean getIsSignedIn() {
        return preferences.getBoolean("isSignedIn", false);
    }


    public void setUserSeeFirstIntroPage(boolean userSeeFirstIntroPage) {
        preferences.edit().putBoolean("userSeeFirstIntroPage", userSeeFirstIntroPage).commit();
    }

    public boolean isUserSeeFirstIntroduction() {
        return preferences.getBoolean("userSeeFirstIntroPage", false);
    }

    public void setUserSeeInteactiveHelp(boolean see) {
        preferences.edit().putBoolean("userSeeInteractiveHelp", see).commit();
    }

    public boolean isUserSeeInteractiveHelp() {
        return preferences.getBoolean("userSeeInteractiveHelp", false);

    }
    public void setFirebaseToken(String firebaseToken) {
        preferences.edit().putString("firebaseToken", firebaseToken).commit();
    }

    public String getFirebaseToken() {
        return preferences.getString("firebaseToken", "");
    }

    public void setShowParking(boolean showparking) {
        preferences.edit().putBoolean("showparking", showparking).commit();
    }

    public boolean isShowParking() {
        return preferences.getBoolean("showparking", false);
    }

    public void setShowTraffc(boolean showtraffc) {
        preferences.edit().putBoolean("showtraffc", showtraffc).commit();
    }

    public boolean isShowShowTraffc() {
        return preferences.getBoolean("showtraffc", false);
    }

    public void setSendToServer(boolean sendToServer) {
        preferences.edit().putBoolean("sendToServer", sendToServer).commit();
    }

    public boolean isSendToServer() {
        return preferences.getBoolean("sendToServer", false);
    }


}