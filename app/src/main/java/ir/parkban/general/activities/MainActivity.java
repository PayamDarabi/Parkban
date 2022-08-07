package ir.parkban.general.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import androidx.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.parkban.Parking.AddParkingActivity;
import ir.parkban.R;
import ir.parkban.general.customView.CustomTextView;
import ir.parkban.general.storage.PreferenceManager;
import ir.parkban.general.utils.Constants;
import ir.parkban.general.utils.CustomToast;
import ir.parkban.general.webservice.ApiMethodCaller;
import ir.parkban.general.webservice.models.Response;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends BaseDrawerActivity
        implements
        OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {

    @BindView(R.id.rel_slidParking)
    RelativeLayout rel_slidparking;

    @BindView(R.id.rel_mainslider)
    CoordinatorLayout rel_mainslider;

    @BindView(R.id.txt_parkingname)
    CustomTextView txt_parkingname;

    @BindView(R.id.txt_parkingDetails)
    CustomTextView txt_parkingDetails;

    @BindView(R.id.txt_Workhours)
    CustomTextView txt_Workhours;

    @BindView(R.id.appbar)
    AppBarLayout appbar;

    @BindView(R.id.rbMainRate)
    MaterialRatingBar rb_parkingRate;


    private String cur_parkingID = "0";
    private static final int LOCATION_PERMISSION_REQ_CODE = 47;
    public static GoogleMap mMap;
    JSONObject mSearchpHelper;
    LocationManager mLocationManager;
    private BottomSheetBehavior mBottomSheetBehavior;

    //  Marker mTehran;
    private static final LatLng TEHRAN = new LatLng(35.6892, 51.3890);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setActionButtonVisibility(View.VISIBLE, View.GONE);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        @SuppressLint("ResourceType") ImageView locationButton = (ImageView) mapFragment.getView().findViewById(2);
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        // position on right bottom
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        rlp.setMargins(30, 30, 30, 30);
        locationButton.setImageResource(R.drawable.ic_ico_near_me);

        statusCheck();

        try {
            //     mSearchpHelper = new JSONObject(loadJSONFromAsset());
            // comeback
            /*  if (!PreferenceManager.getInstance(getApplicationContext()).isSendToServer() &&
                      !PreferenceManager.getInstance(getApplicationContext()).getFirebaseToken().isEmpty()) {
                 sendFirebaseToken(PreferenceManager.getInstance(getApplicationContext()).getFirebaseToken(),
                        getApplicationContext());
              }*/
            final String userName = PreferenceManager.getInstance(this).getUsername();
            ApiMethodCaller.getInstance(this).GetPublicParkingsList
                    (userName, new Callback<Response>() {
                @Override
                public void onResponse(Call<ir.parkban.general.webservice.models.Response> call, retrofit2.Response<Response> response) {
                    if (response.isSuccessful()) {
                        String msg = response.body().getMessage();
                        String resultCode = response.body().getReturnCode();
                        String token = response.body().getToken();
                        PreferenceManager.getInstance(
                                getApplicationContext()).setToken(token);
                        Object data = response.body().getData();
                        if (resultCode.equals("0")) {
                            int x =0;
                        }
                    } else {
                        CustomToast.showShort(response.message());
                    }
                }

                @Override
                public void onFailure(Call<ir.parkban.general.webservice.models.Response> call, Throwable t) {
                    CustomToast.showShort(getString(R.string.retrofit_failure));
                }
            });

            setProjectConstants();
            AutoCompleteTextView actv =  findViewById(R.id.edtSearch);
            actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String selection = (String) adapterView.getItemAtPosition(i);
                    String[] latlng = Constants.regionNap.get(selection).toString().split(Pattern.quote("|"));

                    LatLng latLng = new LatLng(Double.valueOf(latlng[0]), Double.valueOf(latlng[1]));

                    // Show the current location in Google Map
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));


                }
            });

//            mSearchpHelper.
        } catch (Exception e) {
            e.printStackTrace();
        }


        mBottomSheetBehavior = BottomSheetBehavior.from(rel_mainslider);
        mBottomSheetBehavior.setPeekHeight(dpToPx(this, 0));
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    appbar.setVisibility(View.GONE);
               //  comeback
                    //   Intent intent = new Intent(getBaseContext(), ParkingInfoActivity.class);
               //     intent.putExtra("parking_id", cur_parkingID);
                 //   startActivity(intent);

                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        LatLng origin = new LatLng(35.715298, 51.404343);
        CameraUpdate panToOrigin = CameraUpdateFactory.newLatLng(origin);
        mMap.moveCamera(panToOrigin);

        // set zoom level with animation
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16), 400, null);

        setMyLocationOnMap(mMap);

        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        mMap.getUiSettings().setRotateGesturesEnabled(false);

        MapStyleOptions style = MapStyleOptions.loadRawResourceStyle(getApplicationContext(), R.raw.nightmode);
        mMap.setMapStyle(style);
        mMap.setTrafficEnabled(PreferenceManager.getInstance(this).isShowShowTraffc());

     // comeback
        //   putmarkers();
        //   putPublicmarkers();

        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);


    }
    @Override
    protected void onResume() {
        super.onResume();
      // comeback
        //  putmarkers();

//
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {

        mBottomSheetBehavior = BottomSheetBehavior.from(rel_mainslider);
        mBottomSheetBehavior.setPeekHeight(dpToPx(this, 150));
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        rel_mainslider.setVisibility(View.VISIBLE);
        rel_slidparking.setVisibility(View.VISIBLE);
        appbar.setVisibility(View.GONE);
        //   cl_detailsPanel.setVisibility(View.GONE);
        // Retrieve the data from the marker.
        if (marker.getTag() != null) {
            cur_parkingID = (String) marker.getTag();
            LatLng latLng = new LatLng(marker.getPosition().latitude, marker.getPosition().longitude);

            // Show the current location in Google Map
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.getUiSettings().setMyLocationButtonEnabled(false);

          // comeback
            //  fillParkingData(cur_parkingID);

            //     isUp=false;
            // Check if a click count was set, then display the click count.
            //  Intent intent = new Intent(getBaseContext(), ParkingInfoActivity.class);
            //  intent.putExtra("parking_id", parkingid);
            //  startActivity(intent);
        }
        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }


    @Override
    public void onMapLongClick(LatLng latLng) {
        Intent intent = new Intent(getBaseContext(), AddParkingActivity.class);
       intent.putExtra("latlng", latLng);
       startActivity(intent);
    }
    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public void onMapClick(LatLng latLng) {
        rel_slidparking.setVisibility(View.GONE);
        //     cl_detailsPanel.setVisibility(View.GONE);
        rel_mainslider.setVisibility(View.GONE);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        appbar.setVisibility(View.VISIBLE);

    }

    private void setProjectConstants() throws JSONException {

        String[] colorsArray = getResources().getStringArray(R.array.car_colors);
        String[] typesArray = getResources().getStringArray(R.array.car_types);
        String[] charsArray = getResources().getStringArray(R.array.chars);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();

                try {



                    AssetManager am = getAssets();
                    InputStream inputStream = am.open("search.txt");
                    ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                    HashMap<String, Object> fileObj2 = (HashMap<String, Object>) objectInputStream.readObject();
                    final String[] names = new String[fileObj2.size()];
                    int i = 0;
                    for (Map.Entry<String, Object> entry : fileObj2.entrySet()) {
                        String key = entry.getKey();
                        names[i] = key;
                        Object value = entry.getValue();
                        Constants.regionNap.put(key, value);
                        i++;
                    }
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext()
                                    , android.R.layout.select_dialog_item, names);
                            AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.edtSearch);

                            actv.setThreshold(1);
                            actv.setAdapter(adapter);
                            TypedValue typedValue = new TypedValue();

                            TypedArray a = getApplicationContext().obtainStyledAttributes(typedValue.data, new int[]{R.color.white});
                            int color = a.getColor(0, 0);
                            actv.setDropDownBackgroundDrawable(new ColorDrawable(color));
                            //            actv.setTextColor();
                        }
                    });


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        for (int i = 0; i < colorsArray.length; i++)

            Constants.colorMap.put(i, colorsArray[i].toString());

        for (int i = 0; i < typesArray.length; i++)

            Constants.typeMap.put(i, typesArray[i].toString());

        for (int i = 0; i < charsArray.length; i++)

            Constants.charsMap.put(i, charsArray[i].toString());


    }
    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.gps_dialog_msg)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                        finish();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private String getAddressFromLatLng(LatLng latLng) {
        Geocoder geocoder = new Geocoder(this);

        String address = "";
        try {
            address = geocoder
                    .getFromLocation(latLng.latitude, latLng.longitude, 1)
                    .get(0).getAddressLine(0);
        } catch (IOException e) {
        }

        return address;
    }

    private void setMyLocationOnMap(GoogleMap googleMap) {
        // Enable MyLocation Layer of Google Map

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSION_REQ_CODE);

            return;
        }
        try {
            googleMap.setMyLocationEnabled(true);

            // Get LocationManager object from System Service LOCATION_SERVICE
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            // Create a criteria object to retrieve provider
            Criteria criteria = new Criteria();

            // Get the name of the best provider
            String provider = locationManager.getBestProvider(criteria, true);

            // Get Current Location
            Location myLocation = getLastKnownLocation();
            //set map type
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

            // Get latitude of the current location
            double latitude = myLocation.getLatitude();

            // Get longitude of the current location
            double longitude = myLocation.getLongitude();

            // Create a LatLng object for the current location
            LatLng latLng = new LatLng(latitude, longitude);

            // Show the current location in Google Map
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

            // Zoom in the Google Map
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(18));
            //  googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("You are here!"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Location getLastKnownLocation() {
        mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return null;
            }
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = getAssets().open("searchhelper.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static int dpToPx(Context context, int dp) {
        Resources resources = context.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                resources.getDisplayMetrics());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[]
            , int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQ_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setMyLocationOnMap(mMap);

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    public static void sendFirebaseToken(String token, final Context applicationContext) {
             try {
                ApiMethodCaller.getInstance(applicationContext)
                        .SendFCMToken(PreferenceManager.getInstance(applicationContext)
                                .getUsername()
                                   ,token,"top1", new Callback<Response>() {
                            @Override
                            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                                if (response.isSuccessful()) {
                                    String msg = response.body().getMessage();
                                    String resultCode = response.body().getReturnCode();
                                    String token = response.body().getToken();
                                    PreferenceManager.getInstance(applicationContext)
                                            .setToken(token);

                                    Object data = response.body().getData();
                                    if (resultCode.equals("0")) {
                                        CustomToast.show(msg);
                                        PreferenceManager.getInstance(
                                                applicationContext).setIsSignedIn(true);
                                    } else {
                                        CustomToast.showShort(resultCode + "-" + msg);
                                    }
                                } else {
                                    CustomToast.showShort(response.message());
                                }
                            }

                            @Override
                            public void onFailure(Call<ir.parkban.general.webservice.models.Response> call, Throwable t) {
                                CustomToast.showShort(applicationContext.getString(R.string.retrofit_failure));
                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
