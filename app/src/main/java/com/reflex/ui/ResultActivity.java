package com.reflex.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reflex.R;
import com.reflex.core.model.ActionBootstrap;
import com.reflex.database.DatabaseAccess;

import java.io.IOException;

public class ResultActivity extends AppCompatActivity {

    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView textStatement = findViewById(R.id.text_statement);
        EditText textDescription = findViewById(R.id.text_description);
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

            if (TextUtils.isEmpty(textDescription.getText().toString())){
                Toast.makeText(getApplicationContext(), "Please Fill the description",Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                actionBootstrap = new ActionBootstrap(targetApp, actionName,
                        new ObjectMapper().readTree(constraints),
                        textDescription.getText().toString());
                actionBootstrap.setActive(true);
                DatabaseAccess.addActionToTrigger(appName,triggerString, actionBootstrap);
                startActivity(new Intent(this, HomeActivity.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
