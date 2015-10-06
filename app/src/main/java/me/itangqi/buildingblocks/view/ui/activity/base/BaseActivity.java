package me.itangqi.buildingblocks.view.ui.activity.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.itangqi.buildingblocks.R;
import me.itangqi.buildingblocks.domain.utils.ThemeUtils;

public class BaseActivity extends AppCompatActivity {

    @Bind(R.id.toolbar) Toolbar mToolbar;
    protected int layoutResID = R.layout.activity_base;

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeUtils.changeTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(layoutResID);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
    }
}
