package me.itangqi.buildingblocks.view.ui.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.itangqi.buildingblocks.R;
import me.itangqi.buildingblocks.domain.application.App;
import me.itangqi.buildingblocks.view.ui.activity.base.SwipeBackActivity;

public class SearchResultActivity extends SwipeBackActivity {
    private SwipeBackLayout mSwipeBackLayout;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_base;
    }

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.addActivity(this);
        ButterKnife.bind(this);
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        handleIntent(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            setTitle(getString(R.string.search_result_title) + " " +query);
            //use the query to search
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.removeActivity(this);
    }
}