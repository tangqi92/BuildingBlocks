package me.itangqi.buildingblocks.presenters;

import java.util.Calendar;

import me.itangqi.buildingblocks.domin.utils.Constants;
import me.itangqi.buildingblocks.model.DailyModel;
import me.itangqi.buildingblocks.view.IMainActivity;

/**
 * Created by Troy on 2015/9/26.
 */
public class MainActivityPresenter {

    private DailyModel mDailyModel;
    private IMainActivity mMainActivity;

    public MainActivityPresenter(IMainActivity iMainActivity) {
        this.mMainActivity = iMainActivity;
        this.mDailyModel = DailyModel.newInstance();
    }

    public void clearCache() {
        Calendar calendar = Calendar.getInstance();
        int today = Integer.parseInt(Constants.simpleDateFormat.format(calendar.getTime()));
        int totalDeleted = mDailyModel.clearOutdateCache(today - 7);
        long deletedSize = mDailyModel.clearOutdatePhoto(today - 7);
        if (totalDeleted > 0) {
            mMainActivity.showSnackBar("清理了" + totalDeleted + "条过期数据；" + "图片" + (deletedSize / 1024) + "KB", 1500);
        }
    }

}
