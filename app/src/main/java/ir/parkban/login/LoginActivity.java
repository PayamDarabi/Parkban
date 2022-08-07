package ir.parkban.login;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.parkban.general.activities.MainActivity;
import ir.parkban.general.customView.CustomButtonView;
import ir.parkban.general.customView.CustomTextView;
import ir.parkban.R;
import ir.parkban.general.storage.PreferenceManager;
import ir.parkban.general.utils.CustomToast;
import ir.parkban.general.webservice.ApiMethodCaller;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.et_login_username)
    TextInputEditText et_userName;

    @BindView(R.id.et_login_password)
    TextInputEditText et_passwd;

    @BindView(R.id.btn_login)
    CustomButtonView btnLogin;

    @BindView(R.id.prg_login)
    AVLoadingIndicatorView prgrss_login;

    @BindView(R.id.lay_pass)
    LinearLayout lay_pass;

    @BindView(R.id.lay_username)
    LinearLayout lay_username;

    @BindView(R.id.txt_code)
    CustomTextView txt_code;

    static int btnLoginClick = -1;
    private String uName;
    private String passWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        prgrss_login.setVisibility(View.INVISIBLE);
      //  String token = PreferenceManager.getInstance(this).getToken();


        final TextWatcher txtwatcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==11)
                {
                    btnLogin.performClick();
                }
            }

            public void afterTextChanged(Editable s) {
            }
        };
        et_userName.addTextChangedListener(txtwatcher);
        et_userName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                btnLogin.performClick();
                return false;
            }
        });
        if (!PreferenceManager.getInstance(this).getIsSignedIn()) {
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean hasError = false;
                    btnLoginClick++;
                    if (btnLoginClick > 1)
                        btnLoginClick = 0;
                    View view = getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    if (btnLoginClick == 0) {

                        et_userName.setError(null);
                        uName = et_userName.getText().toString();
                        Random r = new Random();
                        int i1 = (r.nextInt(Integer.MAX_VALUE));
                        passWord = String.valueOf(i1);

                        if (TextUtils.isEmpty(uName)) {
                            et_userName.setError(getString(R.string.create_error_no_name));
                            et_userName.requestFocus();
                            hasError = true;
                            btnLogin.setEnabled(true);
                        }
                        uName = uName.trim();
                        if (!(uName.startsWith("09") && uName.length() == 11)) {
                            et_userName.setError(getString(R.string.mobile_number_invalid));
                            et_userName.requestFocus();
                            hasError = true;
                            btnLogin.setEnabled(true);
                        }
                    }
                    if (!hasError) {

                        if (btnLoginClick == 0) {
                            doRegister(uName);
                        } else {
                            doLogin(uName, et_passwd.getText().toString());
                        }
                    }
                }
            });
        }
        else{
            startActivity(MainActivity.createIntent(LoginActivity.this));
            finish();
        }
    }

    public void doRegister(final String userName) {
        try {
            ApiMethodCaller.getInstance(getApplicationContext())
                    .Register(userName, new Callback<ir.parkban.general.webservice.models.Response>() {
                        @Override
                        public void onResponse(Call<ir.parkban.general.webservice.models.Response> call, Response<ir.parkban.general.webservice.models.Response> response) {
                            if (response.isSuccessful()) {
                                String msg = response.body().getMessage();
                                String resultCode = response.body().getReturnCode();
                                String token = response.body().getToken();
                                PreferenceManager.getInstance(
                                        getApplicationContext()).setToken(token);
                                Object data = response.body().getData();
                                if (resultCode.equals("0")) {
                                    lay_username.setVisibility(View.GONE);
                                    lay_pass.setVisibility(View.VISIBLE);

                                    if (!passWord.isEmpty()) {
                                        txt_code.setText("111111");
                                        et_passwd.setText("111111");
                                      //  passWord = "";
                                        btnLogin.setEnabled(true);
                                    }
                                } else {
                                    CustomToast.showShort(resultCode + "-" + msg);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void doLogin(final String userName, final String passWord) {
        try {
            ApiMethodCaller.getInstance(getApplicationContext())
                    .Login(userName,passWord, new Callback<ir.parkban.general.webservice.models.Response>() {
                        @Override
                        public void onResponse(Call<ir.parkban.general.webservice.models.Response> call, Response<ir.parkban.general.webservice.models.Response> response) {
                            if (response.isSuccessful()) {
                                String msg = response.body().getMessage();
                                String resultCode = response.body().getReturnCode();
                                String token = response.body().getToken();
                                PreferenceManager.getInstance(
                                        getApplicationContext()).setToken(token);
                                Object data = response.body().getData();
                                if (resultCode.equals("0")) {

                                    PreferenceManager.getInstance(
                                            getApplicationContext()).setIsSignedIn(true);
                                    PreferenceManager.getInstance(getApplication()).setUsername(userName);
                                    startActivity(MainActivity.createIntent(LoginActivity.this));
                                    finish();

                                } else {
                                    CustomToast.showShort(resultCode + "-" + msg);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}