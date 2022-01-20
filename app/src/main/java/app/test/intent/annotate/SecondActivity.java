package app.test.intent.annotate;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @InjectIntent("temp1")
    public String temp1;

    @InjectIntent("")
    public String temp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        InjectViewUtil.injectIntent(this);

        System.out.println(temp1);
        System.out.println(temp2);
    }

}