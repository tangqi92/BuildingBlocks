package me.itangqi.buildingblocks;

import android.test.InstrumentationTestCase;

import java.util.Map;

import me.itangqi.buildingblocks.domain.utils.ThemeUtils;
import me.itangqi.buildingblocks.model.DailyModel;
import me.itangqi.buildingblocks.presenters.MainActivityPresenter;

/**
 * Created by Troy on 2015/10/2.
 */
public class ParserTest extends InstrumentationTestCase {

    public void testUpdate() {
        MainActivityPresenter presenter = new MainActivityPresenter(null);
    }

    public void testDarkHtml() {
        String url = "http://daily.zhihu.com/story/3892357";
        DailyModel model = DailyModel.newInstance();
        ThemeUtils.isLight = false;
        Map<String, String> dark = model.parseHtml(url);
        ThemeUtils.isLight = true;
        Map<String, String> light = model.parseHtml(url);
        assertEquals(dark.get("content").equals(light.get("content")), false);
    }

}
