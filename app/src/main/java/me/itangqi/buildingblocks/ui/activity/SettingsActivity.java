package me.itangqi.buildingblocks.ui.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.kenumir.materialsettings.MaterialSettings;
import com.kenumir.materialsettings.items.CheckboxItem;
import com.kenumir.materialsettings.items.DividerItem;
import com.kenumir.materialsettings.items.HeaderItem;
import com.kenumir.materialsettings.items.SwitcherItem;
import com.kenumir.materialsettings.items.TextItem;
import com.kenumir.materialsettings.storage.PreferencesStorageInterface;
import com.kenumir.materialsettings.storage.StorageInterface;

/**
 * Created by tangqi on 7/20/15.
 */
public class SettingsActivity extends MaterialSettings {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addItem(new HeaderItem(this).setTitle("Sample title 1"));
        addItem(new CheckboxItem(this, "key1").setTitle("Checkbox item 1").setSubtitle("Subtitle text 1").setOnCheckedChangeListener(new CheckboxItem.OnCheckedChangeListener() {
            @Override
            public void onCheckedChange(CheckboxItem cbi, boolean isChecked) {
                Toast.makeText(SettingsActivity.this, "CHECKED: " + isChecked, Toast.LENGTH_SHORT).show();
            }
        }));
        addItem(new DividerItem(this));
        addItem(new SwitcherItem(this, "key1a").setTitle("Switcher item 3 - no subtitle"));
        addItem(new DividerItem(this));
        addItem(new SwitcherItem(this, "key1b").setTitle("Switcher item 3").setSubtitle("With subtitle"));
        addItem(new DividerItem(this));
        addItem(new CheckboxItem(this, "key2").setTitle("Checkbox item 2").setSubtitle("Subtitle text 2 with long text and more txt and more and more ;-)").setDefaultValue(true));
        addItem(new DividerItem(this));
        addItem(new CheckboxItem(this, "key1c").setTitle("Checkbox item 3 - no subtitle"));
        addItem(new DividerItem(this));
        addItem(new TextItem(this, "key3").setTitle("Simple text item 1").setSubtitle("Subtitle of simple text item 1").setOnclick(new TextItem.OnClickListener() {
            @Override
            public void onClick(TextItem v) {
                Toast.makeText(SettingsActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
            }
        }));
        addItem(new HeaderItem(this).setTitle("Sample title 2"));
        addItem(new TextItem(this, "key4").setTitle("Simple text item 2").setSubtitle("Subtitle of simple text item 2").setOnclick(new TextItem.OnClickListener() {
            @Override
            public void onClick(TextItem v) {
                Toast.makeText(SettingsActivity.this, "Clicked 2", Toast.LENGTH_SHORT).show();
            }
        }));
        addItem(new DividerItem(this));
        addItem(new TextItem(this, "key5").setTitle("Simple text item 3 - no subtitle"));
        addItem(new DividerItem(this));
        addItem(new DividerItem(this));
        addItem(new HeaderItem(this).setTitle("Same usage with dialogs"));
        addItem(new TextItem(this, "key6").setTitle("Simple message dialog").setSubtitle("Clck to show message and change subtext").setOnclick(new TextItem.OnClickListener() {
            @Override
            public void onClick(TextItem item) {
            }
        }));
    }

    @Override
    public StorageInterface initStorageInterface() {
        return new PreferencesStorageInterface(this);
    }
}
