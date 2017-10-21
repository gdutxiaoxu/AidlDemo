package xj.musicclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onButtonClick(View view){
        switch (view.getId()){
            case R.id.btn_easy_demo:
                break;
            case R.id.btn_async_demo:
                break;
            case R.id.btn_parceable_demo:
                jump(ParceableDemoActivity.class);
                break;
        }
    }

    public  <T extends Activity> void  jump(Class<T > tClass){
        Intent intent = new Intent(this, tClass);
        startActivity(intent);
    }
}
