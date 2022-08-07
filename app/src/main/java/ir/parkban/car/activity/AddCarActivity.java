package ir.parkban.car.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.common.api.Api;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import ir.parkban.R;
import ir.parkban.car.adapter.CarsListAdapter;
import ir.parkban.car.model.Car;
import ir.parkban.general.activities.BaseActivity;
import ir.parkban.general.activities.MainActivity;
import ir.parkban.general.customView.CustomButtonView;
import ir.parkban.general.customView.CustomEditText;
import ir.parkban.general.customView.CustomTextView;
import ir.parkban.general.storage.PreferenceManager;
import ir.parkban.general.utils.Constants;
import ir.parkban.general.utils.CustomToast;
import ir.parkban.general.webservice.ApiMethodCaller;
import ir.parkban.general.webservice.models.Response;
import ir.parkban.login.LoginActivity;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Payam on 11/14/2017.
 */

public class AddCarActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.txtUserName)
    CustomTextView txtUsername;


    @BindView(R.id.et_carname)
    CustomEditText et_carName;


    @BindView(R.id.et_plateIRNumber)
    CustomEditText et_plateIRNumber;

    @BindView(R.id.et_platePart1Number)
    CustomEditText et_platePart1Number;

    @BindView(R.id.et_platePart2Number)
    CustomEditText et_platePart2Number;


    @BindView(R.id.btn_submitCar)
    CustomButtonView btn_submitCar;

    @BindView(R.id.color_spinner)
    MaterialSpinner colorSpinner;

    @BindView(R.id.type_spinner)
    MaterialSpinner typeSpinner;

    @BindView(R.id.chars_spinner)
    MaterialSpinner charsSpinner;


    private List<Car> carsList = new ArrayList<>();
    private CarsListAdapter mAdapter;

    private static String username;
    private static int selected_colorId;
    private static int selected_typeId;
    private static int selected_charId;
    static HashMap<Integer, String> colorMap;
    static HashMap<Integer, String> charMap;
    static HashMap<Integer, String> typeMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);
        btn_submitCar.setOnClickListener(this);
        setTitle(getString(R.string.add_cars));
        setActionButtonVisibility(View.GONE, View.GONE);
        setSpinnersData();
        setVisibilityOfSearchEditText(View.GONE);
        username = PreferenceManager.getInstance(this).getUsername();
        txtUsername.setText(username);
    }

    private void setSpinnersData() {

        colorMap = Constants.colorMap;
        String[] colorArray = new String[colorMap.size()];

        for (int i = 0; i < colorMap.size(); i++)
            colorArray[i] = colorMap.get(i).toString();

        colorSpinner.setItems(colorArray);
        colorSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                selected_colorId = position;
            }
        });

        charMap = Constants.charsMap;
        String[] charsArray = new String[charMap.size()];

        for (int i = 0; i < charMap.size(); i++)
            charsArray[i] = charMap.get(i).toString();

        charsSpinner.setItems(charsArray);
        charsSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                selected_charId = position;
            }
        });


        typeMap = Constants.typeMap;
        String[] typeArray = new String[typeMap.size()];

        for (int i = 0; i < typeMap.size(); i++)
            typeArray[i] = typeMap.get(i).toString();

        typeSpinner.setItems(typeArray);
        typeSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                selected_typeId = position;
            }
        });
    }

    public static Intent createIntent(Context context) {
        return new Intent(context, AddCarActivity.class);
    }


    @Override
    public void onClick(View v) {
        try {
            if (v.getId() == R.id.btn_submitCar) {
                ApiMethodCaller.getInstance(v.getContext()).AddCar(username, String.valueOf(selected_typeId)
                        , et_carName.getText().toString(), String.valueOf(selected_colorId),
                        et_plateIRNumber.getText().toString() + et_platePart1Number.getText().toString()
                                + selected_charId + et_platePart2Number.getText().toString(), new Callback<Response>() {
                            @Override
                            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                                if (response.isSuccessful()) {
                                    String msg = response.body().getMessage();
                                    String resultCode = response.body().getReturnCode();
                                    String token = response.body().getToken();
                                    PreferenceManager.getInstance(
                                            getApplicationContext()).setToken(token);
                                    Object data = response.body().getData();
                                    if (resultCode.equals("0")) {
                                        CustomToast.showShort(getString(R.string.car_added_successfully));
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<ir.parkban.general.webservice.models.Response> call, Throwable t) {
                                CustomToast.showShort(getString(R.string.retrofit_failure));
                            }
                        });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}