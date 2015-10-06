package me.itangqi.buildingblocks.view.ui.activity.base;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.Utils;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;
import me.itangqi.buildingblocks.R;
import me.itangqi.buildingblocks.domain.utils.ThemeUtils;

/**
 * 手势右划返回
 * Created by tangqi on 9/13/15.
 */
public abstract class SwipeBackActivity extends AppCompatActivity implements SwipeBackActivityBase {

    private SwipeBackActivityHelper mHelper;

    abstract protected int getLayoutResource();

    @Bind(R.id.app_bar_layout) AppBarLayout mAppBar;
    @Bind(R.id.toolbar) Toolbar mToolbar;

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeUtils.changeTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        ButterKnife.bind(this);

        initBar();

        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
    }

    public void initBar() {
        if (mToolbar == null || mAppBar == null) {
            throw new IllegalStateException("no toolbar");
        }

        setSupportActionBar(mToolbar);

        if (canBack()) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (Build.VERSION.SDK_INT >= 21) {
            mAppBar.setElevation(10.6f);
        }
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public boolean canBack() {
        return false;
    }
}
