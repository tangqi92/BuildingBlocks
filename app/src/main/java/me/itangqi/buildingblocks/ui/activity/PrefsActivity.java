package me.itangqi.buildingblocks.ui.activity;

import android.os.Bundle;

import me.itangqi.buildingblocks.R;
import me.itangqi.buildingblocks.ui.activity.base.ToolbarActivity;
import me.itangqi.buildingblocks.ui.fragment.PrefsFragment;

public class PrefsActivity extends ToolbarActivity {
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_prefs;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("设置");
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_frame, new PrefsFragment())
                .commit();
    }
}