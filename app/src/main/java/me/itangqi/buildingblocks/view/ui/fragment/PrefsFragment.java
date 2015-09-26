package me.itangqi.buildingblocks.view.ui.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.design.widget.Snackbar;

import me.itangqi.buildingblocks.R;
import me.itangqi.buildingblocks.domin.utils.VersionUtils;
import me.itangqi.buildingblocks.presenters.WebActivityPresenter;


public class PrefsFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {

    private CheckBoxPreference mIsEnableCache;
    private Preference mCachePre, mVersionPre;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.prefs);

        initPrefs();
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        // TODO the rest over to you :)
        SharedPreferences.Editor editor = preference.getEditor();
        if (mIsEnableCache == preference) {
            editor.putBoolean("enable_cache", mIsEnableCache.isChecked());
        } else if (mCachePre == preference) {
            WebActivityPresenter presenter = WebActivityPresenter.newInstance();
            long deletedSize = presenter.clearCacheFolder();
//            ToastUtils.showShort("释放了" + (deletedSize / 1024L / 1024L) + "MB");
            Snackbar.make(getView(), "释放了 " + (deletedSize / 1024L / 1024L) + " MB", Snackbar.LENGTH_LONG).show();

        }
        return true;
    }

    private void initPrefs() {
        mIsEnableCache = (CheckBoxPreference) findPreference("enable_cache");
        mCachePre = findPreference("delete_cache");
        mCachePre.setOnPreferenceClickListener(this);
        mVersionPre = findPreference("version");
        mVersionPre.setTitle("版本：" + VersionUtils.setUpVersionName(getActivity()));
    }
}
