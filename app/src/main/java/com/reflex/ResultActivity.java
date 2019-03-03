package com.reflex;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.reflex.core.model.ActionBootstrap;
import com.reflex.core.model.App;
import com.reflex.database.DatabaseAccess;
import com.reflex.services.AppProvider;
import com.reflex.services.fileSystem.FileSystem;

import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static com.reflex.core.providers.ReflexProvider.READ_JSON_STREAM;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView textStatement = findViewById(R.id.text_statement);
        Bundle extras = getIntent().getExtras();
        assert extras != null;
        String appName = extras.getString("app");
        String targetApp = extras.getString("targetApp");
        String actionName = extras.getString("action");
        String trigger = extras.getString("trigger");
        String triggerString = extras.getString("triggerString");
        String constraints = extras.getString("constraints");
        Toast.makeText(this, constraints, Toast.LENGTH_LONG).show();

        String statement = "If " + appName + " " + trigger + " then " + targetApp +" will " + actionName;
        textStatement.setText(statement);
        Button btnCreate = findViewById(R.id.btn_create);
        btnCreate.setOnClickListener(view -> {
            ActionBootstrap actionBootstrap = null;

            try {
                actionBootstrap = new ActionBootstrap(targetApp, actionName,
                        new ObjectMapper().readTree(constraints),
                        "In case of emergency delete all important files by SMS");
                DatabaseAccess.addActionToTrigger(appName,triggerString, actionBootstrap);
                startActivity(new Intent(this, HomeActivity.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
