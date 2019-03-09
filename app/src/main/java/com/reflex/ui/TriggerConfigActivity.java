package com.reflex.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.reflex.R;
import com.reflex.core.model.App;
import com.reflex.core.model.Trigger;
import com.reflex.services.AppProvider;

import java.util.ArrayList;

public class TriggerConfigActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trigger_config);

        ArrayList<EditText> editTexts = new ArrayList<>();
        LinearLayout layoutConstraints = findViewById(R.id.layout_constraints);
        Bundle extras = getIntent().getExtras();
        String triggerString = extras.getString("triggerString", "trigger");
        String appName = extras.getString("app");
        String action = extras.getString("action");

        App app = AppProvider.getInstance()
                .getApp(appName);
        Trigger trigger = app.getTrigger(triggerString);
        for (String field : trigger.getFields()) {
            EditText editText = new EditText(this);
            editText.setHint(field);
            int colorResource = getResources().getColor(R.color.colorWhite);
            editText.setHintTextColor(colorResource);
            editText.setTextColor(colorResource);
            layoutConstraints.addView(editText);
            if (field.contains("Number")) {
                editText.setInputType(InputType.TYPE_CLASS_PHONE);
                editTexts.add(editText);
                continue;
            }

            editText.setInputType(InputType.TYPE_CLASS_TEXT);
            editTexts.add(editText);
        }

        Button btnSubmit = findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(view -> {
            Intent intent = new Intent(this, ActionAppsActivity.class);
            intent.putExtra("app", appName);
            intent.putExtra("trigger", trigger.getTriggerName());
            intent.putExtra("triggerString", triggerString);
            intent.putExtra("action", action);
            ObjectNode node = JsonNodeFactory.instance.objectNode();
            for (EditText editText : editTexts) {
                node.put(editText.getHint().toString().toLowerCase(), editText.getText().toString());
            }
            intent.putExtra("constraints", node.toString());
            startActivity(intent);
        });
    }
}
