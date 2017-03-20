package com.justep.x5.support.chooser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.os.Build;
import com.justep.x5.support.R;
import com.justep.x5.support.media.AudioHandler;
import com.justep.x5.support.video.record.CameraActivity;
import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class FileUploadChooserActivity extends Activity {
    private static final int FILECHOOSER_RESULTCODE = 1;
    private static final int VIDEOCHOOSER_RESULTCODE = 2;
    private static final int IMAGECHOOSER_RESULTCODE = 3;
    private static final int AUDIOCHOOSER_RESULTCODE = 4;

    Uri fileUri;

    private PopupWindow fileChooserPopup;
    private LinearLayout ll_main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_upload_chooser_activity);
        Intent intent = this.getIntent();
        acceptType = fromString(intent.getType());
        initView();
        initFileUploadChooserPopup();
        ll_main.post(new Runnable() {
            @Override
            public void run() {
                showFileUploadChooserPopup();
            }
        });
    }

    List<String> acceptType = new ArrayList<String>();

    private static List<String> fromString(String string) {
        if(string == null || string.isEmpty()){
            List temp = new ArrayList<String>();
            temp.add("*/*");
            return temp;
        }
        String[] result = string.replace(" ","").split(",");
        List<String> types =  Arrays.asList(result);
        return types;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        acceptType = fromString(intent.getType());
    }

    private void initView() {
        ll_main = (LinearLayout) findViewById(R.id.ll_main);
    }

    public void showFileUploadChooserPopup() {
        makeWindowDark();
        Boolean HasNavigationBar=checkDeviceHasNavigationBar(FileUploadChooserActivity.this);
        if(HasNavigationBar){
            fileChooserPopup.showAtLocation(ll_main, Gravity.BOTTOM, 0, 100);
        }else{
            fileChooserPopup.showAtLocation(ll_main, Gravity.BOTTOM, 0, 0);
        }
    }

    Button btn_file;
    Button btn_camera_photo;
    Button btn_camera_video;
    Button btn_record_audio;

    View stroke_camera_photo;
    View stroke_camera_video;
    View stroke_record_audio;
    View stroke_file;
    private void initFileUploadChooserPopup() {
        LayoutInflater inflater = LayoutInflater.from(FileUploadChooserActivity.this);
        View view = inflater.inflate(R.layout.file_upload_chooser, null);
        //当view参数为null时候，不实例化PopupWindow对象，实例代码：
        if(view!=null) {
            fileChooserPopup = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            fileChooserPopup.setFocusable(true);
            fileChooserPopup.setOutsideTouchable(true);
            //点击空白处不消失
            // fileChooserPopup.setBackgroundDrawable(new ColorDrawable());
            fileChooserPopup.setAnimationStyle(R.style.PopupWindowAnimation);
        }
        btn_camera_photo = (Button) view.findViewById(R.id.btn_camera_photo);
        stroke_camera_photo=(View) view.findViewById(R.id.stroke_camera_photo);
        btn_camera_photo.setOnClickListener(new View.OnClickListener() {
            /** Create a file Uri for saving an image or video */
            private Uri getOutputMediaFileUri() {
                return Uri.fromFile(getOutputMediaFile());
            }

            private String getTempDirectoryPath() {
                File cache = null;

                // SD Card Mounted
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    cache = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +
                            "/Android/data/" + FileUploadChooserActivity.this.getPackageName() + "/cache/");
                }
                // Use internal storage
                else {
                    cache = FileUploadChooserActivity.this.getCacheDir();
                }

                // Create the cache directory if it doesn't exist
                cache.mkdirs();
                return cache.getAbsolutePath();
            }

            /** Create a File for saving an image or video */
            private File getOutputMediaFile() {
                // To be safe, you should check that the SDCard is mounted
                // using Environment.getExternalStorageState() before doing this.


                // Create a media file name
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                File mediaFile = null;
                mediaFile = new File(getTempDirectoryPath() + File.separator +
                        "IMG_" + timeStamp + ".jpg");
                return mediaFile;
            }

            @Override
            public void onClick(View v) {
                fileUri = null;
                fileChooserPopup.dismiss();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//create a intent to take picture
                fileUri = getOutputMediaFileUri();
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
                // start the image capture Intent
                FileUploadChooserActivity.this.startActivityForResult(intent, IMAGECHOOSER_RESULTCODE);
            }
        });
        if(!(acceptType.contains("image/*") || acceptType.contains("*/*"))){
            btn_camera_photo.setVisibility(View.GONE);
            stroke_camera_photo.setVisibility(View.GONE);
        }else{
            visibilityButtons.add(btn_camera_photo);
        }
        btn_camera_video = (Button) view.findViewById(R.id.btn_camera_video);
        stroke_camera_video= (View) view.findViewById(R.id.stroke_camera_video);
        btn_camera_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileChooserPopup.dismiss();
                Intent intent = new Intent(FileUploadChooserActivity.this, CameraActivity.class);
                FileUploadChooserActivity.this.startActivityForResult(intent, VIDEOCHOOSER_RESULTCODE);
            }
        });

        if(!(acceptType.contains("video/*") || acceptType.contains("*/*"))){
            btn_camera_video.setVisibility(View.GONE);
            stroke_camera_video.setVisibility(View.GONE);
        }else{
            visibilityButtons.add(btn_camera_video);
        }

        btn_record_audio = (Button) view.findViewById(R.id.btn_record_audio);
        stroke_record_audio= (View) view.findViewById(R.id.stroke_record_audio);
        btn_record_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FileUploadChooserActivity.this, AudioHandler.class);//MediaStore.Audio.Media.RECORD_SOUND_ACTION
                FileUploadChooserActivity.this.startActivityForResult(intent, AUDIOCHOOSER_RESULTCODE);
            }
        });

        if(!(acceptType.contains("audio/*") || acceptType.contains("*/*"))){
            btn_record_audio.setVisibility(View.GONE);
            stroke_record_audio.setVisibility(View.GONE);
        }else{
            visibilityButtons.add(btn_record_audio);
        }



        btn_file = (Button) view.findViewById(R.id.btn_file);
        stroke_file = (View) view.findViewById(R.id.stroke_file);
        btn_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileChooserPopup.dismiss();
	            Intent intent = new Intent();
                //SDK19以后打开方式变为ACTION_OPEN_DOCUMENT,SDK19之前是ACTION_GET_CONTENT
	            intent.setAction(Build.VERSION.SDK_INT<19?Intent.ACTION_GET_CONTENT:Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                FileUploadChooserActivity.this.startActivityForResult(intent, FILECHOOSER_RESULTCODE);
            }
        });
        if(acceptType.contains("doc/-")){
            btn_file.setVisibility(View.GONE);
            stroke_file.setVisibility(View.GONE);
        }else{
            visibilityButtons.add(btn_file);
        }



        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        fileChooserPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                makeWindowLight();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelFileChooserPopup();
            }
        });

        this.checkAutoClick();
    }
    List<Button> visibilityButtons = new ArrayList<Button>();
    private void checkAutoClick() {
        if(visibilityButtons.size() == 1){
            ll_main.post(new Runnable() {
                @Override
                public void run() {
                    visibilityButtons.get(0).performClick();
                }
            });

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == FILECHOOSER_RESULTCODE || requestCode == VIDEOCHOOSER_RESULTCODE || requestCode == AUDIOCHOOSER_RESULTCODE){
            Intent intent = new Intent();
            if(data != null){
                intent.setData(data.getData());
            }
            this.setResult(resultCode, intent);
            this.finish();
        }else if(requestCode == IMAGECHOOSER_RESULTCODE){
            Intent intent = new Intent();
            if(fileUri != null){
                intent.setData(fileUri);
                this.setResult(resultCode, intent);
            }else{
                this.setResult(RESULT_CANCELED, intent);
            }
            this.finish();
        }
    }

    public void cancelFileChooserPopup(){
        fileChooserPopup.dismiss();
        Intent intent = new Intent();
        this.setResult(RESULT_CANCELED, intent);
        this.finish();
    };

    public void makeWindowDark() {
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.5f;
        window.setAttributes(lp);
    }

    public void makeWindowLight() {
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 1f;
        window.setAttributes(lp);
    }
    //获取是否存在NavigationBar
    public static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;

    }
}
