//package com.example.nativecr;
//
//import android.content.Context;
//import android.media.AudioManager;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.SeekBar;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//public class MainActivity extends AppCompatActivity {
//    private SeekBar seekBar;
//    private AudioManager audioManager;
//    private int previousVolume;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        seekBar = findViewById(R.id.seekBar);
//        audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
//        seekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
//        seekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
//        previousVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//    }
//
//    public void UpButton(View view){
//        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//        audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
//        int newVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//        seekBar.setProgress(newVolume);
//        if (newVolume > currentVolume) {
//            Toast.makeText(this, "Volume Increased", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "Volume is already at max", Toast.LENGTH_SHORT).show();
//        }
//        previousVolume = newVolume;
//    }
//
//    public void DownButton(View view){
//        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//        audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);
//        int newVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//        seekBar.setProgress(newVolume);
//        if (newVolume < currentVolume) {
//            Toast.makeText(this, "Volume Decreased", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "Volume is already at min", Toast.LENGTH_SHORT).show();
//        }
//        previousVolume = newVolume;
//    }
//}


package com.example.nativecr;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private SeekBar seekBar;
    private ContentResolver contentResolver;
    private int brightness;
    private int previousBrightness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivity(intent);
            }
        }

        seekBar = findViewById(R.id.seekBar);
        contentResolver = getContentResolver();

        try {
            Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        } catch (Exception e) {
            e.printStackTrace();
        }

        brightness = Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, 0);

        seekBar.setMax(255);
        seekBar.setProgress(brightness);
        previousBrightness = brightness;

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    setBrightness(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // No action needed
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // No action needed
            }
        });
    }

    public void UpButton(View view) {
        int currentBrightness = Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, 0);
        if (currentBrightness < 255) {
            int newBrightness = currentBrightness + 25;
            if (newBrightness > 255) newBrightness = 255;
            setBrightness(newBrightness);
            Toast.makeText(this, "Brightness Increased", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Brightness is already at max", Toast.LENGTH_SHORT).show();
        }
    }

    public void DownButton(View view) {
        int currentBrightness = Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, 0);
        if (currentBrightness > 0) {
            int newBrightness = currentBrightness - 25;
            if (newBrightness < 0) newBrightness = 0;
            setBrightness(newBrightness);
            Toast.makeText(this, "Brightness Decreased", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Brightness is already at min", Toast.LENGTH_SHORT).show();
        }
    }

    private void setBrightness(int brightnessValue) {
        Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, brightnessValue);
        seekBar.setProgress(brightnessValue);
    }
}
