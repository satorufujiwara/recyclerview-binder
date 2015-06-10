package jp.satorufujiwara.binder.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class BinderSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);
        FragmentActivityExt
                .setContentFragment(this, R.id.container, BinderSampleFragment.newInstance());
    }
}
