package com.reflex.services.fileSystem;

import com.reflex.R;
import com.reflex.core.providers.App;

public class FileSystem extends App {

    public static FileSystem instance;
    public static FileSystem getInstance() {
        if (instance == null) {
            instance = new FileSystem();
        }
        return instance;
    }

    private FileSystem() {
        super(R.drawable.file_system,null, FileSystemReflexes.getInstance());
    }
}
