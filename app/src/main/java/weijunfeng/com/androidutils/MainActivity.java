package weijunfeng.com.androidutils;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import weijunfeng.com.androidutils.view.editline.EditLineActivity;
import weijunfeng.com.androidutils.view.tickview.Main2Activity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.editline).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EditLineActivity.class));
            }
        });
        findViewById(R.id.tickview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
            }
        });
    }

    public void onclick1(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri uri = Uri.parse("tel:13333333333");
        intent.setData(uri);
        startActivity(intent);
    }

    public void onclick2(View view) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri uri = Uri.parse("tel:13333333333");
        intent.setData(uri);
        startActivity(intent);
    }

}
