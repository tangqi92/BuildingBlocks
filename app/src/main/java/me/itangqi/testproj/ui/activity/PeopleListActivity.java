package me.itangqi.testproj.ui.activity;

import android.support.v4.app.Fragment;

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

}
