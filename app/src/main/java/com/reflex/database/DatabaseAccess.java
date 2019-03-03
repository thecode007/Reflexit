package com.reflex.database;

import android.os.Environment;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.reflex.core.model.ActionBootstrap;
import com.reflex.core.model.TriggerBootstrap;
import com.reflex.services.fileSystem.FileSystem;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.reflex.core.providers.ReflexProvider.READ_JSON_FILE;

public class DatabaseAccess {

    public static String database_url = Environment.getExternalStorageDirectory() + "/reflexIt/bootstrap_trigger.json";
    private static FileSystem fileSystem = FileSystem.getInstance();


    public static void addTrigger(String triggerUrl, TriggerBootstrap triggerBootstrap) throws IOException {
        JsonNode rootNode = JsonNodeFactory.instance.objectNode();
        fileSystem.execute(READ_JSON_FILE, database_url, rootNode);
        JsonNode resultNode = rootNode.get("result");
        JsonNode triggerNode = resultNode.get(triggerUrl);
        if (triggerNode != null){
            return;
        }
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.convertValue(triggerBootstrap, JsonNode.class);
        ((ObjectNode)resultNode).set(triggerUrl, node);
        mapper.writeValue(new File(database_url), resultNode);
    }


    public static void addActionToTrigger(String app,String triggerUrl, ActionBootstrap actionBootstrap) throws IOException {
        JsonNode rootNode = JsonNodeFactory.instance.objectNode();
        fileSystem.execute(READ_JSON_FILE, database_url, rootNode);
        JsonNode resultNode = rootNode.get("result");
        if (resultNode!= null && resultNode.has(triggerUrl)) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode triggerNode = resultNode.get(triggerUrl);
            TriggerBootstrap triggerBootstrap = mapper.readValue(triggerNode.toString(), TriggerBootstrap.class);
            triggerBootstrap.addAction(actionBootstrap);
            triggerNode = mapper.convertValue(triggerBootstrap, JsonNode.class);
            ((ObjectNode) resultNode).set(triggerUrl, triggerNode);
            mapper.writeValue(new File(database_url), resultNode);
            return;
        }
        ArrayList<ActionBootstrap> actionBootstraps = new ArrayList<>();
        actionBootstraps.add(actionBootstrap);
        TriggerBootstrap triggerBootstrap = new TriggerBootstrap(app, actionBootstraps);
        addTrigger(triggerUrl, triggerBootstrap);
    }

    public static void updateTrigger(TriggerBootstrap bootstrap) {
    }


    public static JsonNode getAllData() {
        JsonNode rootNode = new ObjectMapper().createObjectNode();
        fileSystem.execute(READ_JSON_FILE, database_url, rootNode);
        return rootNode;
    }

    public static void deleteAction(String triggerString, String action, JsonNode constraints) {
    }
}
