package com.reflex.services.fileSystem;

import android.support.annotation.Nullable;

import com.reflex.services.providers.ActionRepository;
import com.reflex.services.providers.App;
import com.reflex.services.providers.TriggerRepository;

public class FileSystem extends App {
    public FileSystem(@Nullable TriggerRepository triggerRepository, @Nullable ActionRepository actionRepository) {
        super(triggerRepository, actionRepository);
    }
}
