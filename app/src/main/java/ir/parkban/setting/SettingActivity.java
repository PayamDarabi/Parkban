package ir.parkban.setting;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.parkban.R;
import ir.parkban.general.activities.BaseActivity;
import ir.parkban.general.activities.MainActivity;
import ir.parkban.general.customView.CustomTextView;
import ir.parkban.general.storage.PreferenceManager;

public class SettingActivity extends BaseActivity {
    @BindView(R.id.swtch_showtraffic)
    Switch swtch_showtraffic;

    @BindView(R.id.txtlogOut)
    CustomTextView txtlogOut;

    @BindView(R.id.imgLogOut)
    ImageView imgLogOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setTitle(getString(R.string.settings));
        ButterKnife.bind(this);
        setActionButtonVisibility(View.GONE, View.GONE);
        setVisibilityOfSearchEditText(View.GONE);
        txtlogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferenceManager.getInstance(getApplicationContext()).setIsSignedIn(false);
                finish();
            }
        });
        imgLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferenceManager.getInstance(getApplicationContext()).setIsSignedIn(false);
                finish();
            }
        });
        swtch_showtraffic.setChecked(PreferenceManager.getInstance(this).isShowShowTraffc());
        swtch_showtraffic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                PreferenceManager.getInstance(getApplicationContext()).setShowTraffc(b);
                MainActivity.mMap.setTrafficEnabled(PreferenceManager.getInstance(getApplicationContext()).isShowShowTraffc());
            }
        });

    }

    public static Intent createIntent(Context context) {
        return new Intent(context, SettingActivity.class);
    }

}
