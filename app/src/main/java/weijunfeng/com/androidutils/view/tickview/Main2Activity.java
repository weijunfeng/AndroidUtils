package weijunfeng.com.androidutils.view.tickview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import weijunfeng.com.androidutils.R;

public class Main2Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tickview);


        final TickView tickView = (TickView) findViewById(R.id.tick_view);
        final TickView tickViewAccent = (TickView) findViewById(R.id.tick_view_accent);
        tickView.setOnCheckedChangeListener(new TickView.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(TickView tickView, boolean isCheck) {
                Toast.makeText(Main2Activity.this, isCheck + "", Toast.LENGTH_SHORT).show();
            }
        });
        tickViewAccent.setOnCheckedChangeListener(new TickView.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(TickView tickView, boolean isCheck) {
                Toast.makeText(Main2Activity.this, isCheck + "", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.check_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tickView.setChecked(true);
                tickViewAccent.setChecked(true);
            }
        });
        findViewById(R.id.uncheck_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tickView.setChecked(false);
                tickViewAccent.setChecked(false);
            }
        });
    }
}
