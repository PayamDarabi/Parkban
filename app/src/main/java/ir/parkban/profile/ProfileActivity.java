package ir.parkban.profile;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.gson.internal.LinkedTreeMap;

import butterknife.BindView;
import ir.parkban.R;
import ir.parkban.general.activities.BaseActivity;
import ir.parkban.general.customView.CustomTextView;
import ir.parkban.general.storage.PreferenceManager;
import ir.parkban.general.utils.CustomToast;
import ir.parkban.general.webservice.ApiMethodCaller;
import ir.parkban.general.webservice.models.Profile;
import ir.parkban.general.webservice.models.Response;
import retrofit2.Call;
import retrofit2.Callback;

public class ProfileActivity extends BaseActivity {

    @BindView(R.id.coordinateLayout_person)
    CoordinatorLayout coordinatorLayoutPerson;

    @BindView(R.id.rel_person)
    RelativeLayout relPerson;

    @BindView(R.id.fab_edit_profile)
    FloatingActionButton fabViewProfile;

    @BindView(R.id.txtUserName)
    CustomTextView txtUsername;

    @BindView(R.id.lin_user_info)
    LinearLayout linUserInfo;

    @BindView(R.id.txtNameFamily)
    CustomTextView txtFullName;

    @BindView(R.id.txtEmail)
    CustomTextView txtEmail;

    @BindView(R.id.txtAddres)
    CustomTextView txtAddres;


    @BindView(R.id.txtMelliCode)
    CustomTextView txtMelliCode;


    @BindView(R.id.progress)
    ProgressBar progressBar;

    @BindView(R.id.linContent)
    LinearLayout linContent;

    @BindView(R.id.img_person)
    ImageView img_person;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle(R.string.profile);
        setActionButtonVisibility(View.GONE, View.VISIBLE);

        final String userName = PreferenceManager.getInstance(this).getUsername();
        txtUsername.setText(txtUsername.getText() + " : " + userName);
        setProfile(userName);
        fabViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    DialogChangeProfile dialogChangeProfile = new DialogChangeProfile(
                            v.getContext(), userName);
                    dialogChangeProfile.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            setProfile(userName);
                        }
                    });
                    dialogChangeProfile.show();

                }
            }
        });
    }
    private void setProfile(final String user_id) {
        try {
            ApiMethodCaller.getInstance(this)
                    .GetProfile(user_id, new Callback<Response>() {
                        @Override
                        public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                            if (response.isSuccessful()) {
                                String msg = response.body().getMessage();
                                String resultCode = response.body().getReturnCode();
                                String token = response.body().getToken();
                                PreferenceManager.getInstance(
                                        getApplication()).setToken(token);
                                LinkedTreeMap<String, String> data = (LinkedTreeMap<String, String>) response.body().getData();

                                Profile profile = new Profile(data);

                                if (resultCode.equals("0")) {

                                    String email = getString(R.string.email) + " : ";
                                    String address = getString(R.string.address) + " : ";
                                    String name = getString(R.string.fullname) + " : ";
                                    String melliCode = getString(R.string.mellicode) + " : ";

                                    if (profile.getEmailAdress() == null) {
                                        txtEmail.setTextColor(getResources().getColor(R.color.red));
                                        email += getString(R.string.emtpy_field);
                                    }
                                    if (profile.getFullName() == null) {
                                        txtFullName.setTextColor(getResources().getColor(R.color.red));
                                        name += getString(R.string.emtpy_field);
                                    }
                                    if (profile.getMelliCode() == null) {
                                        txtMelliCode.setTextColor(getResources().getColor(R.color.red));
                                        melliCode += getString(R.string.emtpy_field);
                                    }
                                    if (profile.getPrimaryAddress() == null) {
                                        txtAddres.setTextColor(getResources().getColor(R.color.red));
                                        address += getString(R.string.emtpy_field);
                                    }

                                    txtEmail.setText(email);
                                    txtFullName.setText(name);
                                    txtMelliCode.setText(melliCode);
                                    txtAddres.setText(address);
                                }
                            } else {
                                CustomToast.showShort(response.body().getMessage());
                            }
                        }

                        @Override
                        public void onFailure(Call<ir.parkban.general.webservice.models.Response> call, Throwable t) {
                            CustomToast.showShort(getString(R.string.retrofit_failure));
                        }
                    });
        }
        catch (Exception e){
            e.printStackTrace();
        }
        setViewHeight();
    }

    private void setViewHeight() {


        try {
            if (fabViewProfile.getHeight() == 0) {
                fabViewProfile.post(new Runnable() {
                    @Override
                    public void run() {
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) coordinatorLayoutPerson.getLayoutParams();
                        params.height = relPerson.getHeight() + (fabViewProfile.getHeight() / 2);
                        coordinatorLayoutPerson.setLayoutParams(params);
                    }
                });

            } else {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) coordinatorLayoutPerson.getLayoutParams();
                params.height = relPerson.getHeight() + (fabViewProfile.getHeight() / 2);
                coordinatorLayoutPerson.setLayoutParams(params);
            }

        } catch (Exception e) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public static Intent createIntent(Context context) {
        return new Intent(context, ProfileActivity.class);
    }
}
