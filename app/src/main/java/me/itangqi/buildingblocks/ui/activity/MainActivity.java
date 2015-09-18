package me.itangqi.buildingblocks.ui.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.text.DateFormat;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.itangqi.buildingblocks.R;
import me.itangqi.buildingblocks.ui.activity.base.BaseActivity;
import me.itangqi.buildingblocks.ui.fragment.NewsListFragment;
import me.itangqi.buildingblocks.utils.Constants;
import me.itangqi.buildingblocks.utils.NetworkUtils;

public class MainActivity extends BaseActivity {

    @Bind(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @Bind(R.id.coordinatorLayout) CoordinatorLayout mContainer;
    @Bind(R.id.navigation_view) NavigationView mNavigationView;
    @Bind(R.id.tabs) TabLayout mTabLayout;
    @Bind(R.id.pager) ViewPager mViewPager;
    @Bind(R.id.toolbar) Toolbar mToolbar;

    @OnClick(R.id.fab)
    public void fabOnClick() {
        Snackbar.make(mContainer, R.string.snack_rest_over_to_you, Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        layoutResID = R.layout.activity_main;
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        if (!NetworkUtils.isNetworkConnected(this)) {
            Snackbar.make(mContainer, R.string.snack_network_error, Snackbar.LENGTH_LONG).show();
        }

        if (mNavigationView != null) {
            setupDrawerContent();
        }

        setupActionBarToggle();

        if (mViewPager != null) {
            setupViewPager();
        }
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

    private class MyPagerAdapter extends FragmentStatePagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Bundle bundle = new Bundle();
            Fragment newFragment = new NewsListFragment();

            Calendar dateToGetUrl = Calendar.getInstance();
            dateToGetUrl.add(Calendar.DAY_OF_YEAR, 1 - i);
            String date = Constants.simpleDateFormat.format(dateToGetUrl.getTime());

            bundle.putBoolean("first_page?", i == 0);
            bundle.putBoolean("single?", false);
            bundle.putString("date", date);

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
}
