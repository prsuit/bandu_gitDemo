package com.DFHT.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.DisplayMetrics;

import com.DFHT.ENGlobalParams;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2015/10/26.
 */
public class PhoneUtil {
    public static boolean isRuninMainThread() {
        return android.os.Process.myPid() == ENGlobalParams.mainPID;
    }


    public static String getSign(Context context) {
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> apps = pm.getInstalledPackages(PackageManager.GET_SIGNATURES);
        Iterator<PackageInfo> iter = apps.iterator();
        while (iter.hasNext()) {
            PackageInfo packageinfo = iter.next();
            String packageName = packageinfo.packageName;
            if (packageName.equals(context.getPackageName())) {
                ENGlobalParams.sign = packageinfo.signatures[0].toCharsString();
                return packageinfo.signatures[0].toCharsString();
            }
        }
        return null;
    }

}