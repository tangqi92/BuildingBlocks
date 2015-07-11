package me.itangqi.testproj.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import me.itangqi.testproj.R;
import me.itangqi.testproj.ui.fragment.NewsListFragment;


/**
 *  客户端内置的专栏用户列表
 *
 * @author bxbxbai
 */
public class NewsListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_people);
        initToolBar();
        getSupportActionBar().setTitle(R.string.news_list);

        getSupportFragmentManager().beginTransaction().add(R.id.container,
                NewsListFragment.newInstance()).commit();
    }

    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, NewsListActivity.class));
    }
}
