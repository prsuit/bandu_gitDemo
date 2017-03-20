package com.tt;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder.AudioSource;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * FIXME (441000, 1, 16) is the only format that is guaranteed to work on all
 * devices
 * 
 * @author 
 * 
 */
public class AIRecorder {

    private static String TAG = "AIRecorder";

    private static int CHANNELS = 1;
    private static int BITS = 16;
    private static int FREQUENCY = 16000; // sample rate
    private static int INTERVAL = 50; // callback interval

    private static AIRecorder instance = null;
    private AudioRecord recorder = null;
    private AudioTrack player = null;

    private byte[] buffer = null;
    private Thread thread = null; // recorder/player thread
    private String path = null; // wave file path

    private volatile boolean running = false;

    public static interface Callback {
        public void run(byte[] data, int size);
    }

    public static AIRecorder getInstance() {
        if (instance == null)
            instance = new AIRecorder();
        return instance;
    }

    private AIRecorder() {

        int bufferSize = CHANNELS * FREQUENCY * BITS * INTERVAL / 1000 / 8;
        int minBufferSize = AudioRecord.getMinBufferSize(FREQUENCY, AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT);

        if (minBufferSize > bufferSize)
            bufferSize = minBufferSize;

        buffer = new byte[bufferSize];
        recorder = new AudioRecord(AudioSource.DEFAULT, FREQUENCY, AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT, bufferSize);

        player = new AudioTrack(AudioManager.STREAM_MUSIC, FREQUENCY, AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT, buffer.length, AudioTrack.MODE_STREAM);
    }

    @Override
    protected void finalize() {
        recorder.release();
        player.release();
        Log.d(TAG, "released");
    }

    public int start(final String path, final Callback callback) {

        stop();
        this.path = path;

        thread = new Thread() {
            @Override
            public void run() {
                RandomAccessFile file = null;

                try {
                    running = true;
                    recorder.startRecording();

                    if (path != null) {
                        file = fopen(path);
                    }

                    Log.d(TAG, "started");

                    /*
                     * discard the beginning 100ms for fixing the transient noise bug
                     * 
                     */
                    int discardBytes = CHANNELS * FREQUENCY * BITS * 100 / 1000 / 8;
                    while (discardBytes > 0) {
                        int requestBytes = buffer.length < discardBytes ? buffer.length : discardBytes;
                        int readBytes = recorder.read(buffer, 0, requestBytes);
                        if (readBytes > 0) {
                            discardBytes -= readBytes;
                            Log.d(TAG, "discard: " + readBytes);
                        } else {
                            break;
                        }
                    }

                    while (true) {
                        if (!running || recorder.getRecordingState() == AudioRecord.RECORDSTATE_STOPPED) {
                        	recorder.stop(); // FIXME elapse 400ms
                        	break;
                        }

                        int size = recorder.read(buffer, 0, buffer.length);
                        if (size > 0) {
                            if (callback != null) {
                                callback.run(buffer, size);
                            }
                            if (file != null) {
                                fwrite(file, buffer, 0, size);
                            }
                        }

                        if (!running) {
                            break;
                        }
                    }

                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                } finally {
                    try {
                        running = false;
                        if (recorder.getRecordingState() != AudioRecord.RECORDSTATE_STOPPED) {
                            recorder.stop(); // FIXME elapse 400ms
                        }

                        Log.d(TAG, "stoped");      
                        
                        if (file != null) {
                            fclose(file);
                        }

                    } catch (IOException e) {
                        // ignore
                    }
                }
            }
        };

        Log.d(TAG, "starting");

        running = true;
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();

        while (running && recorder.getRecordingState() != AudioRecord.RECORDSTATE_RECORDING)
            Thread.yield(); // wait till recorder started

        return recorder.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING ? 0 : -1;
    }

    public int stop() {
        if (!running)
            return 0;
        try {
            Log.d(TAG, "stopping");
            running = false;
            thread.join(); // wait till recorder stopped
        } catch (InterruptedException e) {
            // ignore
        }

        return recorder.getRecordingState() == AudioRecord.RECORDSTATE_STOPPED ? 0 : -1;
    }

    public int playback() {

        stop();

        if (path == null)
            return -1;

        thread = new Thread() {

            @Override
            public void run() {

                RandomAccessFile file = null;
                try {
                    file = new RandomAccessFile(path, "r");
                    file.seek(44);

                    running = true;
                    player.play();

                    Log.d(TAG, "playback started");

                    while (running) {
                        int size = file.read(buffer, 0, buffer.length);
                        if (size == -1) {
                            break;
                        }
                        player.write(buffer, 0, size);
                    }

                    player.flush();

                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                } finally {
                    try {
                        running = false;
                        if (player.getPlayState() != AudioTrack.PLAYSTATE_STOPPED)
                            player.stop();

                        Log.d(TAG, "playback stoped");

                        if (file != null)
                            file.close();
                    } catch (IOException e) {
                        // ignore
                    }
                }

            }
        };

        Log.d(TAG, "playback starting");

        running = true;
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();

        while (running && player.getPlayState() != AudioTrack.PLAYSTATE_PLAYING)
            Thread.yield(); // wait till player started

        return player.getPlayState() == AudioTrack.PLAYSTATE_PLAYING ? 0 : -1;
    }

    private RandomAccessFile fopen(String path) throws IOException {
        File f = new File(path);

		if (f.exists()) {
			f.delete();
		} else {
			File parentDir = f.getParentFile();
			if (!parentDir.exists()) {
				parentDir.mkdirs();
			}
		}

        RandomAccessFile file = new RandomAccessFile(f, "rw");

        /* RIFF header */
        file.writeBytes("RIFF"); // riff id
        file.writeInt(0); // riff chunk size *PLACEHOLDER*
        file.writeBytes("WAVE"); // wave type

        /* fmt chunk */
        file.writeBytes("fmt "); // fmt id
        file.writeInt(Integer.reverseBytes(16)); // fmt chunk size
        file.writeShort(Short.reverseBytes((short) 1)); // format: 1(PCM)
        file.writeShort(Short.reverseBytes((short) CHANNELS)); // channels: 1
        file.writeInt(Integer.reverseBytes(FREQUENCY)); // samples per second
        file.writeInt(Integer.reverseBytes((int) (CHANNELS * FREQUENCY * BITS / 8))); // BPSecond
        file.writeShort(Short.reverseBytes((short) (CHANNELS * BITS / 8))); // BPSample
        file.writeShort(Short.reverseBytes((short) (CHANNELS * BITS))); // bPSample

        /* data chunk */
        file.writeBytes("data"); // data id
        file.writeInt(0); // data chunk size *PLACEHOLDER*

        Log.d(TAG, "wav path: " + path);
        return file;
    }

    private void fwrite(RandomAccessFile file, byte[] data, int offset, int size) throws IOException {
        file.write(data, offset, size);
        //Log.d(TAG, "fwrite: " + size);
    }

    private void fclose(RandomAccessFile file) throws IOException {
        try {
            file.seek(4); // riff chunk size
            file.writeInt(Integer.reverseBytes((int) (file.length() - 8)));

            file.seek(40); // data chunk size
            file.writeInt(Integer.reverseBytes((int) (file.length() - 44)));

            Log.d(TAG, "wav size: " + file.length());

        } finally {
            file.close();
        }
    }
}