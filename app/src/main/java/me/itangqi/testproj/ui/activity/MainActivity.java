package me.itangqi.testproj.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.itangqi.testproj.R;


public class MainActivity extends BaseActivity {

@Bind(R.id.button)
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initToolBar();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewsListActivity.start(MainActivity.this);

            }
        });

    }


}
