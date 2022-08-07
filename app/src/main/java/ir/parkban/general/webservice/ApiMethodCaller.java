package ir.parkban.general.webservice;


import android.content.Context;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import ir.parkban.BuildConfig;
import ir.parkban.Parkban;
import ir.parkban.general.storage.PreferenceManager;
import ir.parkban.general.utils.AppInfoProvider;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


/**
 * Created by Novin Pendar on 1/13/2016.
 */
public class ApiMethodCaller {


    private static ApiMethodCaller apiMethodCaller;
    private static Retrofit retrofit;
    private static ApiCaller apiCaller;
    private static Context mContext;
    private final static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();;

    private ApiMethodCaller(Context context) {

        mContext = context;
        httpClient.readTimeout(50, TimeUnit.SECONDS);
        httpClient.connectTimeout(50, TimeUnit.SECONDS);
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .addHeader("Content-Type",
                                "application/json")
                        .addHeader("app-key",
                                AppInfoProvider.getAppKey())
                        .addHeader("device-id",
                                AppInfoProvider.getAndroidID(Parkban.getInstance().getApplicationContext()))
                        .addHeader("accept-language",
                                AppInfoProvider.getAcceptLanguage())
                        .addHeader("client-platform-type",
                                AppInfoProvider.getClientPlatformType())
                        .addHeader("app-version",
                                AppInfoProvider.getAppVersion(Parkban.getInstance().getApplicationContext()) + "")
                        .addHeader("token",
                                PreferenceManager.getInstance(Parkban.getInstance().getApplicationContext()).getToken())
                        .build();
                return chain.proceed(request);
            }
        });
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);
        }
        Gson gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL,
                        Modifier.TRANSIENT,
                        Modifier.STATIC)
                .create();

        retrofit = new Retrofit.Builder()
                .client(httpClient.build())
                .baseUrl(AppInfoProvider.getBaseURL())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiCaller = retrofit.create(ApiCaller.class);
    }

    @Nullable
    public static ApiMethodCaller getInstance(final Context context) {
        try {
            if (apiMethodCaller == null)
                apiMethodCaller = new ApiMethodCaller(context);
            return apiMethodCaller;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public Call<ir.parkban.general.webservice.models.Response> Register(String userName,
                                                                        Callback<ir.parkban.general.webservice.models.Response> callback) {
        HashMap<String,String> obj = new HashMap<>();

        try {
            obj.put("username", userName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String path = AppInfoProvider.getURLSecPart() + "/register";
        Call<ir.parkban.general.webservice.models.Response> caller = apiCaller.Register
                (path, obj);
        caller.enqueue(callback);
        return caller;
    }
    public Call<ir.parkban.general.webservice.models.Response> Login(String userName,
                                                                     String onetime_password,
                                                                     Callback<ir.parkban.general.webservice.models.Response> callback) {
        HashMap<String,String> obj = new HashMap<>();
        try {
            obj.put("username", userName);
            obj.put("onetime_password", onetime_password);

        } catch (Exception e) {
            e.printStackTrace();
        }
        String path = AppInfoProvider.getURLSecPart() + "/login";
        Call<ir.parkban.general.webservice.models.Response> caller = apiCaller.Login
                (path, obj);
        caller.enqueue(callback);
        return caller;

    }
    public Call<ir.parkban.general.webservice.models.Response> GetPublicParkingsList(String userName,
                                                                                      Callback<ir.parkban.general.webservice.models.Response> callback) {
        JSONObject obj = new JSONObject();

        try {
            obj.put("username", userName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String path = AppInfoProvider.getURLSecPart() + "/public/list";
        Call<ir.parkban.general.webservice.models.Response> caller = apiCaller.GetPublicParkingsList
                (path, obj);
        caller.enqueue(callback);
        return caller;
    }

    public Call<ir.parkban.general.webservice.models.Response> GetCarsList(String userName,
                                                                                     Callback<ir.parkban.general.webservice.models.Response> callback) {
        JSONObject obj = new JSONObject();

        try {
            obj.put("username", userName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String path = AppInfoProvider.getURLSecPart() + "/car/list";
        Call<ir.parkban.general.webservice.models.Response> caller = apiCaller.GetCarsList
                (path, obj);
        caller.enqueue(callback);
        return caller;
    }
    public Call<ir.parkban.general.webservice.models.Response> AddCar(String userName,
                                                                           String car_type,
                                                                           String full_name,
                                                                           String car_color,
                                                                           String plate_number,
                                                                           Callback<ir.parkban.general.webservice.models.Response> callback) {
        JSONObject obj = new JSONObject();

        try {
            obj.put("username", userName);
            obj.put("car_type", car_type);
            obj.put("full_name", full_name);
            obj.put("car_color", car_color);
            obj.put("plate_number", plate_number);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String path = AppInfoProvider.getURLSecPart() + "/car/add";
        Call<ir.parkban.general.webservice.models.Response> caller = apiCaller.AddCar
                (path, obj);
        caller.enqueue(callback);
        return caller;
    }

    public Call<ir.parkban.general.webservice.models.Response> GetParkedCarsList(String userName,
                                                                      String parking_id,
                                                                      Callback<ir.parkban.general.webservice.models.Response> callback) {
        JSONObject obj = new JSONObject();

        try {
            obj.put("username", userName);
            obj.put("parking_id", parking_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String path = AppInfoProvider.getURLSecPart() + "/car/parked";
        Call<ir.parkban.general.webservice.models.Response> caller = apiCaller.GetParkedCarsList
                (path, obj);
        caller.enqueue(callback);
        return caller;
    }


    public Call<ir.parkban.general.webservice.models.Response> GetParkingsList(String userName,
                                                                                 String user_id,
                                                                                 Callback<ir.parkban.general.webservice.models.Response> callback) {
        JSONObject obj = new JSONObject();

        try {
            obj.put("username", userName);
            obj.put("user_id", user_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String path = AppInfoProvider.getURLSecPart() + " /list";
        Call<ir.parkban.general.webservice.models.Response> caller = apiCaller.GetParkingsList
                (path, obj);
        caller.enqueue(callback);
        return caller;
    }

    public Call<ir.parkban.general.webservice.models.Response> GetParkingsPhotos(String userName,
                                                                                 String parking_id,
                                                                                 Callback<ir.parkban.general.webservice.models.Response> callback) {
        JSONObject obj = new JSONObject();

        try {
            obj.put("username", userName);
            obj.put("parking_id", parking_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String path = AppInfoProvider.getURLSecPart() + "/photo/list";
        Call<ir.parkban.general.webservice.models.Response> caller = apiCaller.GetParkingPhotos
                (path, obj);
        caller.enqueue(callback);
        return caller;
    }

    public Call<ir.parkban.general.webservice.models.Response> AddParkingPhoto(String userName,
                                                                                String description,
                                                                                String photo_address,
                                                                                String parking_id,
                                                                                Boolean is_main,
                                                                                Callback<ir.parkban.general.webservice.models.Response> callback) {
        JSONObject obj = new JSONObject();

        try {
            obj.put("username", userName);
            obj.put("description", description);
            obj.put("photo_address", photo_address);
            obj.put("parking_id", parking_id);
            obj.put("is_main", is_main);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String path = AppInfoProvider.getURLSecPart() + "/photo/add";
        Call<ir.parkban.general.webservice.models.Response> caller = apiCaller.AddParkingPhoto
                (path, obj);
        caller.enqueue(callback);
        return caller;
    }
    public Call<ir.parkban.general.webservice.models.Response> SendFCMToken(String userName,
                                                                            String fcm_token,
                                                                            String topic_id,
                                                                                 Callback<ir.parkban.general.webservice.models.Response> callback) {
        HashMap<String,String> obj = new HashMap<>();

        try {
            obj.put("username", userName);
            obj.put("fcm_token", fcm_token);
            obj.put("topic_id", topic_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String path = AppInfoProvider.getURLSecPart() + "/fcm/token";
        Call<ir.parkban.general.webservice.models.Response> caller = apiCaller.SendFCMToken
                (path, obj);
        caller.enqueue(callback);
        return caller;
    }

    public Call<ir.parkban.general.webservice.models.Response> GetProfile(String userName,
                                                                           Callback<ir.parkban.general.webservice.models.Response> callback) {
        HashMap<String,String> obj = new HashMap<>();

        try {
            obj.put("username", userName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String path = AppInfoProvider.getURLSecPart() + "/user/profile/get";
        Call<ir.parkban.general.webservice.models.Response> caller = apiCaller.GetProfile
                (path, obj);
        caller.enqueue(callback);
        return caller;
    }
    public Call<ir.parkban.general.webservice.models.Response> EditProfile(HashMap<String,String> obj,
                                                                          Callback<ir.parkban.general.webservice.models.Response> callback) {

        String path = AppInfoProvider.getURLSecPart() + "/user/profile/edit";
        Call<ir.parkban.general.webservice.models.Response> caller = apiCaller.EditProfile
                (path, obj);
        caller.enqueue(callback);
        return caller;
    }
}
