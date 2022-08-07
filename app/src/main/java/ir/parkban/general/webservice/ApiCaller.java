package ir.parkban.general.webservice;


import org.json.JSONObject;

import java.util.HashMap;

import ir.parkban.general.webservice.models.Response;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Url;


/**
 * Created by payam on 12/24/2017
 */
public interface ApiCaller {

    @POST
    public Call<Response> Register(@Url String path, @Body HashMap<String,String> object);

    @POST
    public Call<Response> Login(@Url String path, @Body HashMap<String,String> object);

    @POST
    public Call<Response> GetPublicParkingsList(@Url String path, @Body JSONObject object);

    @POST
    public Call<Response> GetCarsList(@Url String path, @Body JSONObject object);

    @POST
    public Call<Response> AddCar(@Url String path, @Body JSONObject object);

    @POST
    public Call<Response> GetParkedCarsList(@Url String path, @Body JSONObject object);

    @POST
    public Call<Response> GetParkingsList(@Url String path, @Body JSONObject object);

    @POST
    public Call<Response> GetParkingPhotos(@Url String path, @Body JSONObject object);

    @POST
    public Call<Response> AddParkingPhoto(@Url String path, @Body JSONObject object);

    @POST
    public Call<Response> SendFCMToken(@Url String path, @Body  HashMap<String,String> object);

    @POST
    public Call<Response> GetProfile(@Url String path, @Body  HashMap<String,String> object);

    @POST
    public Call<Response> EditProfile(@Url String path, @Body  HashMap<String,String> object);


}