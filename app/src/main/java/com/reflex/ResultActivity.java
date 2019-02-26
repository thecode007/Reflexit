package com.reflex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        TextView textStatement = findViewById(R.id.text_statement);
        Bundle extras = getIntent().getExtras();
        String appName = extras.getString("app");
        String actionName = extras.getString("action");
        String trigger = extras.getString("trigger");

        textStatement.setText("If " + appName + " " + trigger + " then " + actionName);
    }
}
