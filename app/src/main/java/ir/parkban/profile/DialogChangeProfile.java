package ir.parkban.profile;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.gson.internal.LinkedTreeMap;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.parkban.R;
import ir.parkban.general.storage.PreferenceManager;
import ir.parkban.general.utils.CustomToast;
import ir.parkban.general.utils.InternetConnectionChecker;
import ir.parkban.general.webservice.ApiMethodCaller;
import ir.parkban.general.webservice.models.Profile;
import ir.parkban.general.webservice.models.Response;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Payam on 7/30/2017.
 */
public class DialogChangeProfile extends Dialog {

    @BindView(R.id.edt_fullname)
    EditText edt_fullname;

    @BindView(R.id.edt_address)
    EditText edt_address;

    @BindView(R.id.edt_email)
    EditText edt_email;

    @BindView(R.id.edt_mellicode)
    EditText edt_mellicode;


    @BindView(R.id.btnsubmitProfile)
    Button btnsubmitProfile;


    @BindView(R.id.progressBar)
    ProgressBar progressBar;

     Profile profile;
     Context context;
     String userName;

    public DialogChangeProfile(@NonNull Context context, String userName) {
        super(context);
        this.userName = userName;
        this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }
        setContentView(R.layout.dialog_edit_profile);

        ButterKnife.bind(this);

        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        int height = (int) (metrics.heightPixels * 0.90);
        getWindow().setLayout((int) (metrics.widthPixels * 0.90), height);

        fillProfileData();

        btnsubmitProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDateToServer();
            }
        });
    }

    private void fillProfileData() {
        try {
            ApiMethodCaller.getInstance(context)
                    .GetProfile(userName, new Callback<Response>() {
                        @Override
                        public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                            if (response.isSuccessful()) {
                                String msg = response.body().getMessage();
                                String resultCode = response.body().getReturnCode();
                                String token = response.body().getToken();
                                PreferenceManager.getInstance(
                                        context).setToken(token);
                                LinkedTreeMap<String, String> data =
                                        (LinkedTreeMap<String, String>)
                                                response.body().getData();

                                Profile profile = new Profile(data);

                                if (resultCode.equals("0")) {
                                    if (profile.getEmailAdress() != null)
                                        edt_email.setText(profile.getEmailAdress());

                                    if (profile.getFullName() != null)
                                        edt_fullname.setText(profile.getFullName());

                                    if (profile.getMelliCode() != null)
                                        edt_mellicode.setText(profile.getMelliCode());

                                    if (profile.getPrimaryAddress() != null)
                                        edt_address.setText(profile.getPrimaryAddress());
                                }
                            } else {
                                CustomToast.showShort(response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<ir.parkban.general.webservice.models.Response> call, Throwable t) {
                            CustomToast.showShort(context.getString(R.string.retrofit_failure));
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendDateToServer() {
        if (InternetConnectionChecker.internetConnect()) {
            btnsubmitProfile.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            try {
                HashMap<String, String> obj = new HashMap<>();
                try {
                    obj.put("username", userName);
                    obj.put("EmailAddress", edt_email.getText().toString());
                    obj.put("FullName", edt_fullname.getText().toString());
                    obj.put("MelliCode", edt_mellicode.getText().toString());
                    obj.put("PrimaryAddress", edt_address.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ApiMethodCaller.getInstance(context)
                        .EditProfile(obj, new Callback<Response>() {
                            @Override
                            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                                if (response.isSuccessful()) {
                                    String msg = response.body().getMessage();
                                    String resultCode = response.body().getReturnCode();
                                    String token = response.body().getToken();
                                    PreferenceManager.getInstance(
                                            context).setToken(token);
                                    if (resultCode.equals("0")) {
                                        CustomToast.show(msg);
                                        progressBar.setVisibility(View.GONE);
                                        dismiss();
                                    }
                                } else {
                                    CustomToast.showShort(response.body().getMessage());
                                }
                            }

                            @Override
                            public void onFailure(Call<ir.parkban.general.webservice.models.Response> call, Throwable t) {
                                CustomToast.showShort(context.getString(R.string.retrofit_failure));
                            }
                        });
            }
            catch (Exception e){
                e.printStackTrace();
            }

        } else {
            Toast.makeText(getContext(), getContext().getString(R.string.internet_Fail), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}