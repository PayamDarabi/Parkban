package ir.parkban.general.utils;

import android.widget.Toast;

import ir.parkban.Parkban;

/**
 * Created by p.darabi on 8/21/2016.
 */
public class CustomToast {

    public static void show( String message){
        Toast.makeText(Parkban.getInstance().getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }
    public static void showShort( String message){
        Toast.makeText(Parkban.getInstance().getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }

}
