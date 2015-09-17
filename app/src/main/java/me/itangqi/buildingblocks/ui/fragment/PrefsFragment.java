package me.itangqi.buildingblocks.ui.fragment;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import me.itangqi.buildingblocks.R;


public class PrefsFragment extends PreferenceFragment
        implements Preference.OnPreferenceClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.prefs);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        // TODO the rest over to you :)
        return false;
    }
}
