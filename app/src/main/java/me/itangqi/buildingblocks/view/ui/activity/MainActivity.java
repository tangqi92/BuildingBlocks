package me.itangqi.buildingblocks.view.ui.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.itangqi.buildingblocks.R;
import me.itangqi.buildingblocks.domain.receiver.UpdaterReceiver;
import me.itangqi.buildingblocks.domain.service.Updater;
import me.itangqi.buildingblocks.domain.utils.Constants;
import me.itangqi.buildingblocks.domain.utils.PrefUtils;
import me.itangqi.buildingblocks.domain.utils.VersionUtils;
import me.itangqi.buildingblocks.presenters.MainActivityPresenter;
import me.itangqi.buildingblocks.view.IMainActivity;
import me.itangqi.buildingblocks.view.ui.activity.base.BaseActivity;
import me.itangqi.buildingblocks.view.ui.fragment.NewsListFragment;

public class MainActivity extends BaseActivity implements IMainActivity {

    public static final String TAG = "MainActivity";

    @Bind(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @Bind(R.id.coordinatorLayout) CoordinatorLayout mContainer;
    @Bind(R.id.navigation_view) NavigationView mNavigationView;
    @Bind(R.id.tabs) TabLayout mTabLayout;
    @Bind(R.id.pager) ViewPager mViewPager;
    @Bind(R.id.toolbar) Toolbar mToolbar;

    private MainActivityPresenter mPresenter;
    private UpdaterReceiver mUpdaterReceiver;
    private static boolean isLightTheme = true;

    @OnClick(R.id.fab)
    public void fabOnClick() {
        FragmentStatePagerAdapter adapter = (FragmentStatePagerAdapter) mViewPager.getAdapter();
        NewsListFragment fragment = (NewsListFragment) adapter.instantiateItem(mViewPager, mViewPager.getCurrentItem());
        fragment.onRefresh();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (isLightTheme)
            setTheme(R.style.Theme_AppTheme_Light);
        else
            setTheme(R.style.Theme_AppTheme_Dark);
        layoutResID = R.layout.activity_main;
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        if (mNavigationView != null) {
            setupDrawerContent();
        }

        setupActionBarToggle();

        if (mViewPager != null) {
            setupViewPager();
        }
        mPresenter = new MainActivityPresenter(this);
        mPresenter.clearCache();
        mPresenter.checkUpdate();
        IntentFilter filter = new IntentFilter(Constants.BROADCAST_UPDATE_ACTION);
        filter.addCategory(Constants.BROADCAST_UPDATE_CATEGORY);
        mUpdaterReceiver = new UpdaterReceiver(this);
        LocalBroadcastManager.getInstance(this).registerReceiver(mUpdaterReceiver, filter);
    }

    private void setupDrawerContent() {
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        //Checking if the item is in checked state or not, if not make it in checked state
                        if (menuItem.isChecked()) menuItem.setChecked(false);
                        else menuItem.setChecked(true);
                        //Closing drawer on item click
                        mDrawerLayout.closeDrawers();
                        //Check to see which item was being clicked and perform appropriate action
                        switch (menuItem.getItemId()) {
                            case R.id.nav_pick_photo:
                                return prepareIntent(PickPhotoActivity.class);
                            case R.id.nav_pick_place:
                                return prepareIntent(GooglePlacesActivity.class);
                            case R.id.nav_settings:
                                return prepareIntent(PrefsActivity.class);
                            case R.id.nav_about:
                                return prepareIntent(AboutActivity.class);
                            default:
                                return true;
                        }
                    }
                });
    }

    private void setupActionBarToggle() {
        // Initializing Drawer Layout and ActionBarToggle
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.openDrawerContentDescRes, R.string.closeDrawerContentDescRes) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    private void setupViewPager() {
        mViewPager.setOffscreenPageLimit(Constants.PAGE_COUNT);
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setupSearchView(Menu menu) {
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchMenuItem
                .getActionView();
        searchView.setIconifiedByDefault(true);
        if (searchManager != null && searchView != null) {
            searchView.setSearchableInfo(searchManager
                    .getSearchableInfo(getComponentName()));

            searchView
                    .setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {

                            if (!hasFocus) {
                                if (searchMenuItem != null) {
                                    searchMenuItem.collapseActionView();
                                }// end if
                                if (searchView != null) {
                                    searchView.onActionViewCollapsed();
                                }// end if
                            }// end if
                        }
                    });

            searchView
                    .setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            /**
                             * hides and then unhides search tab to make sure
                             * keyboard disappears when query is submitted
                             */
                            if (searchView != null) {
                                searchView.setVisibility(View.INVISIBLE);
                                searchView.setVisibility(View.VISIBLE);
                            }
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            // TODO Auto-generated method stub
                            return false;
                        }
                    });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        setupSearchView(menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.men_action_read_mode:
                if (isLightTheme)
                    isLightTheme = false;
                else isLightTheme = true;
                SharedPreferences.Editor editor = PrefUtils.getSharedPreferences().edit();
                editor.putBoolean("light_or_dark", isLightTheme);
                Intent i = this.getBaseContext().getPackageManager().getLaunchIntentForPackage(this.getBaseContext().getPackageName());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                return true;
            case R.id.menu_action_feedback:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri data = Uri.parse("mailto:imtangqi@gmail.com");
                intent.setData(data);
                startActivity(intent);
                return true;
            default:
                Snackbar.make(mContainer, R.string.snack_rest_over_to_you, Snackbar.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean prepareIntent(Class clazz) {
        startActivity(new Intent(MainActivity.this, clazz));
        return true;
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
                Snackbar.make(mContainer, R.string.snack_exit_once_more, Snackbar.LENGTH_LONG).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void showSnackBar(String data, int time) {
        Snackbar.make(mContainer, data, time).show();
    }

    @Override
    public void showUpdate(final int versionCode, String versionName, final String apkUrl, List<String> disc) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("发现新版本")
                        .setIcon(R.drawable.icon)
                        .setMessage("当前版本号为：" + VersionUtils.getVerisonCode() + "\n" + "新版本号为：" + versionCode)
                        .setPositiveButton("下载", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(MainActivity.this, Updater.class);
                                intent.putExtra("url", apkUrl);
                                startService(intent);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
            }
        });
    }

    private class MyPagerAdapter extends FragmentStatePagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Bundle bundle = new Bundle();
            Fragment newFragment = NewsListFragment.newInstance();

            Calendar dateToGetUrl = Calendar.getInstance();
            dateToGetUrl.add(Calendar.DAY_OF_YEAR, 1 - i);
            int date = Integer.parseInt(Constants.simpleDateFormat.format(dateToGetUrl.getTime()));

            bundle.putBoolean("first_page?", i == 0);
            bundle.putBoolean("single?", false);
            bundle.putInt("date", date);

            newFragment.setArguments(bundle);
            return newFragment;
        }

        @Override
        public int getCount() {
            return Constants.PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Calendar displayDate = Calendar.getInstance();
            displayDate.add(Calendar.DAY_OF_YEAR, -position);

            return DateFormat.getDateInstance().format(displayDate.getTime());
        }
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mUpdaterReceiver);
        super.onDestroy();
    }
}
