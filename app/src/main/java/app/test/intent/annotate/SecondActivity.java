package app.test.intent.annotate;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @InjectIntent("temp1")
    public String temp1;

    @InjectIntent("")
    public String temp2;

    @InjectView(R.id.tv_temp1)
    private TextView tv_temp1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        InjectViewUtil.injectViews(this);
        InjectViewUtil.injectIntent(this);

        // 这里会输出 intent 中携带的  temp1 和 temp2 中携带的数据
        tv_temp1.setText(temp1 + " " + temp2);
        System.out.println(temp1);
        System.out.println(temp2);
    }

}