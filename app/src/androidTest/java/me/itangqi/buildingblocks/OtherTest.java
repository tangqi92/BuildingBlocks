package me.itangqi.buildingblocks;

import android.test.InstrumentationTestCase;

import me.itangqi.buildingblocks.domain.utils.VersionUtils;

/**
 * Created by Troy on 2015/10/3.
 */
public class OtherTest extends InstrumentationTestCase {

    public void testVersionCode() {
       int versionCode = VersionUtils.getVerisonCode();
        assertEquals(83, versionCode);
    }

}
