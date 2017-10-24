package weijunfeng.com.androidutils.view.editline;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import weijunfeng.com.androidutils.R;

public class EditLineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editline);
        findViewById(R.id.edte).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                findViewById(R.id.line).setSelected(hasFocus);
            }
        });
        findViewById(R.id.edte2).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                findViewById(R.id.line2).setSelected(hasFocus);
            }
        });
    }
}
