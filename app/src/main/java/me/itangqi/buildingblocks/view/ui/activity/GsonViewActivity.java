package me.itangqi.buildingblocks.view.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import me.itangqi.buildingblocks.R;
import me.itangqi.buildingblocks.model.entity.DailyGson;
import me.itangqi.buildingblocks.view.IGsonNews;
import me.itangqi.buildingblocks.view.ui.activity.base.SwipeBackActivity;

/**
 * Created by Troy on 2015/9/24.
 */
public class GsonViewActivity extends SwipeBackActivity implements IGsonNews{





    @Override
    protected int getLayoutResource() {
        return R.layout.activity_gson_news;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void loadGson(DailyGson dailyGson) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
