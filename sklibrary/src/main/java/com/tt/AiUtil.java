package com.tt;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import org.apache.http.util.EncodingUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class AiUtil {

    /**
     * 计算字符串的SHA1值
     */
    public static String sha1(String message) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(message.getBytes(), 0, message.length());
            return bytes2hex(md.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 计算文件的MD5值
     */
    public static String md5(Context c, String fileName) {
        int bytes;
        byte buf[] = new byte[BUFFER_SIZE];
        try {
            InputStream is = c.getAssets().open(fileName);
            MessageDigest md = MessageDigest.getInstance("MD5");
            while ((bytes = is.read(buf, 0, BUFFER_SIZE)) > 0) {
                md.update(buf, 0, bytes);
            }
            is.close();
            return bytes2hex(md.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 计算英文句子中，单词的个数
     */
    public static long getWordCount(String sent) {
        return sent.trim().split("\\W+").length;
    }

    /**
     * 计算中文句子中，汉字的个数
     */
    public static long getHanziCount(String pin1yin1) {
        return pin1yin1.trim().split("-").length;
    }

    /**
     * 获取应用缓存路径
     */
    public static File externalFilesDir(Context c) {
        File f = c.getExternalFilesDir(null);
        // not support android 2.1
        if (f == null || !f.exists()) {
            f = c.getFilesDir();
        }
        return f;
    }

    /**
     * 读取文件内容
     */
    public static String readFile(File file) {
        String res = null;
        try {
            FileInputStream fin = new FileInputStream(file);
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            res = EncodingUtils.getString(buffer, "UTF-8");
            fin.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 从Assets 读取文件内容
     */
    public static String readFileFromAssets(Context c, String fileName) {
        String res = null;
        try {
            InputStream in = c.getAssets().open(fileName);
            int length = in.available();
            byte[] buffer = new byte[length];
            in.read(buffer);
            res = EncodingUtils.getString(buffer, "UTF-8");
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 将字符串写入指定目录文件
     */
    public static void writeToFile(String filePath, String string) {
        try {
            FileOutputStream fout = new FileOutputStream(filePath);
            byte[] bytes = string.getBytes();
            fout.write(bytes);
            fout.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将字符串写入指定文件
     */
    public static void writeToFile(File f, String string) {
        try {
            FileWriter fw = new FileWriter(f);
            fw.write(string);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将InputStream 写入指定文件
     */
    public static void writeToFile(File f, InputStream is) {
        int bytes;
        byte[] buf = new byte[BUFFER_SIZE];
        try {
            FileOutputStream fos = new FileOutputStream(f);
            while ((bytes = is.read(buf, 0, BUFFER_SIZE)) > 0) {
                fos.write(buf, 0, bytes);
            }
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从Assets 解压zip文件
     * 
     * @return 解压后的目录
     */
    public static File unzipFile(Context c, String fileName) {
        try {
            String pureName = fileName.replaceAll("\\.[^.]*$", "");

            File filesDir = externalFilesDir(c);
            File targetDir = new File(filesDir, pureName);

            String md5sum = md5(c, fileName);
            File md5sumFile = new File(targetDir, ".md5sum");

            if (targetDir.isDirectory()) {
                if (md5sumFile.isFile()) {
                    String md5sum2 = readFile(md5sumFile);
                    if (md5sum2.equals(md5sum)) {// already extracted
                        return targetDir;
                    }
                }
                // remove old dirty resource
                removeDirectory(targetDir);
            }

            unzip(c, fileName, targetDir);
            writeToFile(md5sumFile, md5sum);
            return targetDir;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(tag, "Failed to extract resource", e);
        }
        return null;
    }

    private static String tag = "AiUtil";
    private static int BUFFER_SIZE = 8192;

    private static String bytes2hex(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString();
    }

//    private static void removeDirectory(File directory) {
//        if (directory.isDirectory()) {
//            File[] files = directory.listFiles();
//            for (int i = 0; i < files.length; i++) {
//                if (files[i].isDirectory()) {
//                    removeDirectory(files[i]);
//                }
//                files[i].delete();
//            }
//            directory.delete();
//        }
//    }
    
    private static void removeDirectory(File directory) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    removeDirectory(files[i]);
                }
                // 修复删除的问题 http://blog.csdn.net/hwz2311245/article/details/47020655
                final File to = new File(files[i].getAbsolutePath() + System.currentTimeMillis());  
                files[i].renameTo(to);  
                to.delete();
//                files[i].delete();
            }
            final File to = new File(directory.getAbsolutePath() + System.currentTimeMillis());  
            directory.renameTo(to);  
            to.delete();
//            directory.delete();
        }
    }

    public static File getFilesDir(Context context) {
        File targetDir = context.getExternalFilesDir(null);
        if (targetDir == null || !targetDir.exists()) {
            targetDir = context.getFilesDir();
        }
        return targetDir;
    }
    
    private static void unzip(Context c, String fileName, File targetDir)
            throws IOException {
        InputStream is = c.getAssets().open(fileName);
        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(is,
                BUFFER_SIZE));

        ZipEntry ze;
        while ((ze = zis.getNextEntry()) != null) {
            if (ze.isDirectory()) {
                new File(targetDir, ze.getName()).mkdirs();
            } else {
                File file = new File(targetDir, ze.getName());
                File parentdir = file.getParentFile();
                if (parentdir != null && (!parentdir.exists())) {
                    parentdir.mkdirs();
                }
                int pos;
                byte[] buf = new byte[BUFFER_SIZE];
                OutputStream bos = new FileOutputStream(file);
                while ((pos = zis.read(buf, 0, BUFFER_SIZE)) > 0) {
                    bos.write(buf, 0, pos);
                }
                bos.flush();
                bos.close();
            }
        }
        zis.close();
        is.close();
    }


    public static String replaceStr(String str) {
        if ("one(large) bowl of...".equals(str)) {
            str = "one (large) bowl of...";
        }
        return str;
    }

    public static String formatReftext(String string) {
        /*String retText = orgText
            *//*全角转半角     ，。‘’“”！？*//*
                .replace("，", ", ").replace("。", ". ").replace("‘", "' ").replace("’", "'")
                .replace("“", "\" ").replace("”", "\" ").replace("！", "! ").replace("？", "? ")
                .replace("：", ": ").replace("；", "; ")
			*//*特殊字符去除*//*
                .replace("@", " ").replace("#", " ").replace("$", " ").replace("%", " ")
                .replace("~", " ").replace("`", " ").replace("^", " ").replace("&", " ")
                .replace("*", " ").replace("(", " ").replace(")", " ").replace("_", " ")
                .replace("-", " ").replace("+", " ").replace("=", " ").replace("{", " ")
                .replace("}", " ").replace("[", " ").replace("]", " ").replace("<", " ")
                .replace(">", " ").replace("【", " ").replace("】", " ").replace("、", " ")
                .replace("|", " ").replace("/", " ").replace("￥", " ").replace("（", " ")
                .replace("）", " ").replace("《", " ").replace("》", " ").replace("——", " ")
                .replace("……", " ")
			*//*字母格式转换*//*
                .replace("Ａ", "A").replace("Ｂ", "B").replace("Ｃ", "C").replace("Ｄ", "D")
                .replace("Ｅ", "E").replace("Ｆ", "F").replace("Ｇ", "G").replace("Ｈ", "H")
                .replace("Ｉ", "I").replace("Ｊ", "J").replace("Ｋ", "K").replace("Ｌ", "L")
                .replace("Ｍ", "M").replace("Ｎ", "N").replace("Ｏ", "O").replace("Ｐ", "p")
                .replace("Ｑ", "Q").replace("Ｒ", "R").replace("Ｓ", "S").replace("Ｔ", "T")
                .replace("Ｕ", "U").replace("Ｖ", "V").replace("Ｗ", "W").replace("Ｘ", "X")
                .replace("Ｙ", "Y").replace("Ｚ", "Z")
                .replace("ａ", "a").replace("ｂ", "b").replace("ｃ", "c").replace("ｄ", "d")
                .replace("ｅ", "e").replace("ｆ", "f").replace("ｇ", "g").replace("ｈ", "h")
                .replace("ｉ", "i").replace("ｊ", "j").replace("ｋ", "k").replace("ｌ", "l")
                .replace("ｍ", "m").replace("ｎ", "n").replace("ｏ", "o").replace("ｐ", "p")
                .replace("ｑ", "q").replace("ｒ", "r").replace("ｓ", "s").replace("ｔ", "t")
                .replace("ｕ", "u").replace("ｖ", "v").replace("ｗ", "w").replace("ｘ", "x")
                .replace("ｙ", "y").replace("ｚ", "z");
        return retText;*/
        if(null == string || "".equals(string))
        {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        char[] charArr = string.toCharArray();
        for (int i = 0; i < charArr.length; i++)
        {
            if(charArr[i] >= 65281 && charArr[i] <= 65374)
            {
                charArr[i] = (char) (charArr[i] - 65248);
            }
            else if(charArr[i] == 12288)
            {
                charArr[i] = (char)32;
            }
            else if(charArr[i] == 12290)
            {
                charArr[i] = (char)46;
            }
            else if(charArr[i] == 8216 || charArr[i] == 8217)
            {
                charArr[i] = (char)39;
            }
            else if(charArr[i] == 96)
            {
                charArr[i] = (char)39;
            }
            else if(charArr[i] == 8218)
            {
                charArr[i] = (char)44;
            }
            builder.append(charArr[i]);
        }
        string = new String(charArr);
        Pattern p = Pattern.compile("[-]{2,3}");
        Matcher m = p.matcher(string);
        string = m.replaceAll("—");
        return string;
    }

    public static String addBlank(String str) {
        if (str.contains("(") && str.contains(")")) {
            StringBuilder sBuilder = new StringBuilder(str);
            sBuilder.insert(str.indexOf("("), " ");
            return sBuilder.toString();
        } else
            return str;
    }


    public static String judgeEvaluateType(String content) {
        String type = "";
        //1代表试用小孩的  0代表试用以前的
        String skType = "0";
//        if(GlobalParams.loginInfo != null){
//            chivoxType = GlobalParams.loginInfo.getData().getChivox();
//        }
        if ("1".equals(skType)) {
            if (!TextUtils.isEmpty(content)) {
                long count = 0;
                String language = distinguish(content);
                if (SkConstants.CHINESE.equals(language)) {
                    count = AiUtil.getHanziCount(content);
                    if (count == 1) {
                        type = SkConstants.CN_WORD;
                    } else if (count > 1) {
                        type = SkConstants.CN_SENTENCE;
                    }
                } else if (SkConstants.ENGLISH.equals(language)) {
                    count = AiUtil.getWordCount(content);
                    if (count == 1) {
                        type = SkConstants.EN_WORD_CHILD;
                    } else if (count > 1) {
                        type = SkConstants.EN_SENTENCE_CHILD;
                    }
                }
            }
        } else {
            if (!TextUtils.isEmpty(content)) {
                long count = 0;
                String language = distinguish(content);
                if (SkConstants.CHINESE.equals(language)) {
                    count = AiUtil.getHanziCount(content);
                    if (count == 1) {
                        type = SkConstants.CN_WORD;
                    } else if (count > 1) {
                        type = SkConstants.CN_SENTENCE;
                    }
                } else if (SkConstants.ENGLISH.equals(language)) {
                    count = AiUtil.getWordCount(content);
                    if (count == 1) {
                        type = SkConstants.EN_WORD;
                    } else if (count > 1) {
                        type = SkConstants.EN_SENTENCE;
                    }
                }
            }
        }
        return type;
    }

    public static String distinguish(String src) {
        String result = "";

        if (src == null || src.isEmpty())
            return result;

        Pattern p;
        Matcher m;

        p = Pattern.compile("[\u4e00-\u9fa5]");
        m = p.matcher(src);
        if (m.find()) {
            return SkConstants.CHINESE;
        }

        p = Pattern.compile("\\p{Alpha}");// 等价于"[a-zA-Z]"
        m = p.matcher(src);
        if (m.find()) {
            return SkConstants.ENGLISH;
        }

        p = Pattern.compile("[0-9]");
        m = p.matcher(src);
        if (m.find()) {
            return SkConstants.ENGLISH;
        }

        p = Pattern.compile("\u0250-\u02AF");// 国际音标
        m = p.matcher(src);
        if (m.find()) {
            return SkConstants.ENGLISH;
        }

        // p = Pattern.compile("\\p{Punct}");//
        // 不包括标点符号，!"#$%&'()*+,-./:;<=>?@[\]^_`{|}~之一。
        // m = p.matcher(src);

        return result;
    }


}
