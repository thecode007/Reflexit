package com.reflex.services.os;

import android.content.Context;
import android.media.AudioManager;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

import com.reflex.core.GlobalApplication;
import com.reflex.core.providers.ReflexProvider;
import java.util.HashMap;

public class OSReflexes extends ReflexProvider {


    private static ReflexProvider instance;

    public static ReflexProvider getInstance() {
        if (instance == null) {
            instance = new OSReflexes();
        }
        return instance;
    }

    private OSReflexes() {
        map = new HashMap<>();
        internalMap = new HashMap<>();

        map.put(UN_MUTE, args -> {
            unMute();
        });

        map.put(VIBRATE, args -> {
            vibrate();
        });
    }



    private void unMute() {
        Context context = GlobalApplication.getAppContext();
        Toast.makeText(context, "Unmute", Toast.LENGTH_LONG).show();
        AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        assert audioManager != null;
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        audioManager.setStreamVolume(AudioManager.STREAM_RING, maxVolume,
                AudioManager.FLAG_SHOW_UI + AudioManager.FLAG_PLAY_SOUND);
    }

    private void vibrate() {
        Context context = GlobalApplication.getAppContext();
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 1500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            assert v != null;
            v.vibrate(VibrationEffect.createOneShot(1500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(1500);
        }
    }
}
