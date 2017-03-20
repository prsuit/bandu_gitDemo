package me.bandu.talk.android.phone.activity;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.DFHT.utils.LogUtils;
import com.DFHT.utils.UIUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;
import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.adapter.MoreClassAdapter;
import me.bandu.talk.android.phone.bean.LoginBean;
import me.bandu.talk.android.phone.bean.UserClassListBean;
import me.bandu.talk.android.phone.middle.GetClassMiddle;
import me.bandu.talk.android.phone.middle.UploadAvater;
import me.bandu.talk.android.phone.utils.ImageLoaderOption;
import me.bandu.talk.android.phone.utils.PreferencesUtils;
import me.bandu.talk.android.phone.utils.SaveBeanToFile;
import me.bandu.talk.android.phone.utils.UploadUtil;
import me.bandu.talk.android.phone.utils.UserUtil;

/**
 * 创建者：tg
 * 类描述：个人中心
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class PersonalDataActivity extends BaseAppCompatActivity implements UploadUtil.OnUploadProcessListener {

    @Bind(R.id.head_rl)
    RelativeLayout headRl;
    @Bind(R.id.name_rl)
    RelativeLayout nameRl;
    @Bind(R.id.school_rl)
    RelativeLayout schoolRl;
    @Bind(R.id.phone_rl)
    RelativeLayout phoneRl;
    @Bind(R.id.head)
    ImageView mImageView;
    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.school_tv)
    TextView schoolTv;
    @Bind(R.id.phone_tv)
    TextView phoneTv;
    @Bind(R.id.code_tv)
    TextView codeTv;
    @Bind(R.id.class_rl)
    RelativeLayout classRl;
    @Bind(R.id.class_code_rl)
    RelativeLayout classCodeRl;
    @Bind(R.id.class_tv)
    TextView classTv;
    @Bind(R.id.class_code_tv)
    TextView classCodeTv;
    @Bind(R.id.line)
    TextView lineTv;
    @Bind(R.id.progress)
    ProgressBar progressBar;
    @Bind(R.id.red_tv)
    TextView redTv;
    @Bind(R.id.lv_moreclass)
    ListView lv_moreclass;

    private Dialog dialog;
    private File PHOTO_DIR;
    private File mCurrentPhotoFile;
    private Bitmap cameraBitmap;
    private File f;
    private List<UserClassListBean.DataBean.ClassListBean> classlist;
    private MoreClassAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_data;
    }


    @Nullable
    @Override
    public void goback(View view) {
        onBackPressed();
    }

    @Override
    protected void toStart() {
        titleTv.setText(R.string.personal_data);
        codeTv.setText(UserUtil.getUid());
        if (UserUtil.getUerInfo(this).getRole() == 1) {//教师
            schoolRl.setVisibility(View.VISIBLE);
            lineTv.setVisibility(View.GONE);
            classRl.setVisibility(View.GONE);
            classCodeRl.setVisibility(View.GONE);
            refreshSchool();
        } else if (UserUtil.getUerInfo(this).getRole() == 2) {//学生
            schoolRl.setVisibility(View.GONE);
            lineTv.setVisibility(View.VISIBLE);
            classRl.setVisibility(View.VISIBLE);
            classCodeRl.setVisibility(View.GONE); // 3.4版本要求这个班级编号不是这样显示，所以gone
//            String className = CacheUtils.getInstance().getStringCache(this,"classname");
//            String classCode = CacheUtils.getInstance().getStringCache(this,"classnum");
//            classTv.setText(className);
//            classCodeTv.setText(classCode);

            //当进入这个页面就去请求网络获取所有的班级列表
            showMyprogressDialog();
            new GetClassMiddle(this, this).getClassList(new UserClassListBean());

            //设置所有班级的数据
            classlist = new ArrayList<>();
            adapter = new MoreClassAdapter(this, classlist);
            lv_moreclass.setAdapter(adapter);

        }

        initView();
        PHOTO_DIR = new File(Environment.getExternalStorageDirectory() + "/bandu/Camera");
    }

    public void refreshSchool() {
        if (UserUtil.getUerInfo(this).getSchool() != null) {
            if (UserUtil.getUerInfo(this).getSchool().equals("")) {
                redTv.setVisibility(View.VISIBLE);
            } else {
                redTv.setVisibility(View.GONE);
            }
        } else {
            redTv.setVisibility(View.VISIBLE);
        }

    }

    @OnClick({R.id.head_rl, R.id.name_rl, R.id.school_rl, R.id.phone_rl, R.id.class_rl})
    void click(View v) {
        switch (v.getId()) {
            case R.id.head_rl:
                setPhotoTitle();
                break;
            case R.id.name_rl:
                Intent intent1 = new Intent(this, ModifyNameActivity.class);
                startActivity(intent1);
                break;
            case R.id.school_rl:
                Intent intent2 = new Intent(this, SelectAddressActivity.class);
                startActivity(intent2);
                break;
            case R.id.phone_rl:
                Intent intent3 = new Intent(this, ModifyPhoneActivity.class);
                startActivity(intent3);
                break;

        }
    }

    @Override
    public void onSuccess(Object result, int requestCode) {
        super.onSuccess(result, requestCode);
        hideMyprogressDialog();
        final UserClassListBean bean = (UserClassListBean) result;
        if (bean != null && bean.getStatus() == 1) {
            if (requestCode == GetClassMiddle.GET_CLASS) {
                UserClassListBean.DataBean dataBean = bean.getData();
                List<UserClassListBean.DataBean.ClassListBean> classlist_in = dataBean.getClass_list();

                // 筛选出待审核的班级 不要显示在班级列表中
                for (int i = classlist_in.size() - 1; i >= 0; i--) {
                    if (classlist_in.get(i).getStatus() == 2) {
                        classlist_in.remove(i);
                    }
                }
                classlist.addAll(classlist_in); //此时classlist里面全是已通过审核的班级列表
                adapter.notifyDataSetChanged();

                //此时是没有班级列表 给出提示
                if (classlist.size() == 0) {
                    classTv.setVisibility(View.VISIBLE);
                    classTv.setText("你还未加入班级");
                }
            }
        }
    }

    @Override
    public void onFailed(int requestCode) {
        super.onFailed(requestCode);
        hideMyprogressDialog();
        UIUtils.showToastSafe("网络异常，请检查网络");
    }


    public void initView() {
        nameTv.setText(UserUtil.getUerInfo(this).getName());
        schoolTv.setText(UserUtil.getUerInfo(this).getSchool() == null ? "请补充" : UserUtil.getUerInfo(this).getSchool());
        phoneTv.setText(UserUtil.getUerInfo(this).getPhone());
        refreshSchool();
        ImageLoader.getInstance().displayImage(PreferencesUtils.getUserInfo().getAvatar(), mImageView, ImageLoaderOption.getOptions());
    }

    private void setPhotoTitle() {
        dialog = new Dialog(this, R.style.dialogStyle);

        View view = LayoutInflater.from(this).inflate(R.layout.activity_head_select, null);
        dialog.setContentView(view);

        dialog.show();

        TextView takephoto = (TextView) view.findViewById(R.id.take_pic);
        TextView bendiphoto = (TextView) view.findViewById(R.id.choose_pic);
        TextView photocancle = (TextView) view.findViewById(R.id.cancel);

        takephoto.setOnClickListener(photoClickListener);
        bendiphoto.setOnClickListener(photoClickListener);
        photocancle.setOnClickListener(photoClickListener);

    }

    @Override
    public void onBackPressed() {
        setResult(9);
        super.onBackPressed();
    }

    View.OnClickListener photoClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.take_pic:
                    // 拍照
                    takePhoto();//TODO
                    dialog.dismiss();
                    break;
                case R.id.choose_pic:
                    // 本地图片
                    useLocalAlbum();
                    dialog.dismiss();
                    break;
                case R.id.cancel:
                    // 取消
                    dialog.dismiss();
                    break;
            }
        }
    };

    protected void takePhoto() {
        /**
         * 默认开启后置摄像头
         */
        try {
            PHOTO_DIR.mkdirs();
            mCurrentPhotoFile = new File(PHOTO_DIR, getPhotoFileName());
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // 默认前置
            intent.putExtra("camerasensortype", 2);
            intent.putExtra("autofocus", true);
//            if (mCurrentPhotoFile==null){
//
//            }
//            if (mCurrentPhotoFile!=null&&mCurrentPhotoFile.exists()){
//                mCurrentPhotoFile.delete();
//            }
//            if (mCurrentPhotoFile!=null||!mCurrentPhotoFile.exists())
            uri = Uri.fromFile(mCurrentPhotoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            // intent.putExtra(MediaStore.EXTRA_OUTPUT,
            // Uri.parse(mCurrentPhotoFile+""));
            startActivityForResult(intent, 1);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Uri uri;

    /**
     * 本地照片
     */
    protected void useLocalAlbum() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
                if (data != null) {
                    final Intent intent = getCropImageIntent(data.getData());
                    startActivityForResult(intent, 3);
                } else {
                    return;
                }
                break;
            case 1:
                try {
                    // Add the image to the media store
                    Intent intentScan = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    sendBroadcast(intentScan);
                    // Launch gallery to crop the photo
                    final Intent intent = getCropImageIntent(uri);
                    startActivityForResult(intent, 3);
                } catch (Exception e) {
                    e.printStackTrace();
                    // showToast("Cannot crop image, not find photo.");
                }
                //                }
                break;
            case 3:
                if (data != null) {
                    cameraBitmap = data.getParcelableExtra("data");
                    // mImageView.setImageBitmap(cameraBitmap);
                    if(cameraBitmap!=null){
                        try {
                            saveBitmap(cameraBitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    return;
                }
                break;
        }
    }


    public void saveBitmap(Bitmap bitmap) throws IOException {

        if(bitmap == null)
            return;
        /**
         * 把bitmap保存到文件中，上传服务器文件
         */
        makeRootDirectory(PHOTO_DIR.getPath());
        f = new File(PHOTO_DIR, getPhotoFileName());
        if (f.exists()) {
            f.delete();
        } else {
            f = new File(PHOTO_DIR, getPhotoFileName());
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            UIUtils.showToastSafe("请检查SD卡是否安装");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (f.exists()) {
            //ImageLoader.getInstance().displayImage("file://" + f.getPath(), mImageView, ImageLoaderOption.getOptions());
            //上传头像
            UploadUtil.getInstance().setOnUploadProcessListener(this);
            UploadAvater.uploadFile(f.getPath());
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    progressBar.setVisibility(View.GONE);
                    try {
                        JSONObject jsonObject = new JSONObject(msg.obj.toString());
                        int status = jsonObject.getInt("status");
                        if (status == 1) {
                            String url = jsonObject.getJSONObject("data").getString("url");
                            GlobalParams.userInfo.setAvatar(url);
                            LoginBean.DataEntity userInfo = PreferencesUtils.getUserInfo();
                            userInfo.setAvatar(url);
                            PreferencesUtils.saveUserInfo(userInfo);
                            File tempFile = new File(getDir("user", MODE_PRIVATE).getAbsolutePath() + "/user");
                            tempFile.mkdirs();
                            SaveBeanToFile.beanToFile(GlobalParams.userInfo, new File(tempFile, "user.data"));
                            initView();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    //progressBar.setProgress(msg.arg1);
                    break;
                case 3:
                    ;                   // progressBar.setVisibility(View.VISIBLE);
                    //progressBar.setMax(msg.arg1);
                    break;
            }
        }
    };

    public void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {

        }
    }

    // 创建裁剪图片的意图。
    public Intent getCropImageIntent(Uri photoUri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(photoUri, "image/**");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 77);
        intent.putExtra("outputY", 77);
        intent.putExtra("return-data", true);
        return intent;
    }

    // 创建照片文件名
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss", Locale.CHINA);
        return dateFormat.format(date) + ".jpg";

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("ImageFilePath", mCurrentPhotoFile + "");
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }

    @Override
    public void onUploadDone(int responseCode, String message) {
        Message msg = Message.obtain();
        UIUtils.showToastSafe(getString(R.string.upload_succ));
        msg.what = 1;
        msg.obj = message;
        handler.sendMessage(msg);
    }

    @Override
    public void onUploadProcess(int uploadSize) {
//        Message msg = Message.obtain();
//        msg.what = 2;
//        msg.arg1 = uploadSize;
//        handler.sendMessage(msg);
    }

    @Override
    public void initUpload(int fileSize) {
//        Message msg = Message.obtain();
//        msg.what = 3;
//        msg.arg1 = fileSize;
//        handler.sendMessage(msg);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        //System.out.println("onConfigurationChanged: is called " + newConfig);
        newConfig.orientation = Configuration.ORIENTATION_PORTRAIT;
        super.onConfigurationChanged(newConfig);
        //LogUtils.e("onConfigurationChanged"+newConfig);
    }
}
