package me.itangqi.testproj.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import me.itangqi.testproj.R;

/**
 * Created by tangqi on 7/20/15.
 */
public class TestActivity extends Activity {
    @Bind(R.id.progress)
    CircularProgressBar progressBar;

    @OnClick({R.id.start, R.id.stop})
    void setVisible(View view) {
        switch (view.getId()) {
            case R.id.start:
                progressBar.setVisibility(View.VISIBLE);
                break;
            case R.id.stop:
                progressBar.setVisibility(View.INVISIBLE);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_test);
        ButterKnife.bind(this);
    }


}
