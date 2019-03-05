package com.reflex.services.camera;

import android.content.Context;
import android.content.Intent;

import com.reflex.core.model.App;
import com.reflex.core.model.Trigger;

public class PictureCaptureTrigger extends Trigger {

    public PictureCaptureTrigger() {
        super("Captures a photo", "com.android.camera.NEW_PICTURE", CameraApp.getInstance());
    }

    @Override
    protected void initReceiverBody(Context context, Intent intent) {

    }
}
