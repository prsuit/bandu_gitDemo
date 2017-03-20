/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
*/
package com.justep.x5.support.media;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.Toast;

//import com.github.skykai.stickercamera.R;

import com.justep.x5.support.R;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

//import org.apache.cordova.CallbackContext;
//import org.apache.cordova.CordovaPlugin;
//import org.apache.cordova.CordovaResourceApi;
//import org.apache.cordova.PluginResult;

/**
 * This class called by CordovaActivity to play and record audio.
 * The file can be local or over a network using http.
 *
 * Audio formats supported (tested):
 * 	.mp3, .wav
 *
 * Local audio files must reside in one of two places:
 * 		android_asset: 		file name must start with /android_asset/sound.mp3
 * 		sdcard:				file name is just sound.mp3
 */
public class AudioHandler extends Activity{

    public static String TAG = "AudioHandler";
    HashMap<String, AudioPlayer> players;	// Audio player object
    ArrayList<AudioPlayer> pausedForPhone;     // Audio players that were paused when phone call came in
    private int origVolumeStream = -1;
//    private CallbackContext messageChannel;
    boolean isRecording = false;
    String audioPath;
    String filename="";
    int recLen=0;
    private Chronometer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audiorecorder);
        final Button record_btn =(Button)findViewById(R.id.record_btn);
        final ImageView ok_btn         =(ImageView)findViewById(R.id.ok_btn);
        final ImageView cancel         =(ImageView)findViewById(R.id.cancel);

        mTimer=(Chronometer)findViewById(R.id.chronometer);



        record_btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                Date date=new Date();
                DateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
                String time=format.format(date);
                if (isRecording == false) {
                    if(audioPath != null){
                        File audioFile = new File(audioPath);
                        if(audioFile.exists()){
                            audioFile.delete();
                        }
                    }
                    if(filename.isEmpty()){
                        filename=time;
                    }
                    ok_btn.setVisibility(View.INVISIBLE);
                    cancel.setVisibility(View.INVISIBLE);
                    // 将计时器清零
                    mTimer.setBase(SystemClock.elapsedRealtime());
                    mTimer.start();
                    record_btn.setBackgroundResource(R.drawable.btn_recording);
                    startRecordingAudio(filename, getTempDirectoryPath()+"/"+filename+".wav");
                    Toast.makeText(AudioHandler.this, "开始录音", Toast.LENGTH_LONG).show();
                } else if (isRecording == true) {
//                    stopRecord();
                    mTimer.stop();
                    stopRecordingAudio(filename);
                    ok_btn.setVisibility(View.VISIBLE);
                    cancel.setVisibility(View.VISIBLE);
                    record_btn.setBackgroundResource(R.drawable.btn_record);
                    isRecording=false;
                    Toast.makeText(AudioHandler.this, "录音结束,点确定上传文件或取消返回主页面.", Toast.LENGTH_LONG).show();
                }
            } catch (Throwable t) {
                t.printStackTrace();
                t.printStackTrace();
            }
        }
    });
        //返回按钮
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioPath=getTempDirectoryPath() +"/"+filename+".wav";
                recordSuccess();
//                AudioHandler.this.finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioHandler.this.finish();
            }
        });
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }




    /**
     * Constructor.
     */
    public AudioHandler() {
        this.players = new HashMap<String, AudioPlayer>();
        this.pausedForPhone = new ArrayList<AudioPlayer>();
    }

//    /**
//     * Executes the request and returns PluginResult.
//     * @param action 		The action to execute.
//     * @param args 			JSONArry of arguments for the plugin.
//     * @param callbackContext		The callback context used when calling back into JavaScript.
//     * @return 				A PluginResult object with a status and message.
//     */
//    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
//        CordovaResourceApi resourceApi = webView.getResourceApi();
//        PluginResult.Status status = PluginResult.Status.OK;
//        String result = "";
//
//        if (action.equals("startRecordingAudio")) {
//            String target = args.getString(1);
//            String fileUriStr;
//            try {
//                Uri targetUri = resourceApi.remapUri(Uri.parse(target));
//                fileUriStr = targetUri.toString();
//            } catch (IllegalArgumentException e) {
//                fileUriStr = target;
//            }
//            this.startRecordingAudio(args.getString(0), FileHelper.stripFileProtocol(fileUriStr));
//        }
//        else if (action.equals("stopRecordingAudio")) {
//            this.stopRecordingAudio(args.getString(0));
//        }
//        else if (action.equals("startPlayingAudio")) {
//            String target = args.getString(1);
//            String fileUriStr;
//            try {
//                Uri targetUri = resourceApi.remapUri(Uri.parse(target));
//                fileUriStr = targetUri.toString();
//            } catch (IllegalArgumentException e) {
//                fileUriStr = target;
//            }
//            this.startPlayingAudio(args.getString(0), FileHelper.stripFileProtocol(fileUriStr));
//        }
//        else if (action.equals("seekToAudio")) {
//            this.seekToAudio(args.getString(0), args.getInt(1));
//        }
//        else if (action.equals("pausePlayingAudio")) {
//            this.pausePlayingAudio(args.getString(0));
//        }
//        else if (action.equals("stopPlayingAudio")) {
//            this.stopPlayingAudio(args.getString(0));
//        } else if (action.equals("setVolume")) {
//           try {
//               this.setVolume(args.getString(0), Float.parseFloat(args.getString(1)));
//           } catch (NumberFormatException nfe) {
//               //no-op
//           }
//        } else if (action.equals("getCurrentPositionAudio")) {
//            float f = this.getCurrentPositionAudio(args.getString(0));
//            callbackContext.sendPluginResult(new PluginResult(status, f));
//            return true;
//        }
//        else if (action.equals("getDurationAudio")) {
//            float f = this.getDurationAudio(args.getString(0), args.getString(1));
//            callbackContext.sendPluginResult(new PluginResult(status, f));
//            return true;
//        }
//        else if (action.equals("create")) {
//            String id = args.getString(0);
//            String src = FileHelper.stripFileProtocol(args.getString(1));
//            getOrCreatePlayer(id, src);
//        }
//        else if (action.equals("release")) {
//            boolean b = this.release(args.getString(0));
//            callbackContext.sendPluginResult(new PluginResult(status, b));
//            return true;
//        }
//        else if (action.equals("messageChannel")) {
//            messageChannel = callbackContext;
//            return true;
//        }
//        else { // Unrecognized action.
//            return false;
//        }
//
//        callbackContext.sendPluginResult(new PluginResult(status, result));
//
//        return true;
//    }

//    /**
//     * Stop all audio players and recorders.
//     */
//    public void onDestroy() {
//        if (!players.isEmpty()) {
//            onLastPlayerReleased();
//        }
//        for (AudioPlayer audio : this.players.values()) {
//            audio.destroy();
//        }
//        this.players.clear();
//    }

//    /**
//     * Stop all audio players and recorders on navigate.
//     */
//    @Override
//    public void onReset() {
//        onDestroy();
//    }

    /**
     * Called when a message is sent to plugin.
     *
     * @param id            The message id
     * @param data          The message data
     * @return              Object to stop propagation or null
     */
    public Object onMessage(String id, Object data) {

        // If phone message
        if (id.equals("telephone")) {

            // If phone ringing, then pause playing
            if ("ringing".equals(data) || "offhook".equals(data)) {

                // Get all audio players and pause them
                for (AudioPlayer audio : this.players.values()) {
                    if (audio.getState() == AudioPlayer.STATE.MEDIA_RUNNING.ordinal()) {
                        this.pausedForPhone.add(audio);
                        audio.pausePlaying();
                    }
                }

            }

            // If phone idle, then resume playing those players we paused
            else if ("idle".equals(data)) {
                for (AudioPlayer audio : this.pausedForPhone) {
                    audio.startPlaying(null);
                }
                this.pausedForPhone.clear();
            }
        }
        return null;
    }

    //--------------------------------------------------------------------------
    // LOCAL METHODS
    //--------------------------------------------------------------------------

    private AudioPlayer getOrCreatePlayer(String id, String file) {
        AudioPlayer ret = players.get(id);
        if (ret == null) {
            if (players.isEmpty()) {
                onFirstPlayerCreated();
            }
            ret = new AudioPlayer(this, id, file);
            players.put(id, ret);
        }
        return ret;
    }

    /**
     * Release the audio player instance to save memory.
     * @param id				The id of the audio player
     */
    private boolean release(String id) {
        AudioPlayer audio = players.remove(id);
        if (audio == null) {
            return false;
        }
        if (players.isEmpty()) {
            onLastPlayerReleased();
        }
        audio.destroy();
        return true;
    }

    /**
     * Start recording and save the specified file.
     * @param id				The id of the audio player
     * @param file				The name of the file
     */
    public void startRecordingAudio(String id, String file) {
        isRecording=true;
        AudioPlayer audio = getOrCreatePlayer(id, file);
        audio.startRecording(file);
    }

    /**
     * Stop recording and save to the file specified when recording started.
     * @param id				The id of the audio player
     */
    public void stopRecordingAudio(String id) {
        AudioPlayer audio = this.players.get(id);
        if (audio != null) {
            audio.stopRecording();
        }
    }

    /**
     * Start or resume playing audio file.
     * @param id				The id of the audio player
     * @param file				The name of the audio file.
     */
    public void startPlayingAudio(String id, String file) {
        AudioPlayer audio = getOrCreatePlayer(id, file);
        audio.startPlaying(file);
    }

    /**
     * Seek to a location.
     * @param id				The id of the audio player
     * @param milliseconds		int: number of milliseconds to skip 1000 = 1 second
     */
    public void seekToAudio(String id, int milliseconds) {
        AudioPlayer audio = this.players.get(id);
        if (audio != null) {
            audio.seekToPlaying(milliseconds);
        }
    }

    /**
     * Pause playing.
     * @param id				The id of the audio player
     */
    public void pausePlayingAudio(String id) {
        AudioPlayer audio = this.players.get(id);
        if (audio != null) {
            audio.pausePlaying();
        }
    }

    /**
     * Stop playing the audio file.
     * @param id				The id of the audio player
     */
    public void stopPlayingAudio(String id) {
        AudioPlayer audio = this.players.get(id);
        if (audio != null) {
            audio.stopPlaying();
        }
    }

    /**
     * Get current position of playback.
     * @param id				The id of the audio player
     * @return 					position in msec
     */
    public float getCurrentPositionAudio(String id) {
        AudioPlayer audio = this.players.get(id);
        if (audio != null) {
            return (audio.getCurrentPosition() / 1000.0f);
        }
        return -1;
    }

    /**
     * Get the duration of the audio file.
     * @param id				The id of the audio player
     * @param file				The name of the audio file.
     * @return					The duration in msec.
     */
    public float getDurationAudio(String id, String file) {
        AudioPlayer audio = getOrCreatePlayer(id, file);
        return audio.getDuration(file);
    }

    /**
     * Set the audio device to be used for playback.
     *
     * @param output			1=earpiece, 2=speaker
     */
    @SuppressWarnings("deprecation")
    public void setAudioOutputDevice(int output) {
        AudioManager audiMgr = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        if (output == 2) {
            audiMgr.setRouting(AudioManager.MODE_NORMAL, AudioManager.ROUTE_SPEAKER, AudioManager.ROUTE_ALL);
        }
        else if (output == 1) {
            audiMgr.setRouting(AudioManager.MODE_NORMAL, AudioManager.ROUTE_EARPIECE, AudioManager.ROUTE_ALL);
        }
        else {
            System.out.println("AudioHandler.setAudioOutputDevice() Error: Unknown output device.");
        }
    }

    /**
     * Get the audio device to be used for playback.
     *
     * @return					1=earpiece, 2=speaker
     */
    @SuppressWarnings("deprecation")
    public int getAudioOutputDevice() {
        AudioManager audiMgr = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        if (audiMgr.getRouting(AudioManager.MODE_NORMAL) == AudioManager.ROUTE_EARPIECE) {
            return 1;
        }
        else if (audiMgr.getRouting(AudioManager.MODE_NORMAL) == AudioManager.ROUTE_SPEAKER) {
            return 2;
        }
        else {
            return -1;
        }
    }

    /**
     * Set the volume for an audio device
     *
     * @param id				The id of the audio player
     * @param volume            Volume to adjust to 0.0f - 1.0f
     */
    public void setVolume(String id, float volume) {
        AudioPlayer audio = this.players.get(id);
        if (audio != null) {
            audio.setVolume(volume);
        } else {
            System.out.println("AudioHandler.setVolume() Error: Unknown Audio Player " + id);
        }
    }

    private void onFirstPlayerCreated() {
        origVolumeStream = this.getVolumeControlStream();
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    private void onLastPlayerReleased() {
        if (origVolumeStream != -1) {
            this.setVolumeControlStream(origVolumeStream);
            origVolumeStream = -1;
        }
    }
    public void recordSuccess(){
        Intent intent = new Intent();
        if(audioPath != null){
            File recordFile = new File(audioPath);
//            if(recordFile.exists()){
                intent.setData(Uri.fromFile(recordFile));
                AudioHandler.this.setResult(RESULT_OK, intent);
                AudioHandler.this.finish();
                return;
//            }
        }
        AudioHandler.this.setResult(RESULT_CANCELED, intent);
        AudioHandler.this.finish();
    }
//    void sendEventMessage(String action, JSONObject actionData) {
//        JSONObject message = new JSONObject();
//        try {
//            message.put("action", action);
//            if (actionData != null) {
//                message.put(action, actionData);
//            }
//        } catch (JSONException e) {
//            Log.e(TAG, "Failed to create event message", e);
//        }
//
//        PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, message);
//        pluginResult.setKeepCallback(true);
//        if (messageChannel != null) {
//            messageChannel.sendPluginResult(pluginResult);
//        }
//    }
private String getTempDirectoryPath() {
    File cache = null;

    // SD Card Mounted
    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
        cache = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/Android/data/" + AudioHandler.this.getPackageName() + "/cache/");
    }
    // Use internal storage
    else {
        cache = AudioHandler.this.getCacheDir();
    }

    // Create the cache directory if it doesn't exist
    cache.mkdirs();
    return cache.getAbsolutePath();
    //return "/sdcard/";
}
}
