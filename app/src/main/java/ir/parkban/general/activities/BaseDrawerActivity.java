package ir.parkban.general.activities;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.appcompat.widget.LinearLayoutManager;
import androidx.appcompat.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.BindView;
import ir.parkban.R;
import ir.parkban.car.activity.CarsActivity;
import ir.parkban.general.adapters.SideMenuAdapter;
import ir.parkban.general.classes.FullDrawerLayout;
import ir.parkban.general.classes.RecyclerItemClickListener;
import ir.parkban.general.customView.CustomTextView;
import ir.parkban.general.storage.PreferenceManager;
import ir.parkban.profile.ProfileActivity;
import ir.parkban.setting.SettingActivity;

public class BaseDrawerActivity extends BaseActivity
        implements RecyclerItemClickListener.OnItemClickListener {

    @BindView(R.id.txtUserName)
    protected CustomTextView txtUserName;

    @BindView(R.id.txtMenuCredit)
    protected CustomTextView txtCredit;

    @BindView(R.id.imgUser)
    protected ImageView imgUserImage;

    @BindView(R.id.imgMenu)
    protected ImageView imgMenu;

    @BindView(R.id.imgv_closeDrawer)
    protected ImageView imgv_closeDrawer;

    @BindView(R.id.drawer)
    protected
    FullDrawerLayout drawer;

    @BindView(R.id.rclSideMenu)
    protected RecyclerView rclSideMenu;

    @BindView(R.id.rel_user_name)
    protected LinearLayout relUserInfo;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (drawer.isDrawerOpen(GravityCompat.END)) {
                            drawer.closeDrawer(GravityCompat.END);
                        } else {
                            drawer.openDrawer(GravityCompat.END);
                        }

                    }
                });
            }
        });

        imgv_closeDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                }
            }
        });

        // rclSideMenu = (RecyclerView) findViewById(R.id.rclSideMenu);
        rclSideMenu.setLayoutManager(new LinearLayoutManager(this));
        rclSideMenu.setAdapter(new SideMenuAdapter());

        rclSideMenu.setNestedScrollingEnabled(false);
        rclSideMenu.addOnItemTouchListener(new RecyclerItemClickListener(this, this));

        relUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(ProfileActivity.createIntent(BaseDrawerActivity.this));
            }
        });
        final String userName = PreferenceManager.getInstance(this).getUsername();
        txtUserName.setText(userName);
    }
    @Override
    public void onItemClick(View view, int position) {

        if (position == 0) {
            startActivity(CarsActivity.createIntent(BaseDrawerActivity.this));
        }
        else if (position == 1) {
         //   startActivity(WalletActivity.createIntent(BaseDrawerActivity.this));
        }

        else if (position == 2) {
        //    startActivity(ParkedCarsActivity.createIntent(BaseDrawerActivity.this));
        }

        else if(position==3)
        {
         //   startActivity(ManageParkingsActivity.createIntent(BaseDrawerActivity.this));
        }


        else if(position==4)
        {
            //transactions
            //     startActivity(ManageParkingsActivity.createIntent(BaseDrawerActivity.this));
        }


        else if (position == 5) {
          //  startActivity(TermsActivity.createIntent(BaseDrawerActivity.this));
        }
    /*    else if (position == 7) {
            startActivity(PrivacyActivity.createIntent(BaseDrawerActivity.this));
        }*/
        else if (position == 6) {
            startActivity(SettingActivity.createIntent(BaseDrawerActivity.this));

        }
       /* else if (position == 9) {
            BacktoryUser.logoutInBackground();
            finish();
        }*/

        drawer.closeDrawer(GravityCompat.END);


    }
    public  androidx.appcompat.widget.Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    public void onItemLongPress(View v, int position) {

    }

}
