package me.itangqi.testproj.ui.activity;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.balysv.materialmenu.extras.toolbar.MaterialMenuIconToolbar;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import butterknife.ButterKnife;
import me.itangqi.testproj.R;

/**
 * Created by baia on 15/3/14.
 *
 * @author bxbxbai
 */
public abstract class BaseActivity extends ActionBarActivity {

    protected abstract Fragment createFragment();

    protected Toolbar toolbar;
    protected MaterialMenuIconToolbar materialMenu;
    protected SystemBarTintManager mTintManager;

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mTintManager = new SystemBarTintManager(this);

        mTintManager.setStatusBarTintEnabled(true);
//        mTintManager.setNavigationBarTintEnabled(true);

        mTintManager.setTintColor(getResources().getColor(R.color.primary));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment =fm.findFragmentById(R.id.id_fragment_container);

        if(fragment == null )
        {
            fragment = createFragment() ;

            fm.beginTransaction().add(R.id.id_fragment_container,fragment).commit();
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void setTranslucentStatus(boolean on) {
        Window w = getWindow();
        WindowManager.LayoutParams params = w.getAttributes();

        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;

        if (on) {
            params.flags |= bits;
        } else {
            params.flags &= ~bits;
        }
        w.setAttributes(params);
    }

    protected void initToolBar() {
        toolbar = ButterKnife.findById(this, R.id.toolbar);
        setSupportActionBar(toolbar);

        materialMenu = new MaterialMenuIconToolbar(this, Color.WHITE, MaterialMenuDrawable.Stroke.REGULAR) {
            @Override
            public int getToolbarViewId() {
                return R.id.toolbar;
            }
        };
        materialMenu.setState(MaterialMenuDrawable.IconState.ARROW);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
