package app.test.intent.annotate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

// 练习
// 1. butterKnife 自定义实现
// 2. 仿 butterKnife，使用注解解析 intent 传递的参数信息
public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.tv_start_activity)
    private TextView tv_start_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InjectViewUtil.injectViews(this);

        tv_start_activity.setOnClickListener(v -> {

            // TODO: 1/7/22 传递参数，开启新界面
            final Intent intent = new Intent(MainActivity.this, SecondActivity.class);

            intent.putExtra("temp1", "result1");
            intent.putExtra("temp2", "result2");
            intent.putExtra("temp3", "result3");
            intent.putExtra("temp4", "result4");
            intent.putExtra("temp5", "result5");

            MainActivity.this.startActivity(intent);
        });
    }

}