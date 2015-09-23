package me.itangqi.buildingblocks.view.ui.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.widget.Toast;

import me.itangqi.buildingblocks.R;
import me.itangqi.buildingblocks.domin.application.App;
import me.itangqi.buildingblocks.presenters.WebActivityPresenter;


public class PrefsFragment extends PreferenceFragment
        implements Preference.OnPreferenceClickListener {

    private CheckBoxPreference mIsEnableCache;
    private Preference mPreference;
    private ListPreference mListPreference;

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
        } else if (mPreference == preference) {
            WebActivityPresenter presenter = new WebActivityPresenter();
            long deletedSize = presenter.clearCacheFolder();
            Toast.makeText(App.getContext(), "释放了" + (deletedSize / 1024) + "KB", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private void initPrefs() {
        mIsEnableCache = (CheckBoxPreference) findPreference("enable_cache");
        mPreference = findPreference("delete_cache");
        mPreference.setOnPreferenceClickListener(this);
    }
}
