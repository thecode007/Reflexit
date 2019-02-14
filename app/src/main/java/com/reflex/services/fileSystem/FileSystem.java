package com.reflex.services.fileSystem;

import com.reflex.services.providers.App;

public class FileSystem extends App {
    public static FileSystem instance;

    public static FileSystem getInstance() {
        if (instance == null) {
            instance = new FileSystem();
        }
        return instance;
    }

    private FileSystem() {
        super(null,FileSystemActions.getInstance());
    }
}
