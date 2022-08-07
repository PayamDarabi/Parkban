package ir.parkban.general.activities;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

import ir.parkban.R;
import ir.parkban.general.customView.CustomTextView;

public class BaseActivity extends AppCompatActivity {

    @Nullable
    @BindView(R.id.toolbar)
    androidx.appcompat.widget.Toolbar toolbar;

    @Nullable
    @BindView(R.id.txtTitle)
    CustomTextView txtTitle;

    @Nullable
    @BindView(R.id.imgBack)
    ImageView imgBack;

    @Nullable
    @BindView(R.id.imgMenu)
    ImageView imgMenu;



    @Nullable
    @BindView(R.id.edtSearch)
    EditText edtSearch;

    @Nullable
    @BindView(R.id.linFakeSpace)
    LinearLayout linFakeSpace;

    public boolean isInSearchMode = false;

    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        try {
            injectView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void injectView() {
        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            toolbar.setContentInsetsAbsolute(0, 0);
            // txtTitle = (CustomTextView) findViewById(R.id.txtTitle);
            // imgBack = (ImageView) findViewById(R.id.imgBack);
            //imgMenu = (ImageView) findViewById(R.id.imgMenu);
            imgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });

        }
        // imgSearch = (ImageView) findViewById(R.id.imgSearch);
        //imgPopupMenu = (ImageView) findViewById(R.id.imgPopupMenu);
        // edtSearch = (EditText) findViewById(R.id.edtSearch);
        // linFakeSpace = (LinearLayout) findViewById(R.id.linFakeSpace);
    }

    @Override
    public void setTitle(CharSequence title) {
        txtTitle.setText(title);
    }


    protected void setVisibilityOfTitle(int visibility) {
        txtTitle.setVisibility(visibility);
    }


    protected void setActionButtonVisibility(int menuVisibility,
                                             int backVisibility) {
        imgBack.setVisibility(backVisibility);
        if (imgMenu != null)
            imgMenu.setVisibility(menuVisibility);

    }


    public void setVisibilityOfSearchEditText(int visibility) {

        edtSearch.setVisibility(visibility);
        //imgBack.setVisibility(View.VISIBLE);
        if (View.VISIBLE == visibility) {
            edtSearch.setText("");
            linFakeSpace.setVisibility(View.GONE);

            imgMenu.setVisibility(View.GONE);
            txtTitle.setVisibility(View.GONE);
        } else {


            linFakeSpace.setVisibility(View.VISIBLE);
            if (BaseActivity.this instanceof MainActivity)
                imgMenu.setVisibility(View.VISIBLE);
            txtTitle.setVisibility(View.VISIBLE);

        }
    }

    public void setTextWatcher(TextWatcher txtWatcher) {
        edtSearch.addTextChangedListener(txtWatcher);

    }


    public androidx.appcompat.widget.Toolbar getToolbar() {
        return toolbar;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void setTitle(int titleId) {
        txtTitle.setText(titleId);
    }

}