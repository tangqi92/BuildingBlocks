package me.itangqi.buildingblocks.view.ui.activity;

import android.os.Bundle;

import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.itangqi.buildingblocks.R;
import me.itangqi.buildingblocks.domain.application.App;
import me.itangqi.buildingblocks.view.ui.activity.base.SwipeBackActivity;
import me.itangqi.buildingblocks.view.ui.fragment.PrefsFragment;

public class PrefsActivity extends SwipeBackActivity {
    private SwipeBackLayout mSwipeBackLayout;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_prefs;
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
        setTitle(getString(R.string.title_settings));
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_frame, new PrefsFragment())
                .commit();
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.removeActivity(this);
    }
}