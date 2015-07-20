package me.itangqi.testproj.ui.activity;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import de.greenrobot.event.EventBus;
import me.itangqi.testproj.event.TestEvent;
import me.itangqi.testproj.ui.fragment.PeopleListFragment;


/**
 * 客户端内置的专栏用户列表
 *
 * @author bxbxbai
 */
public class PeopleListActivity extends BaseActivity {

    private PeopleListFragment mPeopleListFragment;
    @Override
    protected Fragment createFragment() {
        return PeopleListFragment.newInstance();
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

}
