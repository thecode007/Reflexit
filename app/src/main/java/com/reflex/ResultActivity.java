package com.reflex;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.reflex.core.model.App;
import com.reflex.services.AppProvider;
import com.reflex.services.fileSystem.FileSystem;

import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static com.reflex.core.providers.ReflexProvider.READ_JSON_STREAM;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView textStatement = findViewById(R.id.text_statement);
        Bundle extras = getIntent().getExtras();
        String appName = extras.getString("app");
        String targetApp = extras.getString("targetApp");
        String actionName = extras.getString("action");
        String trigger = extras.getString("trigger");
        String constraints = extras.getString("constraints");

        textStatement.setText("If " + appName + " " + trigger + " then " + targetApp +" will " + actionName);
        Button btnCreate = findViewById(R.id.btn_create);
        btnCreate.setOnClickListener(view -> {

        });
    }
}
