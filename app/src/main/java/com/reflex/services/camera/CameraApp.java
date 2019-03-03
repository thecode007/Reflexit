package com.reflex.services.camera;

import com.reflex.core.model.App;

public class CameraApp extends App {

    public static CameraApp instance;
    public static CameraApp getInstance() {
        if (instance == null) {
            instance = new CameraApp();
        }
        return instance;
    }



}
