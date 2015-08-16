package me.itangqi.buildingblocks.ui.activity;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import de.greenrobot.event.EventBus;
import me.itangqi.buildingblocks.event.TestEvent;
import me.itangqi.buildingblocks.ui.fragment.UserListFragment;


/**
 * 客户端内置的专栏用户列表
 *
 * @author bxbxbai
 */
public class PeopleListActivity extends BaseActivity {

    @Override
    protected Fragment createFragment() {
        return UserListFragment.newInstance();
    }

    public void onEventMainThread(TestEvent event) {

        String msg = "onEventMainThread收到了消息：" + event.getMsg();
        Log.d("harvic", msg);
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
                Toast.makeText(getApplicationContext(), "再按一次后退键退出应用程序",
                        Toast.LENGTH_SHORT).show();
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
