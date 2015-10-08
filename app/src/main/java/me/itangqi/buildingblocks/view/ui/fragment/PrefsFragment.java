package me.itangqi.buildingblocks.view.ui.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.design.widget.Snackbar;

import java.io.File;

import me.itangqi.buildingblocks.R;
import me.itangqi.buildingblocks.domain.utils.Constants;
import me.itangqi.buildingblocks.domain.utils.PrefUtils;
import me.itangqi.buildingblocks.domain.utils.ToastUtils;
import me.itangqi.buildingblocks.domain.utils.VersionUtils;
import me.itangqi.buildingblocks.presenters.WebActivityPresenter;

public class PrefsFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {

    private CheckBoxPreference mIsEnableCache;
    private CheckBoxPreference mIsAutoUpdate;
    private Preference mCachePre, mVersionPre, mLogPref;

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
            editor.putBoolean(PrefUtils.PRE_CACHE_ENABLE, mIsEnableCache.isChecked());
        } else if (mCachePre == preference) {
            WebActivityPresenter presenter = new WebActivityPresenter();
            long deletedSize = presenter.clearCacheFolder();
            Snackbar.make(getView(), "释放了 " + (deletedSize / 1024L / 1024L) + " MB", Snackbar.LENGTH_LONG).show();
        } else if (mIsAutoUpdate == preference) {
            editor.putBoolean(PrefUtils.PRE_AUTO_UPDATE, mIsAutoUpdate.isChecked());
        } else if (mLogPref == preference) {
            String logUri = PrefUtils.getCrashUri();
            if (logUri != null && isCrashLogExit()) {
                Uri uri = Uri.parse(logUri);
                Intent sendTo = new Intent(Intent.ACTION_SEND);
                String[] developers = new String[]{"imtangqi@gmail.com", "troyliu0105@outlook.com"};
                sendTo.putExtra(Intent.EXTRA_EMAIL, developers);
                sendTo.putExtra(Intent.EXTRA_SUBJECT, "BuildingBlocks崩溃日志");
                sendTo.putExtra(Intent.EXTRA_TEXT, "欢迎吐槽:\n");
                sendTo.putExtra(Intent.EXTRA_STREAM, uri);
                sendTo.setType("text/plain");
                startActivity(Intent.createChooser(sendTo, "请选择邮件客户端"));
            } else {
                ToastUtils.showShort("我现在好着呢~");
            }
        }
        return true;
    }

    private void initPrefs() {
        mIsEnableCache = (CheckBoxPreference) findPreference("enable_cache");
        mIsAutoUpdate = (CheckBoxPreference) findPreference("auto_update");
        mCachePre = findPreference("delete_cache");
        mCachePre.setOnPreferenceClickListener(this);
        mLogPref = findPreference("send_log");
        mLogPref.setOnPreferenceClickListener(this);
        mVersionPre = findPreference("version");
        mVersionPre.setTitle("版本：" + VersionUtils.setUpVersionName(getActivity()));
    }

    public static boolean isCrashLogExit() {
        try {
            File crash = new File(Constants.LOG_DIR + File.separator + Constants.LOG_NAME);
            if (!crash.exists()) {
                return false;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return true;
    }
}
