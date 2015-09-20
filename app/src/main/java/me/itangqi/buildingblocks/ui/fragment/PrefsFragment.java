package me.itangqi.buildingblocks.ui.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import me.itangqi.buildingblocks.R;


public class PrefsFragment extends PreferenceFragment
        implements Preference.OnPreferenceClickListener {

    private CheckBoxPreference mIsEnableCache;

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
        }
        return true;
    }

    private void initPrefs() {
        mIsEnableCache = (CheckBoxPreference) findPreference("enable_cache");
    }
}
