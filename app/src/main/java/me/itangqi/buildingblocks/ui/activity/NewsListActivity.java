package me.itangqi.buildingblocks.ui.activity;

import android.support.v4.app.Fragment;
import android.view.KeyEvent;

import me.itangqi.buildingblocks.R;
import me.itangqi.buildingblocks.ui.activity.base.BaseActivity;
import me.itangqi.buildingblocks.ui.fragment.NewsListFragment;
import me.itangqi.buildingblocks.utils.ToastUtils;

public class NewsListActivity extends BaseActivity {

    @Override
    protected Fragment createFragment() {
        return NewsListFragment.newInstance();
    }

    /**
     * 实现再按一次退出提醒
     */
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 3000) {
                ToastUtils.showShort(getString(R.string.exit_once_more));
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
