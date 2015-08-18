package me.itangqi.buildingblocks.ui.fragment;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;

import me.itangqi.buildingblocks.R;
import me.itangqi.buildingblocks.utils.Check;


public class PrefsFragment extends PreferenceFragment
        implements Preference.OnPreferenceClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.prefs);
        findPreference("about").setOnPreferenceClickListener(this);

        if (!Check.isZhihuClientInstalled()) {
            ((PreferenceCategory) findPreference("settings_settings"))
                    .removePreference(findPreference("using_client?"));
        }

        if (!PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getBoolean("enable_accelerate_server?", false)) {
            ((PreferenceScreen) findPreference("preference_screen"))
                    .removePreference(findPreference("settings_network_settings"));
        }
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (preference.getKey().equals("about")) {
            return true;
        }
        return false;
    }


}
